package ada.grupo5.msuser.infrastructure.services;

public class CepNotFoundException extends Exception {

    public CepNotFoundException(String cep) {
        super("CEP " + cep + " not found");
    }
}
