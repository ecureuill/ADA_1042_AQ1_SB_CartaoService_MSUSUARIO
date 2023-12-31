package ada.grupo5.msuser.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ada.grupo5.msuser.application.dtos.DependentRequest;
import ada.grupo5.msuser.application.dtos.DependenteResponse;
import ada.grupo5.msuser.application.dtos.UserRequest;
import ada.grupo5.msuser.application.dtos.UserResponse;
import ada.grupo5.msuser.application.services.UserService;
import ada.grupo5.msuser.domain.dependent.DependentAlreadyExistException;
import ada.grupo5.msuser.domain.user.UserAlreadyExistException;
import ada.grupo5.msuser.infrastructure.services.CepNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    @Transactional
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest dto) throws UserAlreadyExistException, DependentAlreadyExistException, CepNotFoundException {
        var user = service.create(dto);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        var users = service.findAll();
        
        return ResponseEntity.ok().body(users.stream().map( UserResponse::new).collect(Collectors.toList()));
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String cpf) {

        var user = service.findByCpf(cpf).orElseThrow();

        return ResponseEntity.ok().body(new UserResponse(user));
    }

    @PostMapping("/{cpf}/dependents")
    @Transactional
    public ResponseEntity<DependenteResponse> createDependent(@PathVariable String cpf, @RequestBody @Valid DependentRequest dto) throws DependentAlreadyExistException {
        var user = service.addDependent(dto, cpf);
        
        return ResponseEntity.ok().body(user);
    
    }

    @DeleteMapping("/{cpf}/dependents/{dependentCpf}")
    @Transactional
    public ResponseEntity<Void> deleteDependent(@PathVariable String cpf, @PathVariable String dependentCpf) {
        service.deleteDependent(cpf, dependentCpf);

        return ResponseEntity.noContent().build();
    }

}
