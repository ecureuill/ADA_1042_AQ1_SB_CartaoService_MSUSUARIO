package ada.grupo5.msuser.application.services;

import org.springframework.stereotype.Service;

import ada.grupo5.msuser.application.dtos.DependentRequest;
import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.dependent.DependentAlreadyExistException;
import ada.grupo5.msuser.infrastructure.repositories.DependentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DependentService {

    private final DependentRepository dependentRepository;

    public Dependent createDependent(DependentRequest record) throws DependentAlreadyExistException{
        if(dependentRepository.existsByCpf(record.cpf())){
            throw new DependentAlreadyExistException(record.cpf());
        }

        Dependent dependent = new Dependent(record.name(), record.cpf(), null);

        return dependent;
    }
}
