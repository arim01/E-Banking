package ma.emsi.ebanking.services;

import jakarta.transaction.Transactional;
import ma.emsi.ebanking.entities.BankAccount;
import ma.emsi.ebanking.entities.CurrentAccount;
import ma.emsi.ebanking.entities.SavingAccount;
import ma.emsi.ebanking.repositories.BankAccountRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankServices {
    @Autowired
    private BankAccountRepos br;
    public void consulter(){
        BankAccount bank=
                br.findById("693becff-5931-4a6c-a203-6e55ff1c0afe").orElse(null);
        if(bank != null){
            System.out.println("------------------");
            System.out.print(bank.getId());
            System.out.print(bank.getStatus());
            System.out.print(bank.getCustomer().getNom());
            System.out.print(bank.getCreateDate());
            if(bank instanceof CurrentAccount){
                System.out.print("Current Acc : "+((CurrentAccount) bank).getOverDraft());
            }
            else if (bank instanceof SavingAccount){
                System.out.print("Saving Acc : "+((SavingAccount) bank).getInterestRate());
            }


            bank.getAccountOperation().forEach(op->{
                System.out.print("----------------");
                System.out.print(op.getType()+ "\t"+ op.getOperationDate());
                System.out.print(op.getAmount());



            });
        }
    }

    //operations classiques liés aux comptes


}
