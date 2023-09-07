package ada.grupo5.msuser.application.dtos;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;

public record DependentRequest(
    @CPF
    @NotBlank
    String cpf,
    @NotBlank
    String name
) {
    
}
