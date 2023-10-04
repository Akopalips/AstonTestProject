package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.ACCOUNT_NOT_SELECTED;
import static ru.aston.testproj.util.Constants.EMPTY_PIN_CODE;
import static ru.aston.testproj.util.Constants.EMPTY_WITHDRAW_VALUE;
import static ru.aston.testproj.util.Constants.INVALID_PIN_CODE;
import static ru.aston.testproj.util.Constants.NEGATIVE_WITHDRAW_VALUE;
import static ru.aston.testproj.util.Constants.PIN_REGEX;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * @author tuspring
 */
@Data
public class AccountWithdrawDto {

    @NotBlank(message = ACCOUNT_NOT_SELECTED)
    protected String name;

    @NotNull(message = EMPTY_WITHDRAW_VALUE)
    @Min(value = 0, message = NEGATIVE_WITHDRAW_VALUE)
    protected Long withdraw;

    @NotBlank(message = EMPTY_PIN_CODE)
    @Pattern(regexp = PIN_REGEX, message = INVALID_PIN_CODE)
    protected String pin;
}