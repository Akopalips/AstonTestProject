package ru.aston.testproj.rest;

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
import ru.aston.testproj.dto.Account.AccountCreateDto;
import ru.aston.testproj.dto.Account.AccountDepositDto;
import ru.aston.testproj.dto.Account.AccountGetDto;
import ru.aston.testproj.dto.Account.AccountTransferDto;
import ru.aston.testproj.dto.Account.AccountWithdrawDto;
import ru.aston.testproj.dto.MessageDto;

/**
 * @author tuspring
 */
@ResponseStatus(HttpStatus.OK)
@RestController
@RequestMapping(value = "/account")
@Slf4j
public class AccountController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto createAccount(@Valid @RequestBody AccountCreateDto dto) {
        log.info("Received account dto {} to create.", dto);
        return new MessageDto( "Создано");
    }

    @PostMapping(value = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto depositFounds (@Valid @RequestBody AccountDepositDto dto) {
        log.info("Received account dto {} to deposit.", dto);
        return new MessageDto( "Успешное пополнение");
    }

    @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto withdrawFounds (@Valid @RequestBody AccountWithdrawDto dto) {
        log.info("Received account dto {} to withdraw.", dto);
        return new MessageDto( "Успешное снятие");
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto transferFounds (@Valid @RequestBody AccountTransferDto dto) {
        log.info("Received account dto {} to transfer.", dto);
        return new MessageDto( "Успешный перевод");
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)//todo пагинация?
    public HashSet<AccountGetDto> transferFounds () {
        log.info("Received request to print accounts.");
        return new HashSet<AccountGetDto>();
    }

}
























