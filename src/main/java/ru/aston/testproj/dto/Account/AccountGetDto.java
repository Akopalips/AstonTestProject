package ru.aston.testproj.dto.Account;

import lombok.Data;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author tuspring
 */
@Data
public class AccountGetDto {

    protected String name;

    protected Long founds;//todo по идее это два спареных целочисленных


}
