package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.ACCOUNT_NOT_SELECTED;
import static ru.aston.testproj.util.Constants.EMPTY_DEPOSIT_VALUE;
import static ru.aston.testproj.util.Constants.NEGATIVE_DEPOSIT;

import lombok.Data;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author tuspring
 */
@Data
public class AccountDepositDto {

    @NotBlank(message = ACCOUNT_NOT_SELECTED)
    protected String name;

    @NotNull(message = EMPTY_DEPOSIT_VALUE)
    @Min(value = 0, message = NEGATIVE_DEPOSIT)
    protected BigDecimal deposit;
}