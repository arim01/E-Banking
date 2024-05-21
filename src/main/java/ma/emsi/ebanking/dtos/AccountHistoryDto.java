package ma.emsi.ebanking.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDto {
    private String accountID;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationsDto> operationsdto;
}
