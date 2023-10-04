package ru.aston.testproj.service.impl;

import static ru.aston.testproj.util.Constants.ACCOUNT_SAVED_TO_DB;
import static ru.aston.testproj.util.Constants.ACCOUNT_WITH_NAME_TO_CREATE;
import static ru.aston.testproj.util.Constants.DEPOSIT_TO_ACCOUNT_SUCCESSFUL;
import static ru.aston.testproj.util.Constants.GET_ALL_ACCOUNTS_FROM_DB;
import static ru.aston.testproj.util.Constants.NOT_EQUALS_PINS_AND;
import static ru.aston.testproj.util.Constants.NO_SUCH_ENTITY;
import static ru.aston.testproj.util.Constants.SOURCE_ACCOUNT_HAS_NOT_ENOUGH_FUNDS_CURRENT_TRANSFER_NEEDED;
import static ru.aston.testproj.util.Constants.START_DEPOSIT_TO_ACCOUNT;
import static ru.aston.testproj.util.Constants.START_TRANSFER_FROM_TO;
import static ru.aston.testproj.util.Constants.START_WITHDRAW_TO_ACCOUNT;
import static ru.aston.testproj.util.Constants.TRANSFER_FROM_TO_COMPLETE;
import static ru.aston.testproj.util.Constants.WITHDRAW_TO_ACCOUNT_SUCCESSFUL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.Arrays;

import ru.aston.testproj.domain.dto.account.AccountCreateDto;
import ru.aston.testproj.domain.dto.account.AccountDepositDto;
import ru.aston.testproj.domain.dto.account.AccountTransferDto;
import ru.aston.testproj.domain.dto.account.AccountWithdrawDto;
import ru.aston.testproj.domain.model.Account;
import ru.aston.testproj.exception.EntityNotFoundException;
import ru.aston.testproj.exception.NotEnoughFundsException;
import ru.aston.testproj.exception.TestprojException;
import ru.aston.testproj.exception.WrongPinException;
import ru.aston.testproj.repository.AccountRepository;
import ru.aston.testproj.service.AccountService;
import ru.aston.testproj.util.AccountMapper;

/**
 * @author tuspring
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private AccountMapper mapper;

    @Override
    public void create(AccountCreateDto dto) {
        log.info(ACCOUNT_WITH_NAME_TO_CREATE, dto.getName());
        Account newEntity = mapper.map(dto, Account.class);
        repository.save(newEntity);
        log.info(ACCOUNT_SAVED_TO_DB, newEntity);
    }

    @Override
    public void deposit(AccountDepositDto dto) throws EntityNotFoundException {
        log.info(START_DEPOSIT_TO_ACCOUNT, dto.getName());
        Account dbEntity = getAccountOrThrowException(dto.getName());
        dbEntity.setFunds(dbEntity.getFunds() + dto.getDeposit());//todo value out of range
        repository.save(dbEntity);
        log.info(DEPOSIT_TO_ACCOUNT_SUCCESSFUL, dto.getName());
    }

    @Override
    public void withdraw(AccountWithdrawDto dto) throws TestprojException {
        log.info(START_WITHDRAW_TO_ACCOUNT, dto.getName());
        Account dbEntity = getAccountOrThrowException(dto.getName());

        checkEqualPinsOrElseThrowException(dto.getPin(), dbEntity.getPin());

        final Long sourceFunds = dbEntity.getFunds();
        final Long withdraw = dto.getWithdraw();
        checkEnoughFundsOrElseThrowException(dbEntity.getName(), sourceFunds, withdraw);

        dbEntity.setFunds(sourceFunds - withdraw);
        repository.save(dbEntity);
        log.info(WITHDRAW_TO_ACCOUNT_SUCCESSFUL, dto.getName());
    }

    @Override
    public void transfer(AccountTransferDto dto) throws TestprojException {
        log.info(START_TRANSFER_FROM_TO, dto.getSourceAccountName(), dto.getTargetAccountName());
        Account dbEntitySource = getAccountOrThrowException(dto.getSourceAccountName());

        checkEqualPinsOrElseThrowException(dto.getSourceAccountPin(), dbEntitySource.getPin());

        final Long sourceFunds = dbEntitySource.getFunds();
        final Long transfer = dto.getTransfer();
        checkEnoughFundsOrElseThrowException(dbEntitySource.getName(), sourceFunds, transfer);

        Account dbEntityTarget = getAccountOrThrowException(dto.getTargetAccountName());
        dbEntitySource.setFunds(sourceFunds - transfer);
        dbEntityTarget.setFunds(dbEntityTarget.getFunds() + transfer);
        repository.saveAll(Arrays.asList(dbEntitySource, dbEntityTarget));
        log.info(TRANSFER_FROM_TO_COMPLETE, dto.getSourceAccountName(), dto.getTargetAccountName());
    }

    @Override
    public Iterable<Account> list() {
        log.info(GET_ALL_ACCOUNTS_FROM_DB);
        return repository.findAll();
    }

    private Account getAccountOrThrowException(String name) throws EntityNotFoundException {
        return repository.findByName(name).orElseThrow(() -> {
            log.info(NO_SUCH_ENTITY, name);
            return new EntityNotFoundException();
        });
    }

    private static void checkEqualPinsOrElseThrowException(String pin0, String pin1) throws WrongPinException {
        if (!pin0.equals(pin1)) {
            log.info(NOT_EQUALS_PINS_AND, pin0, pin1);
            throw new WrongPinException();
        }
    }

    private static void checkEnoughFundsOrElseThrowException(String accountName, Long currentAccountFunds,
                                                             Long withdraw) throws NotEnoughFundsException {
        if (currentAccountFunds < withdraw) {
            log.info(SOURCE_ACCOUNT_HAS_NOT_ENOUGH_FUNDS_CURRENT_TRANSFER_NEEDED,
                     accountName, currentAccountFunds, withdraw);
            throw new NotEnoughFundsException();
        }
    }
}