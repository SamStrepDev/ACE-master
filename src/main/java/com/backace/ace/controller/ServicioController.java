package com.backace.ace.controller;

import com.backace.ace.model.SolicitudServicio;
import com.backace.ace.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/servicios")
@CrossOrigin(origins = "http://localhost:5500")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping("/estado/{placaVehiculo}")
    public ResponseEntity<Map<String, String>> obtenerEstadoPorPlaca(@PathVariable String placaVehiculo) {
        Optional<SolicitudServicio> solicitud = servicioRepository.findTopByPlacaVehiculoOrderByFechaDesc(placaVehiculo);

        if (solicitud.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("estado", solicitud.get().getEstado()); // Retorna el estado del servicio en formato JSON
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "No se encontró el estado para la placa especificada."));
        }
    }
}
