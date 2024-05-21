package ma.emsi.ebanking.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.ebanking.Exceptions.BankAccountException;
import ma.emsi.ebanking.Exceptions.CustomerNotFoundExceptions;
import ma.emsi.ebanking.dtos.AccountHistoryDto;
import ma.emsi.ebanking.dtos.AccountOperationsDto;
import ma.emsi.ebanking.dtos.BankAccountDto;
import ma.emsi.ebanking.dtos.CustomerDto;
import ma.emsi.ebanking.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor @Slf4j
@CrossOrigin("*")

public class BankAccountRestApiController {
    private BankAccountService service;

    @GetMapping("/accounts/{id}")
    public BankAccountDto getBankAccount(@PathVariable String id) throws BankAccountException {

        return service.getBankAccount(id);
    }

    @GetMapping("/accounts")
    public List<BankAccountDto> listeAccount(){
        return service.bankAccountList();
    }

    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationsDto> getHistory(@PathVariable String id){
        return service.accountHistory(id);
    }


    @GetMapping("/accounts/{id}/pageOperations")
    public AccountHistoryDto getAccountHistory(@PathVariable String id,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "5") int size) throws BankAccountException {
        return service.getAccountHistory(id,page,size);
    }

}
