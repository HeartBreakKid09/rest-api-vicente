package com.vicentefelix.api.service;

import com.vicentefelix.api.model.Product;
import com.vicentefelix.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id) != null ? Optional.ofNullable(productRepository.findById(id).get()) : Optional.ofNullable(new Product());
    }

    public Product save(Product productToBeSaved) {
        return productRepository.save(productToBeSaved);
    }

    public Product update(Product productToBeUpdated, Product newProduct) {
        newProduct.setName(productToBeUpdated.getName());
        newProduct.setCostForTheCompany(productToBeUpdated.getCostForTheCompany());
        newProduct.setDescription(productToBeUpdated.getDescription());
        newProduct.setCategory(productToBeUpdated.getCategory());
        newProduct.setBrand(productToBeUpdated.getBrand());
        newProduct.setAvailable(productToBeUpdated.getAvailable());
        newProduct.setReleaseDate(productToBeUpdated.getReleaseDate());

       return this.save(newProduct);
    }

    public void delete(long id) {
        productRepository.deleteById(id);
    }

}
