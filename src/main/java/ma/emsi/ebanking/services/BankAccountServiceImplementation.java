package ma.emsi.ebanking.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.ebanking.Enum.OperationType;
import ma.emsi.ebanking.Exceptions.BalanceInsufisantException;
import ma.emsi.ebanking.Exceptions.BankAccountException;
import ma.emsi.ebanking.Exceptions.CustomerNotFoundExceptions;
import ma.emsi.ebanking.dtos.CustomerDto;
import ma.emsi.ebanking.entities.*;
import ma.emsi.ebanking.mappers.BankAccountMapperImpl;
import ma.emsi.ebanking.repositories.AccountOperationsRepos;
import ma.emsi.ebanking.repositories.BankAccountRepos;
import ma.emsi.ebanking.repositories.CustomerRepos;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
//Injection des dependance par constructeur au lieu de autowired
@AllArgsConstructor @Slf4j
public class BankAccountServiceImplementation implements BankAccountService {


    private CustomerRepos cR;

    private BankAccountRepos bR;

    private AccountOperationsRepos aR;


    //Pour logger des messages @Slf4j
    //Log4J c'est un framework java qui permet de faire la journalisation


    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto c) {
        log.info("Saving new Customer");
        //transfert du dto vers l'entité
        Customer cus=dtoMapper.fromCustomerDTOToCus(c);
        Customer customerSAVED=cR.save(cus);
        return dtoMapper.fromCustomerToDTO(customerSAVED);
    }

    @Override
    public CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundExceptions {
        CurrentAccount compte=new CurrentAccount();
        Customer c=cR.findById(customerId).orElse(null);
        if(c ==null)
            throw new CustomerNotFoundExceptions("Client inexistant");

        compte.setId(UUID.randomUUID().toString());
        compte.setCreateDate(new Date());
        compte.setBalance(initialBalance);
        compte.setCustomer(c);
        compte.setOverDraft(overDraft);

        CurrentAccount savedCompte= bR.save(compte);
        return savedCompte;
    }

    @Override
    public SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundExceptions {
       SavingAccount compte=new SavingAccount();
        Customer c=cR.findById(customerId).orElse(null);
        if(c ==null)
            throw new CustomerNotFoundExceptions("Client inexistant");

        compte.setId(UUID.randomUUID().toString());
        compte.setCreateDate(new Date());
        compte.setBalance(initialBalance);
        compte.setCustomer(c);
        compte.setInterestRate(interestRate);

        SavingAccount savedCompte= bR.save(compte);
        return savedCompte;
    }


    @Override
    public List<CustomerDto> listCustomer() {
        List<Customer> list=cR.findAll();
        List<CustomerDto> customerDTOS = list.stream()
                .map(customer -> dtoMapper.fromCustomerToDTO(customer))
                .collect(Collectors.toList());


        return customerDTOS;
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountException {
        BankAccount b= bR.findById(accountId).orElseThrow(()->new BankAccountException("Compte inexistant"));

        return b;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountException, BalanceInsufisantException {
        BankAccount b= getBankAccount(accountId);
        if(b.getBalance()<amount)
            throw new BalanceInsufisantException("Solde insufisant");

        AccountOperations op=new AccountOperations();
        op.setType(OperationType.DEBIT);
        op.setAmount(amount);
        op.setDescription(description);
        op.setOperationDate(new Date());
        op.setBankAccount(b);
        aR.save(op);
        b.setBalance(b.getBalance()-amount);
        bR.save(b);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountException {
        BankAccount b= getBankAccount(accountId);

        AccountOperations op=new AccountOperations();
        op.setType(OperationType.CREDIT);
        op.setAmount(amount);
        op.setDescription(description);
        op.setOperationDate(new Date());
        op.setBankAccount(b);
        aR.save(op);
        b.setBalance(b.getBalance()+amount);
        bR.save(b);




    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountException, BalanceInsufisantException
    {
        debit(accountIdSource,amount,"Transfert vers : "+ accountIdDestination);
        credit(accountIdDestination,amount,"Transfert de : "+ accountIdSource);

    }

    @Override
    public List<BankAccount> bankAccountList(){
        return bR.findAll();
    }
    
    @Override
    public CustomerDto getCustomer(Long id) throws CustomerNotFoundExceptions
    {
        Customer c= cR.findById(id).orElseThrow(()->new CustomerNotFoundExceptions("Customer not found"));
        return dtoMapper.fromCustomerToDTO(c);
    }

    @Override
    public CustomerDto updateCustomer(CustomerDto c) {
        log.info("Updating an old Customer");
        //transfert du dto vers l'entité
        Customer cus=dtoMapper.fromCustomerDTOToCus(c);
        Customer customerSAVED=cR.save(cus);
        return dtoMapper.fromCustomerToDTO(customerSAVED);
    }

    @Override
    public void deleteCustomer(Long id)
    {
        cR.deleteById(id);
    }






}


