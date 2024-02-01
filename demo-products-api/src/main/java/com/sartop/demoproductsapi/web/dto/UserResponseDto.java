package com.sartop.demoproductsapi.web.dto;

import lombok.*;

@ToString
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserResponseDto
{
    private String id;
    private String username;
    private String role;
}
