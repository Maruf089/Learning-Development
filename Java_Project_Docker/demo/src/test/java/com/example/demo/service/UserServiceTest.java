package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnAllUsers() {
        List<User> users = Arrays.asList(
                new User(1L, "Rahim", "rahim@example.com"),
                new User(2L, "Karim", "karim@example.com")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Rahim", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnUserById() {
        User user = new User(1L, "Rahim", "rahim@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals("Rahim", result.getName());
        assertEquals("rahim@example.com", result.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(99L);
        });
    }

    @Test
    void shouldCreateUser() {
        User input = new User(null, "Rahim", "rahim@example.com");
        User saved = new User(1L, "Rahim", "rahim@example.com");

        when(userRepository.save(input)).thenReturn(saved);

        User result = userService.createUser(input);

        assertNotNull(result.getId());
        assertEquals("Rahim", result.getName());
        verify(userRepository).save(input);
    }

    @Test
    void shouldUpdateUser() {
        User existing = new User(1L, "Old", "old@example.com");
        User update = new User(null, "New", "new@example.com");
        User updated = new User(1L, "New", "new@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userRepository.save(any(User.class))).thenReturn(updated);

        User result = userService.updateUser(1L, update);

        assertEquals("New", result.getName());
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    void shouldDeleteUser() {
        User existing = new User(1L, "Rahim", "rahim@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(userRepository).delete(existing);

        userService.deleteUser(1L);

        verify(userRepository).delete(existing);
    }
}
