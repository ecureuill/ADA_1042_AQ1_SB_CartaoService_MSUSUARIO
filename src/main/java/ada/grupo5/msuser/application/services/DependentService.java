package ada.grupo5.msuser.application.services;

import org.springframework.stereotype.Service;

import ada.grupo5.msuser.application.dtos.DependentRequest;
import ada.grupo5.msuser.application.dtos.FinanceResponse;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.dependent.DependentAlreadyExistException;
import ada.grupo5.msuser.domain.valueobjects.CreditCard;
import ada.grupo5.msuser.infrastructure.repositories.DependentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DependentService {

    private final DependentRepository dependentRepository;
    private final FinanceService financeService;

    public Dependent createDependent(DependentRequest record) throws DependentAlreadyExistException{
        if(dependentRepository.existsByCpf(record.cpf())){
            throw new DependentAlreadyExistException(record.cpf());
        }

        Dependent dependent = new Dependent(record.name(), record.cpf(), null);

        return dependent;
    }

    public Dependent addCreditCard(Dependent dependent) {
        dependent = dependentRepository.findByCpf(dependent.getCpf()).orElseThrow();
        
        FinanceResponse additionalCreditCard = financeService.createDependentCreditCard(dependent);

        dependent.setAditionalCreditCard(new CreditCard(additionalCreditCard.cardNumber(), additionalCreditCard.expirationDate(), additionalCreditCard.securityCode()));

        return dependentRepository.save(dependent);
    }
}
