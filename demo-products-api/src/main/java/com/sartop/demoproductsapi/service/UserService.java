package com.sartop.demoproductsapi.service;


import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.exception.EntityNotFoundException;
import com.sartop.demoproductsapi.exception.UserAlreadyExistsException;
import com.sartop.demoproductsapi.exception.WrongPasswordException;
import com.sartop.demoproductsapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserEntity save(UserEntity user)
    {
        try
        {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        catch (DataIntegrityViolationException exception)
        {
            throw new UserAlreadyExistsException(String.format("User {%s} already exits", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public UserEntity getById(long id)
    {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
    }

    @Transactional
    public UserEntity updatePassword(String currentPassowrd, String newPassword, String confirmPassword, long id)
    {
        if(!newPassword.equals(confirmPassword))
        {
            throw new WrongPasswordException("New Password doesn't match with confirm Password");
        }

        UserEntity user = getById(id);
        if(passwordEncoder.matches(currentPassowrd, user.getPassword()))
        {
            throw new WrongPasswordException("Wrong Current Password ");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Transactional
    public List<UserEntity> getAll()
    {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    @Transactional(readOnly = true)
    public UserEntity getByUsername(String username)
    {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User {%s} not found", username))
        );
    }

    @Transactional(readOnly = true)
    public UserEntity.Role getRoleByUsername(String username)
    {
        return userRepository.findRoleByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User {%s} not found", username))
        );
    }


}
