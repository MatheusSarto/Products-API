package com.sartop.demoproductsapi.web.dto.mapper;

import com.sartop.demoproductsapi.entity.ClientEntity;
import com.sartop.demoproductsapi.web.dto.ClientCreateDto;
import com.sartop.demoproductsapi.web.dto.ClientResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper
{
    public static ClientEntity toClient(ClientCreateDto dto)
    {
        return new ModelMapper().map(dto, ClientEntity.class);
    }

    public static ClientResponseDto toDto(ClientEntity client)
    {
        return new ModelMapper().map(client, ClientResponseDto.class);
    }

    public static List<ClientResponseDto> toListDto(List<ClientEntity> clients)
    {
        return clients.stream().map(client -> toDto(client)).collect(Collectors.toList());
    }
}
