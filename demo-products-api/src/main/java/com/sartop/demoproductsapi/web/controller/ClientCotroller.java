package com.sartop.demoproductsapi.web.controller;

import com.sartop.demoproductsapi.entity.ClientEntity;
import com.sartop.demoproductsapi.jwt.JwtUserDetails;
import com.sartop.demoproductsapi.repository.projection.ClientProjection;
import com.sartop.demoproductsapi.service.ClientService;
import com.sartop.demoproductsapi.service.UserService;
import com.sartop.demoproductsapi.web.dto.ClientCreateDto;
import com.sartop.demoproductsapi.web.dto.ClientResponseDto;
import com.sartop.demoproductsapi.web.dto.PageableDto;
import com.sartop.demoproductsapi.web.dto.UserResponseDto;
import com.sartop.demoproductsapi.web.dto.mapper.ClientMapper;
import com.sartop.demoproductsapi.web.dto.mapper.PageableMapper;
import com.sartop.demoproductsapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Clients", description = "All client related operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientCotroller
{
    private final ClientService clientService;
    private final UserService userService;

    @Operation(summary = "Creates a new client", description = "Creates a new client linked to an existing client. Token Required. Access restricted to users of role CLIENT",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Client created successfully", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Client already exits", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> create(@RequestBody @Valid ClientCreateDto dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails)
    {
        ClientEntity client = ClientMapper.toClient(dto);
        client.setUser(userService.getById(userDetails.getId()));
        clientService.save(client);
        return ResponseEntity.status(201).body(ClientMapper.toDto(client));
    }

    @Operation(summary = "Gets client by id", description = "Gets client by id. Token Required. Access restricted to users of role CLIENT or ADMIN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retrieved client successfully", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType="application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENT') AND #id == authentication.principal.id)")
    public ResponseEntity<ClientResponseDto> getClient(@PathVariable long id)
    {
        ClientEntity client = clientService.getById(id);
        return ResponseEntity.status(200).body(ClientMapper.toDto(client));
    }


    @Operation(summary = "Gets ALL client", description = "Gets ALL clientS. Token Required. Access restricted to users of role CLIENT or ADMIN",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Represents page returned"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Represents total elements of each page"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                            content = @Content(schema = @Schema(type = "String", defaultValue = "id,asc")),
                            description = "Represents sort of results. Accepts multiple sort orders."
                    ),
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Retrieved client successfully", content = @Content(mediaType="application/json;charset=UTF-8",
                            schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType="application/json;charset=UTF-8",
                            schema = @Schema(implementation = ErrorMessage.class))),
            }
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true) @PageableDefault(size = 20) Pageable pageable)
    {
        Page<ClientProjection> clients = clientService.getAll(pageable);
        return ResponseEntity.status(200).body(PageableMapper.toDto(clients));
    }

    @GetMapping("/details")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails userDetails)
    {
        ClientEntity client = clientService.getByIUserId(userDetails.getId());
        return ResponseEntity.status(200).body(ClientMapper.toDto(client));
    }
}
