package ru.aston.testproj.service.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import ru.aston.testproj.domain.dto.account.AccountCreateDto;
import ru.aston.testproj.domain.dto.account.AccountDepositDto;
import ru.aston.testproj.domain.dto.account.AccountTransferDto;
import ru.aston.testproj.domain.dto.account.AccountWithdrawDto;
import ru.aston.testproj.domain.model.Account;
import ru.aston.testproj.exception.EntityNotFoundException;
import ru.aston.testproj.exception.NotEnoughFundsException;
import ru.aston.testproj.exception.WrongPinException;
import ru.aston.testproj.repository.AccountRepository;
import ru.aston.testproj.util.AccountMapper;

/**
 * @author tuspring
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTests {

    @InjectMocks
    private AccountServiceImpl service;
    @Mock
    private AccountRepository repository;
    @Spy
    private AccountMapper mapper = new AccountMapper();

    //------------------------------------CREATE------------------------------------
    @Test
    public void create_positive_saved() throws Exception {
        //Given
        AccountCreateDto dto = new AccountCreateDto();
        dto.setName("123");
        dto.setPin("1234");
        doReturn(new Account()).when(repository).save(any());
        //When
        service.create(dto);
        //Then
        verify(repository, times(1)).save(any());
    }

    //------------------------------------deposit------------------------------------
    @Test
    public void deposit_positive_saved() throws Exception {
        //Given
        AccountDepositDto dto = new AccountDepositDto();
        dto.setName("123");
        dto.setDeposit(BigDecimal.valueOf(1234));
        doReturn(Optional.of(new Account())).when(repository).findByName(any());
        doReturn(new Account()).when(repository).save(any());
        //When
        service.deposit(dto);
        //Then
        verify(repository, times(1)).save(any());
    }

    @Test
    public void deposit_negative_EntityNotFound() throws Exception {
        //Given
        AccountDepositDto dto = new AccountDepositDto();
        dto.setName("123");
        dto.setDeposit(BigDecimal.valueOf(1234));
        doReturn(Optional.empty()).when(repository).findByName(any());

        //When-Then
        assertThrows(EntityNotFoundException.class, () -> service.deposit(dto));
        verify(repository, never()).save(any());
    }

    //------------------------------------deposit------------------------------------
    @Test
    public void withdraw_positive_saved() throws Exception {
        //Given
        AccountWithdrawDto dto = new AccountWithdrawDto();
        dto.setName("123");
        dto.setWithdraw(BigDecimal.valueOf(1234));
        dto.setPin("1234");

        Account entity = new Account();
        entity.setId(UUID.randomUUID());
        entity.setName("123");
        entity.setPin("1234");
        entity.setFunds(BigDecimal.valueOf(5000));

        doReturn(Optional.of(entity)).when(repository).findByName(any());
        doReturn(new Account()).when(repository).save(any());
        //When
        service.withdraw(dto);
        //Then
        verify(repository, times(1)).save(any());
    }

    @Test
    public void withdraw_negative_EntityNotFound() throws Exception {
        //Given
        AccountWithdrawDto dto = new AccountWithdrawDto();
        dto.setName("123");
        dto.setWithdraw(BigDecimal.valueOf(1234));
        dto.setPin("1234");

        doReturn(Optional.empty()).when(repository).findByName(any());
        //Then-When
        assertThrows(EntityNotFoundException.class, () -> service.withdraw(dto));
        verify(repository, never()).save(any());
    }

    @Test
    public void withdraw_negative_NotEnoughFundsException() throws Exception {
        //Given
        AccountWithdrawDto dto = new AccountWithdrawDto();
        dto.setName("123");
        dto.setWithdraw(BigDecimal.valueOf(1234));
        dto.setPin("1234");

        Account entity = new Account();
        entity.setId(UUID.randomUUID());
        entity.setName("123");
        entity.setPin("1234");
        entity.setFunds(BigDecimal.valueOf(500));

        doReturn(Optional.of(entity)).when(repository).findByName(any());
        //Then-When
        assertThrows(NotEnoughFundsException.class, () -> service.withdraw(dto));
        verify(repository, never()).save(any());
    }

    @Test
    public void withdraw_negative_WrongPinException() throws Exception {
        //Given
        AccountWithdrawDto dto = new AccountWithdrawDto();
        dto.setName("123");
        dto.setWithdraw(BigDecimal.valueOf(1234));
        dto.setPin("1234");

        Account entity = new Account();
        entity.setId(UUID.randomUUID());
        entity.setName("123");
        entity.setPin("1235");
        entity.setFunds(BigDecimal.valueOf(5000));

        doReturn(Optional.of(entity)).when(repository).findByName(any());
        //Then-When
        assertThrows(WrongPinException.class, () -> service.withdraw(dto));
        verify(repository, never()).save(any());
    }

    //------------------------------------transfer------------------------------------
    @Test
    public void transfer_positive_saved() throws Exception {
        //Given
        AccountTransferDto dto = new AccountTransferDto();
        dto.setSourceAccountName("Source");
        dto.setTargetAccountName("Target");
        dto.setTransfer(BigDecimal.valueOf(1234));
        dto.setSourceAccountPin("1234");

        Account entitySource = new Account();
        entitySource.setId(UUID.randomUUID());
        entitySource.setName("Source");
        entitySource.setPin("1234");
        entitySource.setFunds(BigDecimal.valueOf(5000));

        Account entityTarget = new Account();
        entityTarget.setId(UUID.randomUUID());
        entityTarget.setName("Target");
        entityTarget.setPin("1234");
        entityTarget.setFunds(BigDecimal.valueOf(5000));

        doReturn(Optional.of(entitySource)).when(repository).findByName("Source");
        doReturn(Optional.of(entityTarget)).when(repository).findByName("Target");
        doReturn(new ArrayList<Account>()).when(repository).saveAll(any());
        //When
        service.transfer(dto);
        //Then
        verify(repository, times(1)).saveAll(any());
    }

    @Test
    public void transfer_negative_SourceEntityNotFound() throws Exception {
        //Given
        AccountTransferDto dto = new AccountTransferDto();
        dto.setSourceAccountName("Source");
        dto.setTargetAccountName("Target");
        dto.setTransfer(BigDecimal.valueOf(1234));
        dto.setSourceAccountPin("1234");

        Account entitySource = new Account();
        entitySource.setId(UUID.randomUUID());
        entitySource.setName("Source");
        entitySource.setPin("1234");
        entitySource.setFunds(BigDecimal.valueOf(5000));

        doReturn(Optional.empty()).when(repository).findByName("Source");

        //Then-When
        assertThrows(EntityNotFoundException.class, () -> service.transfer(dto));
        verify(repository, never()).saveAll(any());
    }

    @Test
    public void transfer_negative_WrongPinException() throws Exception {
        //Given
        AccountTransferDto dto = new AccountTransferDto();
        dto.setSourceAccountName("Source");
        dto.setTargetAccountName("Target");
        dto.setTransfer(BigDecimal.valueOf(1234));
        dto.setSourceAccountPin("1234");

        Account entitySource = new Account();
        entitySource.setId(UUID.randomUUID());
        entitySource.setName("Source");
        entitySource.setPin("1235");
        entitySource.setFunds(BigDecimal.valueOf(5000));

        doReturn(Optional.of(entitySource)).when(repository).findByName("Source");

        //Then-When
        assertThrows(WrongPinException.class, () -> service.transfer(dto));
        verify(repository, never()).saveAll(any());
    }

    @Test
    public void transfer_negative_NotEnoughFundsException() throws Exception {
        //Given
        AccountTransferDto dto = new AccountTransferDto();
        dto.setSourceAccountName("Source");
        dto.setTargetAccountName("Target");
        dto.setTransfer(BigDecimal.valueOf(1234));
        dto.setSourceAccountPin("1234");

        Account entitySource = new Account();
        entitySource.setId(UUID.randomUUID());
        entitySource.setName("Source");
        entitySource.setPin("1234");
        entitySource.setFunds(BigDecimal.valueOf(500));

        doReturn(Optional.of(entitySource)).when(repository).findByName("Source");

        //Then-When
        assertThrows(NotEnoughFundsException.class, () -> service.transfer(dto));
        verify(repository, never()).saveAll(any());
    }

    @Test
    public void transfer_negative_TargetEntityNotFoundException() throws Exception {
        //Given
        AccountTransferDto dto = new AccountTransferDto();
        dto.setSourceAccountName("Source");
        dto.setTargetAccountName("Target");
        dto.setTransfer(BigDecimal.valueOf(1234));
        dto.setSourceAccountPin("1234");

        Account entitySource = new Account();
        entitySource.setId(UUID.randomUUID());
        entitySource.setName("Source");
        entitySource.setPin("1234");
        entitySource.setFunds(BigDecimal.valueOf(5000));

        doReturn(Optional.of(entitySource)).when(repository).findByName("Source");
        doReturn(Optional.empty()).when(repository).findByName("Target");

        //Then-When
        assertThrows(EntityNotFoundException.class, () -> service.transfer(dto));
        verify(repository, never()).saveAll(any());
    }

    //------------------------------------list------------------------------------
    @Test
    public void list_positive_findAll() throws Exception {
        //Given
        doReturn(new ArrayList<>()).when(repository).findAll();
        //When
        service.list();
        //Then
        verify(repository, times(1)).findAll();
    }
}