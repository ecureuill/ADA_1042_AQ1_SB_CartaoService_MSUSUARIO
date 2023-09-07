package ada.grupo5.msuser.application.dtos;

import java.time.LocalDate;

public record FinanceResponse (
    String cardNumber,
    LocalDate expirationDate,
    String securityCode    
){

}
