package ada.grupo5.msuser.domain.valueobjects;

import java.time.LocalDate;

import ada.grupo5.msuser.application.dtos.FinanceResponse;
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
    
    public CreditCard(FinanceResponse financeResponse) {
        this.cardNumber = financeResponse.cardNumber();
        this.expirationDate = financeResponse.expirationDate();
        this.securityCode = financeResponse.securityCode();
    }
}
