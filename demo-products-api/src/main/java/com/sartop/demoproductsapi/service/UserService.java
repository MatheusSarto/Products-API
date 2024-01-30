package com.sartop.demoproductsapi.service;


import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.exception.EntityNotFoundException;
import com.sartop.demoproductsapi.exception.UserAlreadyExistsException;
import com.sartop.demoproductsapi.exception.WrongPasswordException;
import com.sartop.demoproductsapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService
{
    @Transactional
    public UserEntity save(UserEntity user)
    {
        try
        {
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
        if(!user.getPassword().equals(currentPassowrd))
        {
            throw new WrongPasswordException("Wrong Current Password ");
        }

        user.setPassword(newPassword);
        return user;
    }

    @Transactional
    public List<UserEntity> getAll()
    {
        List<UserEntity> users = userRepository.findAll();
        return users;
    }

    private final UserRepository userRepository;
}
