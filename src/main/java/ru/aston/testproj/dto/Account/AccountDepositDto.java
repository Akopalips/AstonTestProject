package ru.aston.testproj.dto.Account;

import lombok.Data;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @author tuspring
 */
@Data
public class AccountDepositDto {

    @NotNull
    protected UUID id;
    @NotNull(message = "Empty deposit.")
    @Min(value = 0, message = "Negative deposit.")
    protected Long deposit;

}