package ada.grupo5.msuser.infrastructure.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ada.grupo5.msuser.application.dtos.CepResponse;

@FeignClient(value = "brasilapi", url = "https://brasilapi.com.br/api/cep/v2/")
public interface BrasilAPIClient {
 
    @RequestMapping(method = RequestMethod.GET, value = "/{cep}", produces = "application/json")
    public CepResponse getCep(@PathVariable String cep);
}
