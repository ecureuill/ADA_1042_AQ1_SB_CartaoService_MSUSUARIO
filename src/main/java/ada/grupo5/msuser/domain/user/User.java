package ada.grupo5.msuser.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import ada.grupo5.msuser.domain.dependent.Dependent;
import ada.grupo5.msuser.domain.valueobjects.Address;
import ada.grupo5.msuser.domain.valueobjects.CreditCard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String cpf;
    @Embedded
    private Address address;
    @Embedded
    private CreditCard mainCreditCard;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private List<Dependent> dependents;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(String name, String cpf, Address endereco, CreditCard creditCard, List<Dependent> dependents) {
        this.name = name;
        this.cpf = cpf;
        this.mainCreditCard = creditCard;
        this.address = endereco;
        this.dependents = new ArrayList<Dependent>();
        this.dependents.addAll(dependents);
    }
    
    
}
