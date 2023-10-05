package ru.aston.testproj.domain.dto.account;

import lombok.Data;
import java.math.BigDecimal;


/**
 * @author tuspring
 */
@Data
public class AccountGetDto {

    protected String name;

    protected BigDecimal funds;
}
