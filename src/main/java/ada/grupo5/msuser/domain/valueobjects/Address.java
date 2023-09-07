package ada.grupo5.msuser.domain.valueobjects;

import ada.grupo5.msuser.application.dtos.CepResponse;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String zipCode;
    private String number;
    private String complement;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String country;

    public Address(CepResponse addressResponse, String number, String complement) {
        this.zipCode = addressResponse.cep();
        this.state = addressResponse.state();
        this.city = addressResponse.city();
        this.country = "Brasil";
        this.street = addressResponse.street();
        this.neighborhood = addressResponse.neighborhood();
        this.number = number;
        this.complement = complement;
    }
}
