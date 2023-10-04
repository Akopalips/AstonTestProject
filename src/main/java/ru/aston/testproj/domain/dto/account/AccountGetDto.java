package ru.aston.testproj.domain.dto.account;

import lombok.Data;


/**
 * @author tuspring
 */
@Data
public class AccountGetDto {

    protected String name;

    protected Long funds;//todo по идее это два спареных целочисленных
}
