package ma.emsi.ebanking.dtos;


import lombok.Data;

import ma.emsi.ebanking.Enum.AccountStatus;
import ma.emsi.ebanking.entities.BankAccount;
import ma.emsi.ebanking.entities.Customer;

import java.util.Date;


@Data //getter et setter

public class SavingBankAccountDto extends BankAccountDto {


    private String id;
    private double balance;
    private Date createDate;
    private AccountStatus status;
    private CustomerDto customerDto;
    private double interestRate;





}
