package com.sartop.demoproductsapi.web.dto;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserResponseDto
{
    private String Id;
    private String Username;
    private String UserRole;
}
