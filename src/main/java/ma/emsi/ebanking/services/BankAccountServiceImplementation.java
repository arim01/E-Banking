package ma.emsi.ebanking.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.ebanking.Enum.OperationType;
import ma.emsi.ebanking.Exceptions.BalanceInsufisantException;
import ma.emsi.ebanking.Exceptions.BankAccountException;
import ma.emsi.ebanking.Exceptions.CustomerNotFoundExceptions;
import ma.emsi.ebanking.dtos.*;
import ma.emsi.ebanking.entities.*;
import ma.emsi.ebanking.mappers.BankAccountMapperImpl;
import ma.emsi.ebanking.repositories.AccountOperationsRepos;
import ma.emsi.ebanking.repositories.BankAccountRepos;
import ma.emsi.ebanking.repositories.CustomerRepos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private CustomerRepos customerRepository;
    private BankAccountRepos bankAccountRepository;
    private AccountOperationsRepos accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
    @Override
    public CustomerDto saveCustomer(CustomerDto customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTOToCus(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomerToDTO(savedCustomer);
    }
    @Override
    public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
            throws CustomerNotFoundExceptions {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundExceptions("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreateDate(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentAccountTODTO(savedBankAccount);
    }
    @Override
    public SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
            throws CustomerNotFoundExceptions {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundExceptions("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreateDate(new Date());
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setBalance(initialBalance);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingAccountTODTO(savedBankAccount);
    }
    @Override
    public List<CustomerDto> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDto> customerDTOS = customers.stream()
                .map(customer-> dtoMapper.fromCustomerToDTO(customer))
                .collect(Collectors.toList());
        return customerDTOS;
    }
    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return dtoMapper.fromSavingAccountTODTO(savingAccount);
        } else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentAccountTODTO(currentAccount);
        }
    }
    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountException,
            BalanceInsufisantException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceInsufisantException("Balance not sufficient");
        AccountOperations accountOperation=new AccountOperations();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperationRepository.save(accountOperation);
        bankAccountRepository.save(bankAccount);
        accountOperation.setBankAccount(bankAccount);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
    }
    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountException("BankAccount not found"));
        AccountOperations accountOperation=new AccountOperations();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }
    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws
            BankAccountException, BalanceInsufisantException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }
    @Override
    public List<BankAccountDto> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDto> bankAccountDTOS = bankAccounts.stream().map(bankAccount-> {
            if (bankAccount instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return dtoMapper.fromSavingAccountTODTO(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentAccountTODTO(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOS;
    }
    @Override
    public CustomerDto getCustomer(Long customerId) throws CustomerNotFoundExceptions {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundExceptions("Customer Not found"));
        return dtoMapper.fromCustomerToDTO(customer);
    }
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTOToCus(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomerToDTO(savedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }
    @Override
    public List<AccountOperationsDto> accountHistory(String accountId){
        List<AccountOperations> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAoperationstoDTO(op)).collect(Collectors.toList());
    }
    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) throws BankAccountException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountException("Account not Found");
        Page<AccountOperations> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDto accountHistoryDTO=new AccountHistoryDto();
        List<AccountOperationsDto> accountOperationDTOS = accountOperations.getContent().stream().map(op ->
                dtoMapper.fromAoperationstoDTO(op)).collect(Collectors.toList());
        accountHistoryDTO.setOperationsdto(accountOperationDTOS);
        accountHistoryDTO.setAccountID(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDto> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
        List<CustomerDto> customerDTOS = customers.stream().map(cust -> dtoMapper.fromCustomerToDTO(cust)).collect(Collectors.toList());
        return customerDTOS;
    }


}


