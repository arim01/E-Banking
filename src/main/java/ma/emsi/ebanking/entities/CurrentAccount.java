package ma.emsi.ebanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

//il faut mapper les classes contenant l'heritage
//Mapping heritage : 3 strategies
//1-single table
//cote base de donnée nous allons avoir une seule table
//bankAccount ou nous allons mettre tout les attributs concernant le compte
//en plus de la colonne type ce qu'on appelle 'colonne descriminatrice' c ou on doit indiquer s'il s'agit d'un compte courant ou epargne
//c'est la strategie la plus utilisée


//////////////////
//2- Table per class
//separation des deux tables
//Table Current Acc avec ses attributs

//Table Compte Epargne avec ses attributs


//////////////////
//3- Joined table
//Table account
//dans cette table on garde que les colonne communes cad les attributs de la table account
//Table current
//cette table va contenir les attributs lié a current + id compte
//Table Saving
//meme chose + id compte
//Puis on cree un lien entre les deux

@Entity
@DiscriminatorValue("Current")
public class CurrentAccount extends BankAccount
{
    private double overDraft;
}
