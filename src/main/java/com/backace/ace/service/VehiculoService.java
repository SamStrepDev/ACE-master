package com.backace.ace.service;

import com.backace.ace.model.Vehiculo;
import com.backace.ace.repository.VehiculoRepository; // Asegúrate de tener un repositorio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Vehiculo obtenerVehiculoPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa); // Asegúrate de tener este método en tu repositorio
    }
}
