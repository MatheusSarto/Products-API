package com.sartop.demoproductsapi.service;

import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.exception.EntityNotFoundException;
import com.sartop.demoproductsapi.repository.UserRepository;
import com.sartop.demoproductsapi.web.dto.UserCreateDto;
import com.sartop.demoproductsapi.web.dto.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;
    UserEntity existingUser;
    UserEntity nonExistingUser;


    @BeforeEach
    void setUserEntityUp()
    {
        existingUser = new UserEntity();
        existingUser.setId((long)14);
        existingUser.setName("blablabla");
        existingUser.setPassword("$10$lNKZKsaWZbJ0UzdtlxhuXur4SXMIgPv0Mi4K0aNJOPTICgNhYil0a");
        existingUser.setUsername("novouser2@gmail.com");
        existingUser.setRole(UserEntity.Role.ROLE_CLIENT);
        existingUser.setCreateBy("anonymousUser");
        existingUser.setModifiedBy("anonymousUser");
        existingUser.setCreationDate(LocalDateTime.of(2024, 3, 4, 10, 57, 10, 335313000));
        existingUser.setCreationDate(LocalDateTime.of(2024, 3, 4, 10, 57, 10, 335313000));

        nonExistingUser = UserMapper.toUser(new UserCreateDto("zerigoatdms@gmail.com", "ZERI THE GOAT", "123123"));
    }

    @Test
    void SuccessfullyGetByUsername()
    {
        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(existingUser);

        UserEntity userTest = userService.getByUsername(existingUser.getUsername());

        // Checks if user got from repository is equal to mocked user
        assertThat(existingUser, is(userTest));

        // Checks if repository have been called just once
        verify(userRepository).findByUsername(existingUser.getUsername());
        verifyNoMoreInteractions(userRepository);
    }
    @Test
    void SuccessfullyGetByUserRole()
    {
        when(userRepository.findRoleByUsername(existingUser.getUsername())).thenReturn(existingUser.getRole());

        UserEntity.Role roleTest = userService.getRoleByUsername(existingUser.getUsername());

        // Checks if user got from repository is equal to mocked user
        assertEquals(existingUser.getRole(), roleTest);

        // Checks if repository have been called just once
        verify(userRepository).findRoleByUsername(existingUser.getUsername());
        verifyNoMoreInteractions(userRepository);
    }
}