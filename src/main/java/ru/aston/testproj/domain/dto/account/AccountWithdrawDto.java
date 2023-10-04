package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.ACCOUNT_NOT_SELECTED;
import static ru.aston.testproj.util.Constants.EMPTY_PIN_CODE;
import static ru.aston.testproj.util.Constants.EMPTY_WITHDRAW_VALUE;
import static ru.aston.testproj.util.Constants.INVALID_PIN_CODE_LENGTH;
import static ru.aston.testproj.util.Constants.NEGATIVE_WITHDRAW_VALUE;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author tuspring
 */
@Data
public class AccountWithdrawDto {

    @NotNull(message = ACCOUNT_NOT_SELECTED)
    protected String name;

    @NotNull(message = EMPTY_WITHDRAW_VALUE)
    @Min(value = 0, message = NEGATIVE_WITHDRAW_VALUE)
    protected Long withdraw;

    @NotNull(message = EMPTY_PIN_CODE)
    @Size(min = 4, max = 4, message = INVALID_PIN_CODE_LENGTH)
    protected String pin;
}