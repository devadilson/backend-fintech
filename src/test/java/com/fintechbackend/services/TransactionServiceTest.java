package com.fintechbackend.services;

import com.fintechbackend.domain.repositories.UserRepository;
import com.fintechbackend.domain.user.User;
import com.fintechbackend.domain.transactions.dtos.TransactionDTO;
import com.fintechbackend.domain.repositories.TransactionRepository;
import com.fintechbackend.domain.services.AuthorizationService;
import com.fintechbackend.domain.services.NotificationService;
import com.fintechbackend.domain.services.TransactionService;
import com.fintechbackend.domain.services.UserService;

import com.fintechbackend.domain.user.UserType;
import com.fintechbackend.domain.user.dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private AuthorizationService authService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfully when everything is OK")
    void createTransactionCase1() throws Exception {
        UUID senderUUID = UUID.randomUUID();
        User sender = new User(senderUUID, "Joao", "Souza", "99999999902", "joao@gmail.com", "12345",
                new BigDecimal(10), UserType.COMMON);

        UUID receiverUUID = UUID.randomUUID();
        User receiver = new User(receiverUUID, "Joao", "Souza", "99999999902", "joao@gmail.com", "12345",
                new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(senderUUID)).thenReturn(sender);
        when(userService.findUserById(receiverUUID)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), senderUUID, receiverUUID);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(20));
        verify(userService, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender, "Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(receiver, "Transação recebida com sucesso");

    }

    @Test
    @DisplayName("Should throw Exception when Transaction is not allowed")
    void createTransactionCase2() throws Exception {
        UUID senderUUID = UUID.randomUUID();
        User sender = new User(senderUUID, "Maria", "Souza", "99999999901", "maria@gmail.com", "12345",
                new BigDecimal(10), UserType.COMMON);

        UUID receiverUUID = UUID.randomUUID();
        User receiver = new User(receiverUUID, "Joao", "Souza", "99999999902", "joao@gmail.com", "12345",
                new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(senderUUID)).thenReturn(sender);
        when(userService.findUserById(receiverUUID)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(), any())).thenReturn(false);

        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), senderUUID, receiverUUID);
            transactionService.createTransaction(request);
        });

        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
    }

    public UUID getUuid() {
        return UUID.randomUUID();
    }
}
