package com.sartop.demoproductsapi.web.controller;


import com.sartop.demoproductsapi.entity.ClientEntity;
import com.sartop.demoproductsapi.entity.ProductEntity;
import com.sartop.demoproductsapi.entity.UserEntity;
import com.sartop.demoproductsapi.jwt.JwtUserDetails;
import com.sartop.demoproductsapi.service.ClientService;
import com.sartop.demoproductsapi.service.ProductService;
import com.sartop.demoproductsapi.service.UserService;
import com.sartop.demoproductsapi.web.dto.ProductCreateDto;
import com.sartop.demoproductsapi.web.dto.ProductResponseDto;
import com.sartop.demoproductsapi.web.dto.mapper.ProductMapper;
import com.sartop.demoproductsapi.web.dto.mapper.UserMapper;
import com.sartop.demoproductsapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Tag(name = "Products", description = "All product related operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController
{
    private final ProductService productService;
    private final ClientService clientService;

    @Operation(summary = "Adds a new product", description = "Creates a new product on the database. Bearer Token needed. Restrict to ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Product created successfully",
                        headers = @Header(name = HttpHeaders.LOCATION, description = "Product URL")),
                    @ApiResponse(responseCode = "409", description = "Product already exists",
                        content = @Content(mediaType = "application/json;charset=UTF-8",
                        schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> create(@RequestBody @Valid ProductCreateDto dto,
                                       @AuthenticationPrincipal JwtUserDetails userDetails)
    {
        ProductEntity product = ProductMapper.toProduct(dto);
        product.setClient(clientService.getByIUserId(userDetails.getId()));
        productService.save(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @Operation(summary = "Retrieves product", description = "Retrieves product from database. Bearer Token needed. Restrict to ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully got product",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "Product URL")),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping("/{code}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ProductResponseDto> getById(@PathVariable Long id,
                                                        @AuthenticationPrincipal JwtUserDetails userDetails)
    {
        ClientEntity client = clientService.getByIUserId(userDetails.getId());
        ProductEntity product = productService.getById(id, client);

        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Operation(summary = "Retrieves all products", description = "Retrieves a list with all products from database. Bearer Token needed. Restrict to ADMIN.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully got products",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "Product URL")),
            }
    )
    @GetMapping()
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<ProductResponseDto>> getAll(@AuthenticationPrincipal JwtUserDetails userDetails)
    {
        // TODO MAKE GET ALL METHOD USE PAGES
        ClientEntity client = clientService.getByIUserId(userDetails.getId());
        List<ProductEntity> users = productService.getAll(client);
        return ResponseEntity.status(HttpStatus.OK).body(ProductMapper.toListDto(users));
    }
}
