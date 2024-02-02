package com.sartop.demoproductsapi.web.dto.mapper;

import com.sartop.demoproductsapi.web.dto.PageableDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper
{
    public static PageableDto toDto(Page page)
    {
        return new ModelMapper().map(page, PageableDto.class);
    }
}
