package ada.grupo5.msuser.application.services;

import org.springframework.stereotype.Service;

import ada.grupo5.msuser.application.dtos.CepResponse;
import ada.grupo5.msuser.infrastructure.services.BrasilAPIClient;
import ada.grupo5.msuser.infrastructure.services.CepNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CepService {
    
    private final BrasilAPIClient client;

    public CepResponse findAddress(String cep) throws CepNotFoundException{
        try {
            return client.getCep(cep);
            
        } catch (Exception e) {
            throw new CepNotFoundException(cep);
        }
    }
}
