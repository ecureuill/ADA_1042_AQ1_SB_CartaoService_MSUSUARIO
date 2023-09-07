package ada.grupo5.msuser.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRequest(
    @NotBlank
    @Pattern(regexp = "[0-9]{8}")
    String zipCode,
    @NotBlank
    String number,
    String complement
) {
    
}
