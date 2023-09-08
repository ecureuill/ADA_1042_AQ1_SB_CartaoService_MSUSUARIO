package ada.grupo5.msuser.infrastructure.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ada.grupo5.msuser.application.dtos.FinanceResponse;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.user.User;

@FeignClient(value = "finance", url = "${finance.url}")
public interface FinanceAPIClient {

    @RequestMapping(method = RequestMethod.POST, value = "/cards/additional")
    public FinanceResponse createDependentCreditCard(Dependent dependent);

    @RequestMapping(method = RequestMethod.POST, value = "/cards")
    public FinanceResponse createUserCreditCard(User user);

    @RequestMapping(method = RequestMethod.DELETE, value = "/cards/{cardNumber}")
    public FinanceResponse deleteCreditCard(String cardNumber);

    @RequestMapping(method = RequestMethod.GET, value = "cards")
    public void ping();

    
}
