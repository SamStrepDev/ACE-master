package com.backace.ace.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Vehiculo {

    @Id
    @Column(name = "placa_vehiculo", nullable = false, unique = true)
    private String placaVehiculo; // Placa del vehículo

    private String tipo; // Tipo de vehículo (particular, SUV, van, pickup, taxi)
    private String fechaCompra; // Fecha de compra en formato "yyyy-MM-dd"

    // Getters y Setters
    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
