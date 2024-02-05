package com.sartop.demoproductsapi.service;

import com.sartop.demoproductsapi.entity.ClientEntity;
import com.sartop.demoproductsapi.entity.ProductEntity;
import com.sartop.demoproductsapi.exception.CodeUniqueViolationException;
import com.sartop.demoproductsapi.exception.EntityNotFoundException;
import com.sartop.demoproductsapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService
{
    private final ProductRepository productRepository;

    @Transactional
    public ProductEntity save(ProductEntity product)
    {
        try
        {
            return productRepository.save(product);
        }
        catch (DataIntegrityViolationException exception)
        {
            throw new CodeUniqueViolationException(String.format("Product code {%s} already registered", product.getId()));
        }
    }

    @Transactional(readOnly = true)
    public ProductEntity getById(Long id, ClientEntity client)
    {
        return productRepository.findByIdAndClient(id, client).orElseThrow(
                () ->  new EntityNotFoundException(String.format("Product of code {%s} not found", id))
        );
    }

    public List<ProductEntity> getAll(ClientEntity client)
    {
        return productRepository.findAllByClient(client);
    }

}
