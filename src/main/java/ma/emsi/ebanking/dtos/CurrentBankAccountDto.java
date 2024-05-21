package ma.emsi.ebanking.dtos;


import lombok.Data;
import ma.emsi.ebanking.Enum.AccountStatus;

import java.util.Date;


@Data //getter et setter

public class CurrentBankAccountDto extends BankAccountDto {


    private String id;
    private double balance;
    private Date createDate;
    private AccountStatus status;
    private CustomerDto customerDto;
    private double overDraft;





}
