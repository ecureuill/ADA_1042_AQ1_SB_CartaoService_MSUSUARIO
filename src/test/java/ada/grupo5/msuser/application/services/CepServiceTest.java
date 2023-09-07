package ada.grupo5.msuser.application.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ada.grupo5.msuser.application.dtos.CepResponse;
import ada.grupo5.msuser.infrastructure.services.BrasilAPIClient;
import ada.grupo5.msuser.infrastructure.services.CepNotFoundException;
import feign.FeignException;

@ExtendWith(MockitoExtension.class)
public class CepServiceTest {

    @Mock
    private BrasilAPIClient brasilAPIClient;

    @InjectMocks
    private CepService cepService;

    @DisplayName("Should return a address for a valid CEP")
    @Test
    public void shouldReturnAddressForValidCep() throws CepNotFoundException {
        String cep = "01303001";

        Mockito.when(brasilAPIClient.getCep(cep)).thenReturn(new CepResponse(cep, "SP", "São Paulo", "Centro", "Rua Caio Prado"));

        var address = cepService.findAddress(cep);
        
        Assertions.assertEquals("São Paulo", address.city());
    }

    @DisplayName("Should throw when CEP is invalid")
    @Test
    public void shouldThrowWhenCepIsInvalid() throws CepNotFoundException {
        String cep = "00000000";

        Mockito.when(brasilAPIClient.getCep(cep)).thenThrow(FeignException.class);

        Assertions.assertThrows(CepNotFoundException.class, () -> cepService.findAddress(cep));
    }
    
}
