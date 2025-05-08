package com.vicentefelix.api.model;

import com.sun.istack.NotNull;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name of the product is mandatory")
    private String name;

    @NotNull
    private Double costForTheCompany;

    @NotNull
    private Double costForTheBuyer;

    @Size(max = 255, message = "The description of the product cannot be more than 255 characters ")
    private String description;

    @NotBlank
    private String category;

    @NotBlank
    private String brand;

    private Boolean available;

    private LocalDate releaseDate;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getCostForTheCompany() {
        return costForTheCompany;
    }

    public Double getCostForTheBuyer() {
        return costForTheBuyer;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public Boolean getAvailable() {
        return available;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCostForTheCompany(Double costForTheCompany) {
        this.costForTheCompany = costForTheCompany;
    }

    public void setCostForTheBuyer(Double costForTheBuyer) {
        this.costForTheBuyer = costForTheBuyer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

