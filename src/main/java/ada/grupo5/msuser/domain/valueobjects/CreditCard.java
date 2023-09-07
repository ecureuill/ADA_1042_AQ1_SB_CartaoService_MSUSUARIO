package ada.grupo5.msuser.domain.valueobjects;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CreditCard {
    private String cardNumber;
    private LocalDate expirationDate;
    private String securityCode;
}
