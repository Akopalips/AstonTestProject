package ru.aston.testproj.service;

import ru.aston.testproj.domain.dto.account.AccountCreateDto;
import ru.aston.testproj.domain.dto.account.AccountDepositDto;
import ru.aston.testproj.domain.dto.account.AccountTransferDto;
import ru.aston.testproj.domain.dto.account.AccountWithdrawDto;
import ru.aston.testproj.domain.model.Account;
import ru.aston.testproj.exception.TestprojException;

/**
 * @author tuspring
 */
public interface AccountService {
	void create(AccountCreateDto dto);
	void deposit(AccountDepositDto dto) throws TestprojException;
	void withdraw(AccountWithdrawDto dto) throws TestprojException;
	void transfer(AccountTransferDto dto) throws TestprojException;
	Iterable<Account> list();
}