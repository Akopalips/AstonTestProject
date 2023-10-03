package ru.aston.testproj.dto.Account;

import static ru.aston.testproj.util.ResponceConstants.INVALID_PIN_CODE;

import lombok.Data;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author tuspring
 */
@Data
public class AccountTransferDto {

    @NotNull
    protected UUID idFrom;
    @NotNull
    protected UUID idTo;
    @NotNull(message = "Empty transfer.")
    @Min(value = 0, message = "Negative transfer.")
    protected Long transfer;
    @Size(min = 4, max = 4, message = INVALID_PIN_CODE)
    protected String PINFrom;

}