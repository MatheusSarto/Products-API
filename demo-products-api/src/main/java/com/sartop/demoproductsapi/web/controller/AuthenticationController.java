package com.sartop.demoproductsapi.web.controller;

import com.sartop.demoproductsapi.jwt.JwtToken;
import com.sartop.demoproductsapi.jwt.JwtUserDetails;
import com.sartop.demoproductsapi.jwt.JwtUserDetailsService;
import com.sartop.demoproductsapi.web.dto.UserLoginDto;
import com.sartop.demoproductsapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "All authentication related operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController
{
    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDto dto, HttpServletRequest request)
    {
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            AuthenticationManager.authenticate(authenticationToken);

            JwtToken token = DetailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(token);
        }
        catch (AuthenticationException exception)
        {
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Credentials"));
    }

    private final JwtUserDetailsService DetailsService;
    private final AuthenticationManager AuthenticationManager;
}
