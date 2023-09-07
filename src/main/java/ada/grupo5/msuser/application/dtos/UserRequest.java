package ada.grupo5.msuser.application.dtos;

import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import ada.grupo5.msuser.application.enums.CreditCardType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @CPF
    @NotBlank
    String cpf,
    @NotBlank
    String name,
    @Valid
    @NotNull
    AddressRequest addressRequestRecord,
    @NotNull
    CreditCardType creditCardType,
    @Valid
    @NotNull
    List<DependentRequest> dependentRequestRecords  
) {
    
}
