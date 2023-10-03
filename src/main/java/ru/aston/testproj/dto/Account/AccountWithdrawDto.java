package ru.aston.testproj.dto.Account;

import lombok.Data;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author tuspring
 */
@Data
public class AccountWithdrawDto {

    @NotNull
    protected UUID id;
    @NotNull(message = "Empty deposit.")
    @Min(value = 0, message = "Negative withdraw.")
    protected Long withdraw;
    @Size(min = 4, max = 4, message = "Invalid PIN code.")
    protected String PIN;

}