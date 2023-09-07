package ada.grupo5.msuser.application.dtos;


public record CepResponse(
    String cep,
    String state,
    String city,
    String neighborhood,
    String street
){}
