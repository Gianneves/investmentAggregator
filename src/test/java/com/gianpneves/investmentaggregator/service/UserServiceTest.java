package com.gianpneves.investmentaggregator.service;

import com.gianpneves.investmentaggregator.controller.CreateUserDTO;
import com.gianpneves.investmentaggregator.entity.User;
import com.gianpneves.investmentaggregator.repository.UserRepository;
import jakarta.persistence.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUserWithSuccess() {

            // Arrange
            var user = new User (
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "1234",
                    Instant.now(),
                    null
            );
            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
            var input = new CreateUserDTO("username", "email", "password");

            // Act
            var output = userService.createUser(input);

            // Assert
            assertNotNull(output);

            var userCaptured = userArgumentCaptor.getValue();

            assertEquals(input.username(), userCaptured.getUsername());
            assertEquals(input.email(), userCaptured.getEmail());
            assertEquals(input.password(), userCaptured.getPassword());
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            // Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO("username", "email", "password");

            // Act &  Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }

    @Nested
    class getUserById {

        @Test
        @DisplayName("should get user by id with success when optional is present")
        void shouldGetUserByIdWithSuccessWhenOptionalIsPresent() {
            // Arrange
            var user = new User (
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "1234",
                    Instant.now(),
                    null
            );
            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());
            // Act
            var output =  userService.getUserById(user.getUserId().toString());

            // Assert
            assertTrue(output.isPresent());
            assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
        }

        @Test
        @DisplayName("should get user by id with success when optional is empty")
        void shouldGetUserByIdWithSuccessWhenOptionalIsEmpty() {
            // Arrange
            var userId = UUID.randomUUID();
            doReturn(Optional.empty()).when(userRepository).findById(uuidArgumentCaptor.capture());
            // Act
            var output =  userService.getUserById(userId.toString());

            // Assert
            assertTrue(output.isEmpty());
            assertEquals(userId, uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class getAllUsers {
        @Test
        @DisplayName("should return all users with success")
        void shouldReturnAllUsersWithSuccess() {
            // Arrange
            var user = new User (
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "1234",
                    Instant.now(),
                    null
            );
            var userList = List.of(user);
            doReturn(List.of(user))
                    .when(userRepository)
                    .findAll();

            // Act
            var output = userService.getAllUsers();
            // Assert
            assertNotNull(output);
            assertEquals(userList.size(), output.size());
        }
    }

    @Nested
    class deleteById {
        @Test
        @DisplayName("should delete with success")
        void shouldDeleteWithSuccess() {
            // Arrange
            doReturn(true)
                    .when(userRepository)
                            .existsById(uuidArgumentCaptor.capture());
            doNothing()
                    .when(userRepository)
                    .deleteById(uuidArgumentCaptor.capture());
            var userId = UUID.randomUUID();
            // Act
            userService.deleteById(userId.toString());

            // Assert
            var idList = uuidArgumentCaptor.getAllValues();
            assertEquals(userId, idList.get(0));
            assertEquals(userId, idList.get(1));

        }
    }
}
