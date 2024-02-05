package com.sartop.demoproductsapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductCreateDto
{
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private float price;
    @NotBlank
    @Pattern(regexp = "ON_STOCK|FOR_SALE|SOLD")
    private String status;
}
