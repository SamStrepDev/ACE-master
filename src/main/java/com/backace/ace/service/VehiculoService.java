package com.backace.ace.service;

import com.backace.ace.model.Vehiculo;
import com.backace.ace.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo obtenerVehiculoPorPlaca(String placaVehiculo) {
        return vehiculoRepository.findByPlacaVehiculo(placaVehiculo);
    }
}
