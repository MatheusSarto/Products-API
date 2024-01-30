package com.sartop.demoproductsapi.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
public class ErrorMessage
{

    public ErrorMessage(){}
    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message)
    {
        this.Path           = request.getRequestURI();
        this.Method         = request.getMethod();
        this.Status         = status.value();
        this.StatusMessage  = status.getReasonPhrase();
        this.Message        = message;

    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result)
    {
        this.Path           = request.getRequestURI();
        this.Method         = request.getMethod();
        this.Status         = status.value();
        this.StatusMessage  = status.getReasonPhrase();
        this.Message        = message;

        AddErrors(result);
    }

    private void AddErrors(BindingResult result)
    {
        this.Errors = new HashMap<>();

        for(FieldError fieldError : result.getFieldErrors())
        {
            this.Errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    private String Path;
    private String Method;
    private int Status;
    private String StatusMessage;
    private String Message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> Errors;
}
