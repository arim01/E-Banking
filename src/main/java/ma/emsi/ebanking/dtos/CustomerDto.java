package ma.emsi.ebanking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.ebanking.entities.BankAccount;

import java.util.List;

//mapping

////////
@Data

public class CustomerDto {


    private Long ID;
    private String nom;
    private String Email;

}

