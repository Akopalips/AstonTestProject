package ru.aston.testproj.util;

import org.springframework.stereotype.Component;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ru.aston.testproj.domain.dto.account.AccountCreateDto;
import ru.aston.testproj.domain.dto.account.AccountGetDto;
import ru.aston.testproj.domain.model.Account;

/**
 * @author tuspring
 */
@Component
public class AccountMapper extends ConfigurableMapper {

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
        factoryBuilder
            .mapNulls(false)
            .build();
    }

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Account.class, AccountCreateDto.class)
            .mapNullsInReverse(false)
            .byDefault().register();

        factory.classMap(Account.class, AccountGetDto.class)
            .mapNullsInReverse(false)
            .byDefault().register();
    }
}
