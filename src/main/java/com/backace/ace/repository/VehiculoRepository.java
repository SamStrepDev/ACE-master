package com.backace.ace.repository;

import com.backace.ace.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    Vehiculo findByPlacaVehiculo(String placaVehiculo); // Método para encontrar un vehículo por su placa
}
