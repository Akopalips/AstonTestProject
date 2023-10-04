package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.ACCOUNT_NOT_SELECTED;
import static ru.aston.testproj.util.Constants.EMPTY_PIN_CODE;
import static ru.aston.testproj.util.Constants.EMPTY_TRANSFER_VALUE;
import static ru.aston.testproj.util.Constants.INVALID_PIN_CODE;
import static ru.aston.testproj.util.Constants.NEGATIVE_TRANSFER_VALUE;

import lombok.Data;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author tuspring
 */
@Data
public class AccountTransferDto {

    @NotNull(message = ACCOUNT_NOT_SELECTED)
    protected String sourceAccountName;

    @NotNull(message = ACCOUNT_NOT_SELECTED)
    protected String targetAccountName;

    @NotNull(message = EMPTY_TRANSFER_VALUE)
    @Min(value = 0, message = NEGATIVE_TRANSFER_VALUE)
    protected Long transfer;

    @NotNull(message = EMPTY_PIN_CODE)
    @Size(min = 4, max = 4, message = INVALID_PIN_CODE)
    protected String sourceAccountPin;

    @AssertFalse(message = "Identical accounts request.")
    public boolean isIdenticalAccounts(){
        return null == sourceAccountName ||
            null == targetAccountName ||
            sourceAccountName.trim().equals(targetAccountName.trim());
    }
}