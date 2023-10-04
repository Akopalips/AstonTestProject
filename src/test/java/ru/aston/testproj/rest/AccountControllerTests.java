package ru.aston.testproj.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.HashSet;

import ru.aston.testproj.domain.dto.account.AccountCreateDto;
import ru.aston.testproj.domain.dto.account.AccountDepositDto;
import ru.aston.testproj.domain.dto.account.AccountTransferDto;
import ru.aston.testproj.domain.dto.account.AccountWithdrawDto;
import ru.aston.testproj.domain.model.Account;
import ru.aston.testproj.exception.handler.TestprojExceptionHandler;
import ru.aston.testproj.service.AccountService;
import ru.aston.testproj.util.AccountMapper;

/**
 * @author tuspring
 */
@ExtendWith(MockitoExtension.class)
class AccountControllerTests {

    public static final String CREATE_PATH = "/create";
    public static final String ACCOUNT_PATH = "/account";
    public static final String DEPOSIT_PATH = "/deposit";
    public static final String WITHDRAW_PATH = "/withdraw";
    public static final String TRANSFER_PATH = "/transfer";

    public static final String NAME_FIELD = "name";
    public static final String PIN_FIELD = "pin";
    public static final String DEPOSIT_FIELD = "deposit";
    public static final String WITHDRAW_FIELD = "withdraw";
    public static final String SOURCE_ACCOUNT_NAME_FIELD = "sourceAccountName";
    public static final String TARGET_ACCOUNT_NAME_FIELD = "targetAccountName";
    public static final String TRANSFER_FIELD = "transfer";
    public static final String SOURCE_ACCOUNT_PIN_FIELD = "sourceAccountPin";

    @InjectMocks
    private AccountController controller;
    @Mock
    private AccountService service;
    @Spy
    private AccountMapper mapper = new AccountMapper();

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(TestprojExceptionHandler.class)
            .build();
    }

    //------------------------------------CREATE------------------------------------
    @Test
    public void createAccount_positive_Created() throws Exception {
        //Given
        doNothing().when(service).create(any(AccountCreateDto.class));
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + CREATE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isCreated());
        verify(service, times(1)).create(any(AccountCreateDto.class));
    }

    @Test
    public void createAccount_negative_NoName() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + CREATE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).create(any(AccountCreateDto.class));
    }

    @Test
    public void createAccount_negative_NoPin() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + CREATE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).create(any(AccountCreateDto.class));
    }

    @Test
    public void createAccount_negative_InvalidPin() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + CREATE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(PIN_FIELD, "124").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).create(any(AccountCreateDto.class));
    }

    //------------------------------------DEPOSIT------------------------------------
    @Test
    public void depositFounds_positive_Ok() throws Exception {
        //Given
        doNothing().when(service).deposit(any(AccountDepositDto.class));
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + DEPOSIT_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(DEPOSIT_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isOk());
        verify(service, times(1)).deposit(any(AccountDepositDto.class));
    }

    @Test
    public void depositFounds_negative_NoName() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + DEPOSIT_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(DEPOSIT_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).deposit(any(AccountDepositDto.class));
    }

    @Test
    public void depositFounds_negative_NoDeposit() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + DEPOSIT_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).deposit(any(AccountDepositDto.class));
    }

    @Test
    public void depositFounds_negative_NegativeDeposit() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + DEPOSIT_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(DEPOSIT_FIELD, "-1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).deposit(any(AccountDepositDto.class));
    }
//------------------------------------DEPOSIT------------------------------------

    @Test
    public void withdrawFounds_positive_Ok() throws Exception {
        //Given
        doNothing().when(service).withdraw(any(AccountWithdrawDto.class));
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + WITHDRAW_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(WITHDRAW_FIELD, "1234")
                                 .put(PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isOk());
        verify(service, times(1)).withdraw(any(AccountWithdrawDto.class));
    }

    @Test
    public void withdrawFounds_negative_NoName() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + WITHDRAW_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(WITHDRAW_FIELD, "1234")
                                 .put(PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).withdraw(any(AccountWithdrawDto.class));
    }

    @Test
    public void withdrawFounds_negative_NoWithdraw() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + WITHDRAW_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).withdraw(any(AccountWithdrawDto.class));
    }

    @Test
    public void withdrawFounds_negative_NegativeWithdraw() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + WITHDRAW_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(WITHDRAW_FIELD, "-1234")
                                 .put(PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).withdraw(any(AccountWithdrawDto.class));
    }

    @Test
    public void withdrawFounds_negative_NoPin() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + WITHDRAW_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(WITHDRAW_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).withdraw(any(AccountWithdrawDto.class));
    }

    @Test
    public void withdrawFounds_negative_InvalidPin() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + WITHDRAW_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(NAME_FIELD, "123")
                                 .put(WITHDRAW_FIELD, "1234")
                                 .put(PIN_FIELD, "234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).withdraw(any(AccountWithdrawDto.class));
    }

    //------------------------------------DEPOSIT------------------------------------
    @Test
    public void transferFounds_positive_Ok() throws Exception {
        //Given
        doNothing().when(service).transfer(any(AccountTransferDto.class));
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(SOURCE_ACCOUNT_NAME_FIELD, "123")
                                 .put(TARGET_ACCOUNT_NAME_FIELD, "1234")
                                 .put(TRANSFER_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isOk());
        verify(service, times(1)).transfer(any(AccountTransferDto.class));
    }

    @Test
    public void transferFounds_negative_NoSourceAccountName() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(TARGET_ACCOUNT_NAME_FIELD, "1234")
                                 .put(TRANSFER_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).transfer(any(AccountTransferDto.class));
    }

    @Test
    public void transferFounds_negative_NoTargetAccountName() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(SOURCE_ACCOUNT_NAME_FIELD, "123")
                                 .put(TRANSFER_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).transfer(any(AccountTransferDto.class));
    }

    @Test
    public void transferFounds_negative_NoTransfer() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(TARGET_ACCOUNT_NAME_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_NAME_FIELD, "123")
                                 .put(SOURCE_ACCOUNT_PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).transfer(any(AccountTransferDto.class));
    }

    @Test
    public void transferFounds_negative_NegativeTransfer() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(TARGET_ACCOUNT_NAME_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_NAME_FIELD, "123")
                                 .put(TRANSFER_FIELD, "-1234")
                                 .put(SOURCE_ACCOUNT_PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).transfer(any(AccountTransferDto.class));
    }

    @Test
    public void transferFounds_negative_NoSourceAccountPin() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(TARGET_ACCOUNT_NAME_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_NAME_FIELD, "123")
                                 .put(TRANSFER_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).transfer(any(AccountTransferDto.class));
    }

    @Test
    public void transferFounds_negative_InvalidSourceAccountPin() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(TARGET_ACCOUNT_NAME_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_NAME_FIELD, "123")
                                 .put(TRANSFER_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_PIN_FIELD, "12234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).transfer(any(AccountTransferDto.class));
    }

    @Test
    public void transferFounds_negative_IdenticalNames() throws Exception {
        //Given
        //When
        mockMvc.perform(
                post(ACCOUNT_PATH + TRANSFER_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new JSONObject()
                                 .put(TARGET_ACCOUNT_NAME_FIELD, "123")
                                 .put(SOURCE_ACCOUNT_NAME_FIELD, "123")
                                 .put(TRANSFER_FIELD, "1234")
                                 .put(SOURCE_ACCOUNT_PIN_FIELD, "1234").toString())
            )
            //Then
            .andExpect(status().isBadRequest());
        verify(service, never()).transfer(any(AccountTransferDto.class));
    }

    //------------------------------------LIST------------------------------------
    @Test
    public void list_positive_Ok() throws Exception {
        //Given
        doReturn(new HashSet<Account>()).when(service).list();
        //When
        mockMvc.perform(get(ACCOUNT_PATH + "/list")
            )
            //Then
            .andExpect(status().isOk());
        verify(service, times(1)).list();
    }
}
