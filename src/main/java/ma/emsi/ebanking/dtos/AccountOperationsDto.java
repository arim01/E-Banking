package ma.emsi.ebanking.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.ebanking.Enum.OperationType;
import ma.emsi.ebanking.entities.BankAccount;

import java.util.Date;


@Data

public class AccountOperationsDto {

    private Long id;
    private Date operationDate;
    private double amount;

    private OperationType type;

    private String description;

}
