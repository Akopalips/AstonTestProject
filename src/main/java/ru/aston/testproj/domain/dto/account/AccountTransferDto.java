package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.ACCOUNT_NOT_SELECTED;
import static ru.aston.testproj.util.Constants.EMPTY_PIN_CODE;
import static ru.aston.testproj.util.Constants.EMPTY_TRANSFER_VALUE;
import static ru.aston.testproj.util.Constants.IDENTICAL_ACCOUNTS_REQUEST;
import static ru.aston.testproj.util.Constants.INVALID_PIN_CODE;
import static ru.aston.testproj.util.Constants.NEGATIVE_TRANSFER_VALUE;
import static ru.aston.testproj.util.Constants.PIN_REGEX;

import lombok.Data;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * @author tuspring
 */
@Data
public class AccountTransferDto {

    @NotBlank(message = ACCOUNT_NOT_SELECTED)
    protected String sourceAccountName;

    @NotBlank(message = ACCOUNT_NOT_SELECTED)
    protected String targetAccountName;

    @NotNull(message = EMPTY_TRANSFER_VALUE)
    @Min(value = 0, message = NEGATIVE_TRANSFER_VALUE)
    protected BigDecimal transfer;

    @NotBlank(message = EMPTY_PIN_CODE)
    @Pattern(regexp = PIN_REGEX, message = INVALID_PIN_CODE)
    protected String sourceAccountPin;

    @AssertFalse(message = IDENTICAL_ACCOUNTS_REQUEST)
    public boolean isIdenticalAccounts() {
        return null == sourceAccountName ||
            null == targetAccountName ||
            sourceAccountName.trim().equals(targetAccountName.trim());
    }
}