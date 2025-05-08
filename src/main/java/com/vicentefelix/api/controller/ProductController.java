package com.vicentefelix.api.controller;

import com.vicentefelix.api.exception.ProductNotFoundException;
import com.vicentefelix.api.model.Product;
import com.vicentefelix.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product with ID " + id + " not found!");
        }

        return ResponseEntity.ok(optionalProduct.get());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        Product newProduct = productService.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product newProduct) {
        Optional<Product> productToBeUpdated = productService.findById(id);

        if (productToBeUpdated.isPresent()) {
            Product updateProduct = productService.update(productToBeUpdated.get(), newProduct);
            return ResponseEntity.ok(updateProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}