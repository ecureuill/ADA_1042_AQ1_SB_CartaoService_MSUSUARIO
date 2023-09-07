package ada.grupo5.msuser.application.services;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ada.grupo5.msuser.application.dtos.DependentRequest;
import ada.grupo5.msuser.application.dtos.FinanceResponse;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.dependent.DependentAlreadyExistException;
import ada.grupo5.msuser.infrastructure.repositories.DependentRepository;
import feign.FeignException;

@ExtendWith(MockitoExtension.class)
public class DependentServiceTest {

    @Mock
    private DependentRepository dependentRepository;
    @Mock
    private FinanceService financeService;

    @InjectMocks
    private DependentService dependentService;

    @Test
    @DisplayName("Should add a credit card to the user")
    void testAddCreditCard() {
        Dependent dependent = new Dependent(
            "Maria",
            "19031555096",
            null
        );

        Mockito.when(dependentRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.of(dependent));
        Mockito.when(financeService.createDependentCreditCard(Mockito.any(Dependent.class))).thenReturn(new FinanceResponse("1234567812345678", LocalDate.now().plusYears(3), "123"));

        Dependent updatededDependent = dependentService.addCreditCard(dependent);

        Assertions.assertEquals("1234567812345678", updatededDependent.getAditionalCreditCard().getCardNumber());
    }

    @DisplayName("Should throw NoShuchElementException when dependent not exist")
    @Test
    void testAddCreditCardDependentNoExist() {
         Mockito.when(dependentRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> dependentService.addCreditCard(new Dependent("John Doe", "12345678901", null)));

    }

    @DisplayName("Should throw FeignError when FinanceService fails")
    @Test
    void testAddCreditCardFinanceService() {
        Dependent dependent = new Dependent(
            "Maria",
            "19031555096",
            null
        );

        Mockito.when(dependentRepository.findByCpf(Mockito.anyString())).thenReturn(Optional.of(dependent));
        Mockito.when(financeService.createDependentCreditCard(Mockito.any(Dependent.class))).thenThrow(FeignException.class);

        Assertions.assertThrows(FeignException.class, () -> dependentService.addCreditCard(dependent));
    }

    @DisplayName("Should create dependent")
    @Test
    void testCreateDependent() throws DependentAlreadyExistException {
        DependentRequest dto = new DependentRequest("12312312332", "Maria");
        Mockito.when(dependentRepository.existsByCpf(Mockito.anyString())).thenReturn(false);

        Dependent dependent = dependentService.createDependent(dto);

        Assertions.assertEquals("12312312332", dependent.getCpf());
        Assertions.assertEquals("Maria", dependent.getName());
    }

    @DisplayName("Should throw DependentAlreadyExistException when dependent already exist")
    @Test
    void testCreateDependentAlreadyExist() throws DependentAlreadyExistException {
        DependentRequest dto = new DependentRequest("12312312332", "Maria");
        Mockito.when(dependentRepository.existsByCpf(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(DependentAlreadyExistException.class, () -> dependentService.createDependent(dto));
    
    }
}
