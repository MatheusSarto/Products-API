package com.sartop.demoproductsapi.web.dto.mapper;

import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.web.dto.UserCreateDto;
import com.sartop.demoproductsapi.web.dto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper
{
    public static UserEntity toUser(UserCreateDto createdto)
    {
        return new ModelMapper().map(createdto, UserEntity.class);
    }

    public static UserResponseDto toDto(UserEntity user)
    {
        String role = user.getRole().name().substring("ROLE_".length());

        PropertyMap<UserEntity, UserResponseDto> props = new PropertyMap<UserEntity, UserResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        return mapper.map(user, UserResponseDto.class);
    }

    public static List<UserResponseDto> toListDto(List<UserEntity> users)
    {
        return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
