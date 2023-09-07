package ada.grupo5.msuser.application.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ada.grupo5.msuser.application.dtos.CepResponse;
import ada.grupo5.msuser.application.dtos.DependentRequest;
import ada.grupo5.msuser.application.dtos.DependenteResponse;
import ada.grupo5.msuser.application.dtos.UserRequest;
import ada.grupo5.msuser.application.dtos.UserResponse;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.dependent.DependentAlreadyExistException;
import ada.grupo5.msuser.domain.user.User;
import ada.grupo5.msuser.domain.user.UserAlreadyExistException;
import ada.grupo5.msuser.domain.valueobjects.Address;
import ada.grupo5.msuser.domain.valueobjects.CreditCard;
import ada.grupo5.msuser.infrastructure.repositories.UserRepository;
import ada.grupo5.msuser.infrastructure.services.CepNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DependentService dependentService;
    private final CepService cepService;
    private final FinanceService financeService;

    public UserResponse create(UserRequest cadastroUsuarioRequest) throws UserAlreadyExistException, DependentAlreadyExistException, CepNotFoundException {
        
        if(userRepository.existsByCpf(cadastroUsuarioRequest.cpf())){
            throw new UserAlreadyExistException();
        }

        User user = createUser(cadastroUsuarioRequest);

        user = userRepository.save(user);
        var mainCreditCard = financeService.createUserCreditCard(user);
        user.setMainCreditCard(new CreditCard(mainCreditCard.cardNumber(), mainCreditCard.expirationDate(),  mainCreditCard.securityCode()));

        for(Dependent dependent: user.getDependents())
        {
            var additionalCreditCard = financeService.createDependentCreditCard(dependent);
            dependent.setAditionalCreditCard(new CreditCard(additionalCreditCard.cardNumber(), additionalCreditCard.expirationDate(), additionalCreditCard.securityCode()));
        }

        return new UserResponse(user);

    }

    private User createUser(UserRequest userRequestRecord) throws DependentAlreadyExistException, CepNotFoundException {
        
        CepResponse addressResponse = cepService.findAddress(userRequestRecord.addressRequestRecord().zipCode());
        
        Address address = new Address(addressResponse, userRequestRecord.addressRequestRecord().number(), userRequestRecord.addressRequestRecord().complement());

        List<Dependent> dependents = buildDependents(userRequestRecord);

        User usuario = new User(
            userRequestRecord.name(), 
            userRequestRecord.cpf(),
            address,
            null,
            dependents
        );
        return usuario;
    }

    private List<Dependent> buildDependents(UserRequest userRequestRecord) {
        return userRequestRecord.dependentRequestRecords().stream()
        .map((DependentRequest record) -> {
                try {
                    return dependentService.createDependent(record);
                } catch (DependentAlreadyExistException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public DependenteResponse addDependent(DependentRequest dependentRequest, String cpf) {
        User user = findByCpf(cpf).orElseThrow();
        Dependent dependent = new Dependent(dependentRequest);

        user.getDependents().add(dependent);

        userRepository.save(user);
        dependent = dependentService.addCreditCard(dependent);

        return new DependenteResponse(dependent);
    }

    public void deleteDependent(String cpf, String dependentCpf) {
        User user = findByCpf(cpf).orElseThrow();
        Dependent dependent = user.getDependents().stream().filter(d -> d.getCpf().equals(dependentCpf)).findFirst().orElseThrow();

        user.getDependents().remove(dependent);
        userRepository.save(user);

        financeService.deleteCreditCard(dependent.getAditionalCreditCard().getCardNumber());
    }
    
}
