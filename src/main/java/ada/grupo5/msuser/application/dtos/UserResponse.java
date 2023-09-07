package ada.grupo5.msuser.application.dtos;

import java.util.List;
import java.util.stream.Collectors;

import ada.grupo5.msuser.domain.user.User;

public record UserResponse(
    String name,
    String cpf,
    AddressResponse endereco,
    CreditCardResponse mainCreditCard,
    List<DependenteResponse> dependents
) {

    public UserResponse(User usuario) {
        this(
            usuario.getName(), 
            usuario.getCpf(), 
            new AddressResponse(usuario.getAddress()),
            new CreditCardResponse(usuario.getMainCreditCard()), 
            usuario.getDependents().stream().map(DependenteResponse::new).collect(Collectors.toList())
        );
    }
    
}
