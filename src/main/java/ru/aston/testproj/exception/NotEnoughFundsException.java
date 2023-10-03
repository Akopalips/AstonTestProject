package ru.aston.testproj.exception;

/**
 * @author tuspring
 */
public class NotEnoughFundsException extends TestprojException{

    public NotEnoughFundsException() {
        super("Недостаточно средств.");
    }

    public NotEnoughFundsException(String message) {
        super(message);
    }
}
