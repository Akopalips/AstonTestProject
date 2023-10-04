package ru.aston.testproj.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author tuspring
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String ACCOUNT_NOT_SELECTED = "Не указан аккаунт.";
    public static final String EMPTY_PIN_CODE = "Пустой PIN-код.";
    public static final String INVALID_PIN_CODE = "Некорректный PIN-кода.";
    public static final String EMPTY_TRANSFER_VALUE = "Отсутствует значение перевода.";
    public static final String NEGATIVE_TRANSFER_VALUE = "Отрицательное значение перевода.";
    public static final String EMPTY_WITHDRAW_VALUE = "Отсутствует значение списания.";
    public static final String NEGATIVE_WITHDRAW_VALUE = "Отрицательное списание.";
    public static final String EMPTY_DEPOSIT_VALUE = "Отсутствует значение пополнения.";
    public static final String NEGATIVE_DEPOSIT = "Отрицательное пополнение.";
    public static final String IDENTICAL_ACCOUNTS_REQUEST = "Выбраны одинаковые аккаунты.";
    public static final String UNKNOWN_EXCEPTION = "Неизвестная ошибка.";
    public static final String WRONG_PIN = "Неверный PIN-код.";
    public static final String CREATED = "Создано";
    public static final String SUCCESSFUL_DEPOSIT = "Успешное пополнение";
    public static final String SUCCESSFUL_WITHDRAW = "Успешное снятие";
    public static final String SUCCESSFUL_TRANSFER = "Успешный перевод";

    public static final String RECEIVED_REQUEST_WITH_DTO_TO_CREATE_ACCOUNT = "Received request with dto {} to create account.";
    public static final String RECEIVED_REQUEST_WITH_DTO_TO_DEPOSIT_FUNDS = "Received request with dto {} to deposit funds.";
    public static final String RECEIVED_REQUEST_WITH_DTO_TO_WITHDRAW_FUNDS = "Received request with dto {} to withdraw funds.";
    public static final String RECEIVED_REQUEST_WITH_DTO_TO_TRANSFER_FUNDS = "Received request with dto {} to transfer funds.";
    public static final String RECEIVED_REQUEST_TO_PRINT_ACCOUNTS = "Received request to print accounts.";

    public static final String ACCOUNT_WITH_NAME_TO_CREATE = "Account with name {} to create.";
    public static final String ACCOUNT_SAVED_TO_DB = "Account {} saved to db.";
    public static final String START_DEPOSIT_TO_ACCOUNT = "Start deposit to account {}.";
    public static final String DEPOSIT_TO_ACCOUNT_SUCCESSFUL = "Deposit to account {} successful.";
    public static final String START_WITHDRAW_TO_ACCOUNT = "Start withdraw to account {}.";
    public static final String WITHDRAW_TO_ACCOUNT_SUCCESSFUL = "Withdraw to account {} successful.";
    public static final String START_TRANSFER_FROM_TO = "Start transfer from {} to {}.";
    public static final String TRANSFER_FROM_TO_COMPLETE = "Transfer from {} to {} complete.";
    public static final String GET_ALL_ACCOUNTS_FROM_DB = "Get all accounts from db.";
    public static final String NO_SUCH_ENTITY = "No such entity {}.";
    public static final String NOT_EQUALS_PINS_AND = "Not equals pins {} and {}";
    public static final String SOURCE_ACCOUNT_HAS_NOT_ENOUGH_FUNDS_CURRENT_TRANSFER_NEEDED = "Source account {} has not enough funds: current {} transfer needed {}.";
    public static final String PIN_REGEX = "^[0-9]{4}$";
}
