package ru.aston.testproj.rest;

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
import java.util.HashSet;

import jakarta.validation.Valid;
import ru.aston.testproj.domain.dto.account.AccountCreateDto;
import ru.aston.testproj.domain.dto.account.AccountDepositDto;
import ru.aston.testproj.domain.dto.account.AccountGetDto;
import ru.aston.testproj.domain.dto.account.AccountTransferDto;
import ru.aston.testproj.domain.dto.account.AccountWithdrawDto;
import ru.aston.testproj.domain.dto.MessageDto;
import ru.aston.testproj.exception.EntityNotFoundException;
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
        log.info("Received request with dto {} to create account.", dto);
        service.create(dto);
        return new MessageDto("Создано");
    }

    @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto depositFounds(@Valid @RequestBody AccountDepositDto dto) throws EntityNotFoundException {
        dto.setName(dto.getName().trim());
        log.info("Received request with dto {} to deposit funds.", dto);
        service.deposit(dto);
        return new MessageDto("Успешное пополнение");
    }

    @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto withdrawFounds(@Valid @RequestBody AccountWithdrawDto dto) throws TestprojException {
        dto.setName(dto.getName().trim());
        log.info("Received request with dto {} to withdraw funds.", dto);
        service.withdraw(dto);
        return new MessageDto("Успешное снятие");
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto transferFounds(@Valid @RequestBody AccountTransferDto dto) throws TestprojException {
        dto.setSourceAccountName(dto.getSourceAccountName().trim());
        dto.setTargetAccountName(dto.getTargetAccountName().trim());
        service.transfer(dto);
        log.info("Received request with dto {} to transfer funds.", dto);
        return new MessageDto("Успешный перевод");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)//todo пагинация?
    public Iterable<AccountGetDto> list() {
        log.info("Received request to print accounts.");
        return mapper.mapAsList(service.list(), AccountGetDto.class);
    }
}
























