package ma.emsi.ebanking.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@DiscriminatorValue("Saving")
public class SavingAccount extends BankAccount
{
    private double interestRate;
}
