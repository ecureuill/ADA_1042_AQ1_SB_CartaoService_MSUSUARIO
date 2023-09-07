package ada.grupo5.msuser.infrastructure.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ada.grupo5.msuser.application.dtos.FinanceResponse;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.user.User;

@FeignClient(value = "financeAPI", url = "http://finance-ms/")
public interface FinanceAPIClient {

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public FinanceResponse createDependentCreditCard(Dependent dependent);

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public FinanceResponse createUserCreditCard(User user);

    
}
