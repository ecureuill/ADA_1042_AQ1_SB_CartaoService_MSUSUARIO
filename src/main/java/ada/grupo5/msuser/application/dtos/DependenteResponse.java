package ada.grupo5.msuser.application.dtos;

import ada.grupo5.msuser.domain.dependent.Dependent;

public record DependenteResponse(
    String name, 
    String cpf,
    CreditCardResponse card
) {

    public DependenteResponse(Dependent dependent) {
        this(
            dependent.getName(),
            dependent.getCpf(),
            new CreditCardResponse(dependent.getAditionalCreditCard())
        );
    }
}
