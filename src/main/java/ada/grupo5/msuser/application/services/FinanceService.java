package ada.grupo5.msuser.application.services;

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
        return client.createUserCreditCard(user);
    }

    public FinanceResponse createDependentCreditCard(Dependent dependent)
    {
        return client.createDependentCreditCard(dependent);
    }

}
