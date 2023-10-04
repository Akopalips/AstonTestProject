package ru.aston.testproj.exception;

/**
 * @author tuspring
 */
public class EntityNotFoundException extends TestprojException {

    public EntityNotFoundException() {
        super("Сущность не найдена.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
