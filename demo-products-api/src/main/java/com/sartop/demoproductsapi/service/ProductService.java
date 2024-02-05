package com.sartop.demoproductsapi.service;

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
            throw new CodeUniqueViolationException(String.format("Product code {%s} already registered", product.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public ProductEntity getByCode(String code)
    {
        return productRepository.findByCode(code).orElseThrow(
                () ->  new EntityNotFoundException(String.format("Product of code {%s} not found", code))
        );
    }

    public List<ProductEntity> getAll()
    {
        return productRepository.findAll();
    }
}
