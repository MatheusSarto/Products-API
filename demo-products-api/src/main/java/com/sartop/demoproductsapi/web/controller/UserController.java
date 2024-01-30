package com.sartop.demoproductsapi.web.controller;

import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.service.UserService;
import com.sartop.demoproductsapi.web.dto.UserCreateDto;
import com.sartop.demoproductsapi.web.dto.UserPasswordDto;
import com.sartop.demoproductsapi.web.dto.UserResponseDto;
import com.sartop.demoproductsapi.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController
{
    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userdto)
    {
        UserEntity user = userService.save(UserMapper.toUser(userdto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> create(@PathVariable long id)
    {
        UserEntity user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll()
    {
        List<UserEntity> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(users));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordDto passwordDto, @PathVariable long id)
    {
        UserEntity updatedUser = userService.updatePassword(passwordDto.getCurrentPassword(), passwordDto.getNewPassword(),
                                                            passwordDto.getConfirmPassword(), id);

        return ResponseEntity.noContent().build();
    }

    private final UserService userService;
}
