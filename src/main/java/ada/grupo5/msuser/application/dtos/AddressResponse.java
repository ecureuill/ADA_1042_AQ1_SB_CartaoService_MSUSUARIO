package ada.grupo5.msuser.application.dtos;

import ada.grupo5.msuser.domain.valueobjects.Address;

public record AddressResponse(
    String zipCode,
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state
) {

    public AddressResponse(Address address) {
        this(
            address.getZipCode(),
            address.getStreet(),
            address.getNumber(),
            address.getComplement(),
            address.getNeighborhood(),
            address.getCity(),
            address.getState()
        );
    }

}
