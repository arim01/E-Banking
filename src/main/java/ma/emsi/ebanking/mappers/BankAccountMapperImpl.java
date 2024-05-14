package ma.emsi.ebanking.mappers;

import ma.emsi.ebanking.dtos.CustomerDto;
import ma.emsi.ebanking.entities.Customer;
import org.springframework.beans.BeanUtils;
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
}
