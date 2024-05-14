package ma.emsi.ebanking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.ebanking.Enum.AccountStatus;

import java.util.Date;
import java.util.List;
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", length = 10, discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {

    @Id
    private String id;
    private double balance;
    private Date createDate;
    @Enumerated(EnumType.STRING) // pour que ca donne format string
    private AccountStatus status;

    //Relations
    //un compte appartient a un seul client
    @ManyToOne
    private Customer customer;
    //un compte contient plusieurs operations
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.EAGER)
    //lazy va charger seulement les attributs sans les operations : chargement a la demande
    //Eager chargement des comptes avec leurs operations
    private List<AccountOperations> accountOperation;


}
