package com.sartop.demoproductsapi.service;

import com.sartop.demoproductsapi.entity.ClientEntity;
import com.sartop.demoproductsapi.exception.ClientAlreadyExistsException;
import com.sartop.demoproductsapi.exception.EntityNotFoundException;
import com.sartop.demoproductsapi.repository.ClientRepositoy;
import com.sartop.demoproductsapi.repository.projection.ClientProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService
{
    private final ClientRepositoy clientRepositoy;

    @Transactional
    public ClientEntity save(ClientEntity client)
    {
        try
        {
            return clientRepositoy.save(client);
        }
        catch (DataIntegrityViolationException exception)
        {
            throw new ClientAlreadyExistsException(String.format("Client {%s} already exits", client.getCpf()));
        }
    }
    @Transactional(readOnly = true)
    public ClientEntity getById(long id)
    {
        return clientRepositoy.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Client of id {%s} not found", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> getAll(Pageable pageable)
    {
        return clientRepositoy.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public ClientEntity getByIUserId(Long id)
    {
        return clientRepositoy.findByUserId(id);
    }
}
