package com.sartop.demoproductsapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductCreateDto
{
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private float price;
    @NotBlank
    @Pattern(regexp = "ON_STOCK|FOR_SALE|SOLD")
    private String status;
    @NotBlank
    @Pattern(regexp = "DAIRY|PRODUCE|FREEZER|SEAFOOD|SNAKCS|BEVERAGE|BAKERY|CONDIMENTS|DELI|MEAT|BAKING|CARE_PRODUCTS|DRIED_GOODS|VEGETABLES|CANNED_FOOD|BREAD|CEREAL|FRUITS|GRAINS|INTERNATIONAL_FOOD|EGGS_AND_DAIRY|FAT_AND_OIL|PROTEIN_FOODS|CLEANING|BABY_ITENS|CLOUTHING|TECHNOLOGIE")
    private String category;
}
