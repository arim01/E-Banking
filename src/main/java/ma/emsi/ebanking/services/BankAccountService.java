package ma.emsi.ebanking.services;

import ma.emsi.ebanking.Exceptions.BalanceInsufisantException;
import ma.emsi.ebanking.Exceptions.BankAccountException;
import ma.emsi.ebanking.Exceptions.CustomerNotFoundExceptions;
import ma.emsi.ebanking.dtos.CustomerDto;
import ma.emsi.ebanking.entities.BankAccount;
import ma.emsi.ebanking.entities.CurrentAccount;
import ma.emsi.ebanking.entities.Customer;
import ma.emsi.ebanking.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {
    CustomerDto saveCustomer(CustomerDto c);
    CurrentAccount saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundExceptions;

    SavingAccount saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundExceptions;
    List<CustomerDto> listCustomer();
    BankAccount getBankAccount(String accountId) throws BankAccountException;
    void debit(String accountId,double amount,String description) throws BankAccountException, BalanceInsufisantException;
    void credit(String accountId,double amount,String description) throws BankAccountException;
    void transfert(String accountIdSource,String accountIdDestination,double amount) throws BankAccountException, BalanceInsufisantException;


    List<BankAccount> bankAccountList();

    CustomerDto getCustomer(Long id) throws CustomerNotFoundExceptions;

    CustomerDto updateCustomer(CustomerDto c);

    void deleteCustomer(Long id);
}
