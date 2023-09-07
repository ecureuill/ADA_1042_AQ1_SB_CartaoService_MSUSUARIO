package ada.grupo5.msuser.application.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cglib.core.Local;

import ada.grupo5.msuser.application.dtos.AddressRequest;
import ada.grupo5.msuser.application.dtos.CepResponse;
import ada.grupo5.msuser.application.dtos.DependentRequest;
import ada.grupo5.msuser.application.dtos.FinanceResponse;
import ada.grupo5.msuser.application.dtos.UserRequest;
import ada.grupo5.msuser.application.dtos.UserResponse;
import ada.grupo5.msuser.application.enums.CreditCardType;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.dependent.DependentAlreadyExistException;
import ada.grupo5.msuser.domain.user.User;
import ada.grupo5.msuser.domain.user.UserAlreadyExistException;
import ada.grupo5.msuser.domain.valueobjects.Address;
import ada.grupo5.msuser.domain.valueobjects.CreditCard;
import ada.grupo5.msuser.infrastructure.repositories.UserRepository;
import ada.grupo5.msuser.infrastructure.services.CepNotFoundException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    DependentService dependentService;
    @Mock
    CepService cepService;
    @Mock
    FinanceService financeService;

    @InjectMocks
    UserService userService;
    
    @Test
    @DisplayName("Should create a user with 0 dependents")
    void shouldCreateUserWithNoDependents() throws CepNotFoundException, DependentAlreadyExistException, UserAlreadyExistException {

        CepResponse cepResponse = new CepResponse("01303001", "SP", "São Paulo", "Centro", "Rua Caio Prado");
        
        FinanceResponse financeResponse = new FinanceResponse("45612378098765432", LocalDate.now(), "321");

        User user = new User(
            "Maria",
            "12345678900",
            new Address(cepResponse, "123", "ab"),
            new CreditCard(financeResponse),
            new ArrayList<Dependent>());

        UserResponse expectedResponse = new UserResponse(user);


        when(userRepository.existsByCpf(anyString())).thenReturn(false);
        
        when(cepService.findAddress(anyString())).thenReturn(cepResponse);

        // when(dependentService.createDependent(any(DependentRequest.class))).thenReturn(new Dependent("Joao", "12345678900", null));

        when(financeService.createUserCreditCard(any(User.class))).thenReturn(financeResponse);

        when(userRepository.save(any(User.class))).thenReturn(user);

        var actualResponse = userService.create(new UserRequest(
            "12345678900", 
            "Maria",
            new AddressRequest("01303001", "123", "ab"),
            CreditCardType.OURO,
            new ArrayList<DependentRequest>()
        ));

        assertEquals(expectedResponse, actualResponse);
        
        verify(userRepository, times(1)).existsByCpf(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(cepService, times(1)).findAddress(anyString());
        verify(financeService, times(1)).createUserCreditCard(any(User.class));
    }

    @Test
    @DisplayName("Should create a user with 1 dependent")
    void shouldCreateUseerWithDependents() throws CepNotFoundException, DependentAlreadyExistException, UserAlreadyExistException {

        CepResponse cepResponse = new CepResponse("01303001", "SP", "São Paulo", "Centro", "Rua Caio Prado");
        
        FinanceResponse financeResponse = new FinanceResponse("45612378098765432", LocalDate.now(), "321");

        Dependent dependent = new Dependent(
            "Jose",
            "32132132112",
            new CreditCard(financeResponse)
        );

        User user = new User(
            "Maria",
            "12345678900",
            new Address(cepResponse, "123", "ab"),
            new CreditCard(financeResponse),
            new ArrayList<Dependent>());
        user.getDependents().add(dependent);

        UserResponse expectedResponse = new UserResponse(user);


        when(userRepository.existsByCpf(anyString())).thenReturn(false);
        
        when(cepService.findAddress(anyString())).thenReturn(cepResponse);

        when(dependentService.createDependent(any(DependentRequest.class))).thenReturn(new Dependent("Jose", "32132132112", null));

        when(financeService.createUserCreditCard(any(User.class))).thenReturn(financeResponse);
        when(financeService.createDependentCreditCard(any(Dependent.class))).thenReturn(financeResponse);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserRequest userRequest = new UserRequest(
            "12345678900", 
            "Maria",
            new AddressRequest("01303001", "123", "ab"),
            CreditCardType.OURO,
            new ArrayList<DependentRequest>()
        );
        DependentRequest dependentRequest = new DependentRequest("32132132112", "Jose");
        userRequest.dependentRequestRecords().add(dependentRequest);

        var actualResponse = userService.create(userRequest);

        assertEquals(expectedResponse, actualResponse);
        
        verify(userRepository, times(1)).existsByCpf(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(cepService, times(1)).findAddress(anyString());
        verify(financeService, times(1)).createUserCreditCard(any(User.class));
        verify(financeService, times(1)).createDependentCreditCard(any(Dependent.class));

    }
}
