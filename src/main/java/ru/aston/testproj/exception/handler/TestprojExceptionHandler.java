package ru.aston.testproj.exception.handler;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ru.aston.testproj.domain.dto.MessageDto;
import ru.aston.testproj.exception.EntityNotFoundException;
import ru.aston.testproj.exception.NotEnoughFundsException;
import ru.aston.testproj.exception.TestprojException;
import ru.aston.testproj.exception.WrongPinException;

/**
 * @author tuspring
 */
@ControllerAdvice
public class TestprojExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity(
            new MessageDto(e.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }
    //-----------------------extends DataAccessException
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        return new ResponseEntity(
            new MessageDto("Нарушение уникальности."), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<String> DataAccessExceptionHandler(DataAccessException e) {
        return new ResponseEntity(
            new MessageDto("Неизвестная ошибка."), HttpStatus.BAD_REQUEST);
    }

    //-----------------------extends TestprojException
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundExceptionHandler(EntityNotFoundException e) {
        return new ResponseEntity(
            new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotEnoughFundsException.class)
    public ResponseEntity<String> notEnoughFundsExceptionHandler(NotEnoughFundsException e) {
        return new ResponseEntity(
            new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = WrongPinException.class)
    public ResponseEntity<String> wrongPinExceptionHandler(WrongPinException e) {
        return new ResponseEntity(
            new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TestprojException.class)
    public ResponseEntity<String> TestprojExceptionHandler(TestprojException e) {
        return new ResponseEntity(
            new MessageDto("Неизвестная ошибка."), HttpStatus.BAD_REQUEST);
    }
}