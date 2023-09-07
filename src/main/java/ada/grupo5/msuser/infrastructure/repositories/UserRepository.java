package ada.grupo5.msuser.infrastructure.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ada.grupo5.msuser.domain.user.User;

public interface UserRepository extends JpaRepository<User, UUID>{

    boolean existsByCpf(String cpf);

    Optional<User> findByCpf(String cpf);
    
}
