package ada.grupo5.msuser.infrastructure.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ada.grupo5.msuser.domain.dependent.Dependent;

public interface DependentRepository extends JpaRepository<Dependent, UUID>{

    boolean existsByCpf(String cpf);
    
}
