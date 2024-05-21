package ma.emsi.ebanking.mappers;

import ma.emsi.ebanking.dtos.AccountOperationsDto;
import ma.emsi.ebanking.dtos.CurrentBankAccountDto;
import ma.emsi.ebanking.dtos.CustomerDto;
import ma.emsi.ebanking.dtos.SavingBankAccountDto;
import ma.emsi.ebanking.entities.AccountOperations;
import ma.emsi.ebanking.entities.CurrentAccount;
import ma.emsi.ebanking.entities.Customer;
import ma.emsi.ebanking.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl
{
    public CustomerDto fromCustomerToDTO(Customer c){
        CustomerDto customerDTO=new CustomerDto();
        BeanUtils.copyProperties(c,customerDTO);//source,destination
        return  customerDTO;

        //mapstruct peut faire ca aussi on donne seulement la signature
    }

    public Customer fromCustomerDTOToCus(CustomerDto c){
        Customer customer=new Customer();
        BeanUtils.copyProperties(c,customer);
        return  customer;
    }

    public SavingBankAccountDto fromSavingAccountTODTO(SavingAccount sv){
        SavingBankAccountDto c=new SavingBankAccountDto();
        BeanUtils.copyProperties(sv,c);
        c.setCustomerDto(fromCustomerToDTO(sv.getCustomer()));
        c.setType(sv.getClass().getSimpleName());
        return c;
    }

    public SavingAccount fromSavingAccountDTOTOsv(SavingBankAccountDto sv){
        SavingAccount s= new SavingAccount();
        BeanUtils.copyProperties(sv,s);
        s.setCustomer(fromCustomerDTOToCus(sv.getCustomerDto()));
        return s;
    }

    // current
    public CurrentBankAccountDto fromCurrentAccountTODTO(CurrentAccount ca){
        CurrentBankAccountDto c= new CurrentBankAccountDto();
        BeanUtils.copyProperties(ca,c);
        c.setCustomerDto(fromCustomerToDTO(ca.getCustomer()));
        c.setType(ca.getClass().getSimpleName());
        return c;
    }

    public CurrentAccount fromCurrentAccountDTOTOsv(CurrentBankAccountDto ca){
        CurrentAccount c= new CurrentAccount();
        BeanUtils.copyProperties(ca,c);
        c.setCustomer(fromCustomerDTOToCus(ca.getCustomerDto()));
        return c;
    }


    //operations
    public AccountOperationsDto fromAoperationstoDTO(AccountOperations ac){
        AccountOperationsDto a=new AccountOperationsDto();
        BeanUtils.copyProperties(ac,a);
        return a;
    }
    public AccountOperations fromAoperationsDTOtoAc(AccountOperationsDto ac){
        AccountOperations a=new AccountOperations();
        BeanUtils.copyProperties(ac,a);
        return a;
    }

}
