package ru.aston.testproj.domain.dto.account;

import static ru.aston.testproj.util.Constants.EMPTY_NAME;
import static ru.aston.testproj.util.Constants.EMPTY_PIN_CODE;
import static ru.aston.testproj.util.Constants.INVALID_PIN_CODE;

import lombok.Data;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author tuspring
 */
@Data
public class AccountCreateDto {

    @NotBlank(message = EMPTY_NAME)
    protected String name;

    @NotNull(message = EMPTY_PIN_CODE)
    @Size(min = 4, max = 4, message = INVALID_PIN_CODE)
    protected String pin;

}
