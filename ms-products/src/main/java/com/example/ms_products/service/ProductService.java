package com.example.ms_products.service;

import com.example.ms_products.dto.ProductRequest;
import com.example.ms_products.dto.ProductResponse;
import com.example.ms_products.mapper.ProductMapper;
import com.example.ms_products.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    public void addProduct(ProductRequest request) {
        var product = ProductMapper.toEntity(request);

        try {
            repository.save(product);
            log.info("Product added successfully: {}", product);
        } catch (Exception e) {
            log.error("Error saving product: {}", product, e);
            throw new RuntimeException("Failed to add product", e);
        }
    }


    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }


}
