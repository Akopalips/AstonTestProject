package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.ACCOUNT_NOT_SELECTED;
import static ru.aston.testproj.util.Constants.EMPTY_PIN_CODE;
import static ru.aston.testproj.util.Constants.INVALID_PIN_CODE;
import static ru.aston.testproj.util.Constants.PIN_REGEX;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * @author tuspring
 */
@Data
public class AccountCreateDto {

    @NotBlank(message = ACCOUNT_NOT_SELECTED)
    protected String name;

    @NotBlank(message = EMPTY_PIN_CODE)
    @Pattern(regexp = PIN_REGEX, message = INVALID_PIN_CODE)
    protected String pin;
}
