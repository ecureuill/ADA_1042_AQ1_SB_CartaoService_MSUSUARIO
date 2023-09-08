package ada.grupo5.msuser.application.services;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import ada.grupo5.msuser.application.dtos.FinanceResponse;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.user.User;
import ada.grupo5.msuser.infrastructure.services.FinanceAPIClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FinanceService {
    
    private final FinanceAPIClient client;

    public FinanceResponse createUserCreditCard(User user){
        try {
            return client.createUserCreditCard(user);
        } catch (Exception e) {
            return new FinanceResponse("0000000000000000", LocalDate.now().plusYears(3), "123");
        }
    }

    public FinanceResponse createDependentCreditCard(Dependent dependent)
    {
        try {
            
            return client.createDependentCreditCard(dependent);
        } catch (Exception e) {
            return new FinanceResponse("1111111111111111", LocalDate.now().plusYears(3), "123");
        }
    }

    public void deleteCreditCard(String cardNumber) {
        client.deleteCreditCard(cardNumber);
    }

    public void ping() {
        client.ping();
    }

}
