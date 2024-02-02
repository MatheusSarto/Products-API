package com.sartop.demoproductsapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientResponseDto
{
    private long id;
    private String name;
    private String cpf;
}
