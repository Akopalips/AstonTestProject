package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.ACCOUNT_NOT_SELECTED;
import static ru.aston.testproj.util.Constants.EMPTY_DEPOSIT_VALUE;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * @author tuspring
 */
@Data
public class AccountDepositDto {

    @NotNull(message = ACCOUNT_NOT_SELECTED)
    protected String name;

    @NotNull(message = EMPTY_DEPOSIT_VALUE)
    @Min(value = 0, message = "Negative deposit.")
    protected Long deposit;
}