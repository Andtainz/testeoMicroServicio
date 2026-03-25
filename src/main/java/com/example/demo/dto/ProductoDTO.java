package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductoDTO {
    
    @JsonProperty("id")
    private Long id;
    
    @NotBlank(message = "El nombre del producto es requerido")
    @JsonProperty("nombre")
    private String nombre;
    
    @Positive(message = "El precio debe ser mayor a 0")
    @JsonProperty("precio")
    private Double precio;

    // Constructor vacío
    public ProductoDTO() {}

    // Constructor con parámetros
    public ProductoDTO(Long id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
