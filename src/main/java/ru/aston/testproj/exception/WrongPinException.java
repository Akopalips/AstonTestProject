package ru.aston.testproj.exception;

import static ru.aston.testproj.util.Constants.WRONG_PIN;

/**
 * @author tuspring
 */
public class WrongPinException extends TestprojException {

    public WrongPinException() {
        super(WRONG_PIN);
    }

    public WrongPinException(String message) {
        super(message);
    }
}
