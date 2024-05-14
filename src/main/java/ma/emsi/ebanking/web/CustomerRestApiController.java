package ma.emsi.ebanking.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.ebanking.Exceptions.CustomerNotFoundExceptions;
import ma.emsi.ebanking.dtos.CustomerDto;
import ma.emsi.ebanking.entities.Customer;
import ma.emsi.ebanking.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor @Slf4j
public class CustomerRestApiController {
    private BankAccountService service;


    @GetMapping("customers")
    public List<CustomerDto> liste(){
        return service.listCustomer();
    }

    @GetMapping("/customers/{id}")
    public CustomerDto getCustomer(@PathVariable(name="id") Long Cid) throws CustomerNotFoundExceptions {
        return service.getCustomer(Cid);
    }

    @PostMapping("/customers")
    public CustomerDto saveCustomer(@RequestBody CustomerDto request){
        return service.saveCustomer(request);
    }

    @PutMapping("/customers/{id}") // ca porte le meme nom pas la peine de mettre name
    public CustomerDto updateCustomer(@PathVariable Long id,@RequestBody CustomerDto c){
        c.setID(id);
        return service.updateCustomer(c);

    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(Long id)
    {
        service.deleteCustomer(id);
    }
}
