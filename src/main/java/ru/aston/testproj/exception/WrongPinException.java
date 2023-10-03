package ru.aston.testproj.exception;

/**
 * @author tuspring
 */
public class WrongPinException extends TestprojException{

    public WrongPinException() {
        super("Неверный PIN-код.");
    }

    public WrongPinException(String message) {
        super(message);
    }
}
