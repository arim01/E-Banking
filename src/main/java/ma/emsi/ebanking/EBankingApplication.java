package ma.emsi.ebanking;

import ma.emsi.ebanking.Enum.AccountStatus;
import ma.emsi.ebanking.Enum.OperationType;
import ma.emsi.ebanking.Exceptions.BalanceInsufisantException;
import ma.emsi.ebanking.Exceptions.BankAccountException;
import ma.emsi.ebanking.Exceptions.CustomerNotFoundExceptions;
import ma.emsi.ebanking.dtos.CustomerDto;
import ma.emsi.ebanking.entities.*;
import ma.emsi.ebanking.repositories.AccountOperationsRepos;
import ma.emsi.ebanking.repositories.BankAccountRepos;
import ma.emsi.ebanking.repositories.CustomerRepos;
import ma.emsi.ebanking.services.BankAccountService;
import ma.emsi.ebanking.services.BankServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingApplication {

    public static void main(String[] args) {

        SpringApplication.run(EBankingApplication.class, args);
    }


    //Test de la couche service
    @Bean
    CommandLineRunner CommandlineRunner(BankAccountService b)
    {
        return args->{
            //client
            Stream.of("Arij","Mehdi","Lina").forEach(name->{
                CustomerDto c= new CustomerDto();
                c.setNom(name);
                c.setEmail(name+"@gmail.com");
                b.saveCustomer(c);

            });

            //liste clients
            b.listCustomer().forEach(customer -> {

                try
                {
                    b.saveCurrentAccount(Math.random()*9000,
                            9000,
                            customer.getID());
                    b.saveSavingAccount(Math.random()*9000,
                            5.5,
                            customer.getID());
                    List<BankAccount> liste=b.bankAccountList();
                    for(BankAccount compte : liste)
                    {
                        for(int i=0;i<10;i++){
                           b.credit(compte.getId(),10000+Math.random()*12000,"Credit");
                           b.debit(compte.getId(),1000+Math.random()*9000,"DEBIT");
                        }
                    }





                    b.bankAccountList().forEach(account -> {

                    });
                }
                catch (CustomerNotFoundExceptions e)
                {
                    e.printStackTrace();
                }
                catch (BankAccountException e)
                {
                    e.printStackTrace();
                }
                catch (BalanceInsufisantException e)
                {
                    e.printStackTrace();
                }


            });

        };
    }

    //Enregistrement
    //@Bean
    CommandLineRunner start(CustomerRepos cr,
                            AccountOperationsRepos acr,
                            BankAccountRepos br)
    {
        return args -> {
            Stream.of("Arij","Mehdi","Lina").forEach(name->{
                Customer c= new Customer();
                c.setNom(name);
                c.setEmail(name+"@gmail.com");

                cr.save(c);
            });


            //pour chaque client, on cree des comptes (current et saving)
            cr.findAll().forEach(customer -> {
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());//random id
                currentAccount.setBalance(Math.random()*9000);
                currentAccount.setCreateDate(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                br.save(currentAccount);


                SavingAccount savingAccount= new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCustomer(customer);
                savingAccount.setBalance(Math.random()*9000);
                savingAccount.setCreateDate(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                br.save(savingAccount);
            });

            //pour chaque compte on cree 10 operations
            //une operation concerne un compte
            br.findAll().forEach(account->{
                for(int i=0;i<=10;i++){
                    AccountOperations ac= new AccountOperations();
                    ac.setOperationDate(new Date());
                    ac.setAmount(Math.random()*1200);
                    ac.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
                    ac.setBankAccount(account);

                    acr.save(ac);
                }
            });






        };
    }

    /*
    //consultation
    @Bean
    CommandLineRunner CommandlineRunner(BankServices b)
    {
        return args->{
            b.consulter();
        };
    }*/
}
