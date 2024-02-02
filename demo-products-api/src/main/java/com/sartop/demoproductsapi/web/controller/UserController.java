package com.sartop.demoproductsapi.web.controller;

import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.service.UserService;
import com.sartop.demoproductsapi.web.dto.UserCreateDto;
import com.sartop.demoproductsapi.web.dto.UserPasswordDto;
import com.sartop.demoproductsapi.web.dto.UserResponseDto;
import com.sartop.demoproductsapi.web.dto.mapper.UserMapper;
import com.sartop.demoproductsapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "All user related operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController
{
    @Operation(summary = "Creates a new user", description = "Creates a new user",
            responses = {
                @ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType="application/json",
                        schema = @Schema(implementation = UserResponseDto.class))),
                @ApiResponse(responseCode = "409", description = "User already exits", content = @Content(mediaType="application/json",
                        schema = @Schema(implementation = ErrorMessage.class))),
                @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(mediaType="application/json",
                        schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @PostMapping()
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto userdto)
    {
        UserEntity user = userService.save(UserMapper.toUser(userdto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @Operation(summary = "Gets user by id", description = "Gets user by id",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retrieved user successfully", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable long id)
    {
        UserEntity user = userService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDto(user));
    }

    @Operation(summary = "Gets a list of all users", description = "Gets a list of all users",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retrieved user successfully", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAll()
    {
        // TODO MAKE GET ALL METHOD USE PAGES
        List<UserEntity> users = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toListDto(users));
    }

    @Operation(summary = "Updates user password", description = "Updates user password",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "User password updated successfully", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Wrong Credentials", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid Fields", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT') AND (#id == authentication.principal.id)")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordDto passwordDto, @PathVariable long id)
    {
        UserEntity updatedUser = userService.updatePassword(passwordDto.getCurrentPassword(), passwordDto.getNewPassword(),
                                                            passwordDto.getConfirmPassword(), id);

        return ResponseEntity.noContent().build();
    }

    private final UserService userService;
}
