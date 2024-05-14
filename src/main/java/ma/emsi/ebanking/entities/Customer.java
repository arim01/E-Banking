package ma.emsi.ebanking.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//mapping
@Entity
////////
@Data
@NoArgsConstructor @AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String nom;
    private String Email;
    //Un client peut avoir plusieurs comptes
    @OneToMany(mappedBy = "customer")
    //dans la classe bank account y a un attribut customer qui utilise une annotation many to one
    //ils savent maintenant qu'il s'agit de la meme relation / meme cle etrangeres

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //par cette annotation on demande a jackson d'ignoer cette attribut lors de l'affichage des clients
    private List<BankAccount> listeCompte;


    //Bonne pratique :
    //Faut pas utiliser les entités directement, on doit utiliser les dtos
    //On transfert les données des entites vers les dto
    //On peut meme choisir les attributs a transferer


}
