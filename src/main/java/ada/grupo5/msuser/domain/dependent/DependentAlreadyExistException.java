package ada.grupo5.msuser.domain.dependent;

public class DependentAlreadyExistException extends Exception{

    public DependentAlreadyExistException(String cpf) {
        super("Dependent with cpf " + cpf + "is already registered.");
    }

}
