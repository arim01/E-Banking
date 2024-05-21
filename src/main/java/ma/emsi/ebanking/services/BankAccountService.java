package ma.emsi.ebanking.services;

import ma.emsi.ebanking.Exceptions.BalanceInsufisantException;
import ma.emsi.ebanking.Exceptions.BankAccountException;
import ma.emsi.ebanking.Exceptions.CustomerNotFoundExceptions;
import ma.emsi.ebanking.dtos.*;
import ma.emsi.ebanking.dtos.*;
import ma.emsi.ebanking.entities.BankAccount;
import ma.emsi.ebanking.entities.CurrentAccount;
import ma.emsi.ebanking.entities.Customer;
import ma.emsi.ebanking.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {
    CustomerDto saveCustomer(CustomerDto customerDto);
    CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws
            CustomerNotFoundExceptions;
    SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws
            CustomerNotFoundExceptions;
    List<CustomerDto> listCustomers();
    BankAccountDto getBankAccount(String accountId) throws BankAccountException;
    void debit(String accountId, double amount, String description) throws BankAccountException,
            BalanceInsufisantException;
    void credit(String accountId, double amount, String description) throws BankAccountException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws
            BankAccountException, BalanceInsufisantException;
    List<BankAccountDto> bankAccountList();
    CustomerDto getCustomer(Long customerId) throws CustomerNotFoundExceptions;
    CustomerDto updateCustomer(CustomerDto customerDto);
    void deleteCustomer(Long customerId);
    List<AccountOperationsDto> accountHistory(String accountId);
    AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountException;

    List<CustomerDto> searchCustomers(String keyword);
}
