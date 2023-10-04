package ru.aston.testproj.rest;

import static ru.aston.testproj.util.Constants.CREATED;
import static ru.aston.testproj.util.Constants.RECEIVED_REQUEST_TO_PRINT_ACCOUNTS;
import static ru.aston.testproj.util.Constants.RECEIVED_REQUEST_WITH_DTO_TO_CREATE_ACCOUNT;
import static ru.aston.testproj.util.Constants.RECEIVED_REQUEST_WITH_DTO_TO_DEPOSIT_FUNDS;
import static ru.aston.testproj.util.Constants.RECEIVED_REQUEST_WITH_DTO_TO_TRANSFER_FUNDS;
import static ru.aston.testproj.util.Constants.RECEIVED_REQUEST_WITH_DTO_TO_WITHDRAW_FUNDS;
import static ru.aston.testproj.util.Constants.SUCCESSFUL_DEPOSIT;
import static ru.aston.testproj.util.Constants.SUCCESSFUL_TRANSFER;
import static ru.aston.testproj.util.Constants.SUCCESSFUL_WITHDRAW;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import jakarta.validation.Valid;
import ru.aston.testproj.domain.dto.MessageDto;
import ru.aston.testproj.domain.dto.account.AccountCreateDto;
import ru.aston.testproj.domain.dto.account.AccountDepositDto;
import ru.aston.testproj.domain.dto.account.AccountGetDto;
import ru.aston.testproj.domain.dto.account.AccountTransferDto;
import ru.aston.testproj.domain.dto.account.AccountWithdrawDto;
import ru.aston.testproj.exception.TestprojException;
import ru.aston.testproj.repository.AccountRepository;
import ru.aston.testproj.service.AccountService;
import ru.aston.testproj.util.AccountMapper;

/**
 * @author tuspring
 */
@ResponseStatus(HttpStatus.OK)
@RestController
@RequestMapping(value = "/account")
@Slf4j
public class AccountController {

    @Autowired
    private AccountRepository repository;
    @Autowired
    private AccountService service;
    @Autowired
    private AccountMapper mapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto createAccount(@Valid @RequestBody AccountCreateDto dto) {
        dto.setName(dto.getName().trim());
        log.info(RECEIVED_REQUEST_WITH_DTO_TO_CREATE_ACCOUNT, dto);
        service.create(dto);
        return new MessageDto(CREATED);
    }

    @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto depositFounds(@Valid @RequestBody AccountDepositDto dto) throws TestprojException {
        dto.setName(dto.getName().trim());
        log.info(RECEIVED_REQUEST_WITH_DTO_TO_DEPOSIT_FUNDS, dto);
        service.deposit(dto);
        return new MessageDto(SUCCESSFUL_DEPOSIT);
    }

    @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto withdrawFounds(@Valid @RequestBody AccountWithdrawDto dto) throws TestprojException {
        dto.setName(dto.getName().trim());
        log.info(RECEIVED_REQUEST_WITH_DTO_TO_WITHDRAW_FUNDS, dto);
        service.withdraw(dto);
        return new MessageDto(SUCCESSFUL_WITHDRAW);
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto transferFounds(@Valid @RequestBody AccountTransferDto dto) throws TestprojException {
        dto.setSourceAccountName(dto.getSourceAccountName().trim());
        dto.setTargetAccountName(dto.getTargetAccountName().trim());
        service.transfer(dto);
        log.info(RECEIVED_REQUEST_WITH_DTO_TO_TRANSFER_FUNDS, dto);
        return new MessageDto(SUCCESSFUL_TRANSFER);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<AccountGetDto> list() {
        log.info(RECEIVED_REQUEST_TO_PRINT_ACCOUNTS);
        return mapper.mapAsList(service.list(), AccountGetDto.class);
    }
}