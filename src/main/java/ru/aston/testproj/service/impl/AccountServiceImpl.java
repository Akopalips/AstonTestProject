package ru.aston.testproj.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;

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
		log.info("Account with name {} to create.", dto.getName());
		Account newEntity = mapper.map(dto, Account.class);
		repository.save(newEntity);
		log.info("Account {} saved to db.", newEntity);
	}

	@Override
	public void deposit(AccountDepositDto dto) throws EntityNotFoundException {
		log.info("Start deposit to account {}.", dto.getName());
		Account dbEntity = getAccountOrThrowException(dto.getName());
		dbEntity.setFunds(dbEntity.getFunds() + dto.getDeposit());//todo value out of range
		repository.save(dbEntity);
		log.info("Deposit to account {} successful.", dto.getName());
	}

	@Override
	public void withdraw(AccountWithdrawDto dto) throws TestprojException {
		log.info("Start withdraw to account {}.", dto.getName());
		Account dbEntity = getAccountOrThrowException(dto.getName());

		checkEqualPinsOrElseThrowException(dto.getPin(), dbEntity.getPin());

		final Long sourceFunds = dbEntity.getFunds();
		final Long withdraw = dto.getWithdraw();
		checkEnoughFundsOrElseThrowException(dbEntity.getName(), sourceFunds, withdraw);

		dbEntity.setFunds(sourceFunds - withdraw);
		repository.save(dbEntity);
		log.info("Withdraw to account {} successful.", dto.getName());
	}

	@Override
	public void transfer(AccountTransferDto dto) throws TestprojException {
		log.info("Start transfer from {} to {}.", dto.getSourceAccountName(), dto.getTargetAccountName());
		Account dbEntitySource = getAccountOrThrowException(dto.getSourceAccountName());

		checkEqualPinsOrElseThrowException(dto.getSourceAccountPin(), dbEntitySource.getPin());

		final Long sourceFunds = dbEntitySource.getFunds();
		final Long transfer = dto.getTransfer();
		checkEnoughFundsOrElseThrowException(dbEntitySource.getName(), sourceFunds, transfer);

		Account dbEntityTarget = getAccountOrThrowException(dto.getTargetAccountName());
		dbEntitySource.setFunds(sourceFunds - transfer);
		dbEntityTarget.setFunds(dbEntityTarget.getFunds() + transfer);
		repository.saveAll(Arrays.asList(dbEntitySource, dbEntityTarget));
		log.info("Transfer from {} to {} complete.", dto.getSourceAccountName(), dto.getTargetAccountName());
	}

	@Override
	public Iterable<Account> list() {
			log.info("Get all accounts from db.");
		return repository.findAll();
	}

	private Account getAccountOrThrowException(String name) throws EntityNotFoundException {
		return repository.findByName(name).orElseThrow(() -> {
			log.info("No such entity {}.", name);
			return new EntityNotFoundException();
		});
	}

	private static void checkEqualPinsOrElseThrowException(String pin0, String pin1) throws WrongPinException {
		if (!pin0.equals(pin1)){
			log.info("Not equals pins {} and {}", pin0, pin1);
			throw new WrongPinException();
		}
	}

	private static void checkEnoughFundsOrElseThrowException(String accountName, Long currentAccountFunds, Long withdraw) throws NotEnoughFundsException {
		if (currentAccountFunds < withdraw){
			log.info("Source account {} has not enough funds: current {} transfer needed {}.",
					 accountName, currentAccountFunds, withdraw);
			throw new NotEnoughFundsException();
		}
	}
}