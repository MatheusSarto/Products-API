package com.sartop.demoproductsapi.web.dto.mapper;

import com.sartop.demoproductsapi.entity.ProductEntity;
import com.sartop.demoproductsapi.web.dto.ProductCreateDto;
import com.sartop.demoproductsapi.web.dto.ProductResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMapper
{
    public static ProductEntity toProduct(ProductCreateDto dto)
    {
        return new ModelMapper().map(dto, ProductEntity.class);
    }

    public static ProductResponseDto toDto(ProductEntity product)
    {
        return new ModelMapper().map(product, ProductResponseDto.class);
    }

    public static List<ProductResponseDto> toListDto(List<ProductEntity> products)
    {
        return products.stream().map(product -> toDto(product)).collect(Collectors.toList());
    }
}
