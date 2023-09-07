package ada.grupo5.msuser.domain.user;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String cpf) {
        super("User with cpf " + cpf + "was not founded");
    }

}
