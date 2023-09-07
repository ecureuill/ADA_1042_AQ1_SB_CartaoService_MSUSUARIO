package ada.grupo5.msuser.application.dtos;

import java.time.LocalDate;

import ada.grupo5.msuser.domain.valueobjects.CreditCard;

public record CreditCardResponse(
    String cardNumber,
    LocalDate expirationDate,
    String securityCode
) {

    public CreditCardResponse(CreditCard aditionalCreditCard) {
        this(aditionalCreditCard.getCardNumber(), aditionalCreditCard.getExpirationDate(), aditionalCreditCard.getSecurityCode());
    }

}
