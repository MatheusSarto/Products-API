package com.sartop.demoproductsapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserPasswordDto
{
    @NotBlank
    @Size(min = 6)
    private String CurrentPassword;
    @NotBlank
    @Size(min = 6)
    private String NewPassword;
    @NotBlank
    @Size(min = 6)
    private String ConfirmPassword;
}
