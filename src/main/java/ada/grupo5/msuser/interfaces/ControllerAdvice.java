package ada.grupo5.msuser.interfaces;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ada.grupo5.msuser.domain.dependent.DependentAlreadyExistException;
import ada.grupo5.msuser.domain.user.UserAlreadyExistException;
import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler{
    @ExceptionHandler({
        NotFoundException.class,
        ValidationException.class,
        UserAlreadyExistException.class,
        DependentAlreadyExistException.class,
    })
    public ResponseEntity<String> handleBadRequestException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}