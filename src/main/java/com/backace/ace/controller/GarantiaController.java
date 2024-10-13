package com.backace.ace.controller;

import com.backace.ace.model.Vehiculo; // Asegúrate de tener este modelo creado
import com.backace.ace.service.VehiculoService; // Servicio para manejar la lógica del vehículo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/garantia")
@CrossOrigin(origins = "http://localhost:63342")
public class GarantiaController {

    @Autowired
    private VehiculoService vehiculoService; // Servicio que maneja las operaciones del vehículo

    @GetMapping("/{placa}")
    public ResponseEntity<String> validarGarantia(@PathVariable String placa) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorPlaca(placa); // Método que busca el vehículo por placa

        if (vehiculo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehículo no encontrado.");
        }

        String fechaCompraStr = vehiculo.getFechaCompra(); // Suponiendo que tienes este campo en tu modelo
        LocalDate fechaCompra = LocalDate.parse(fechaCompraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int anioCompra = fechaCompra.getYear();
        int anioActual = LocalDate.now().getYear();
        int diferenciaAnos = anioActual - anioCompra;

        int añosGarantia;

        switch (vehiculo.getTipo()) { // Suponiendo que tienes un campo 'tipo' en el modelo
            case "pickup":
                añosGarantia = 3;
                break;
            case "taxi":
                añosGarantia = 2;
                break;
            case "particular":
            case "suv":
            case "van":
                añosGarantia = 2;
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de vehículo no válido.");
        }

        if (diferenciaAnos < añosGarantia) {
            return ResponseEntity.ok("El vehículo tiene garantía.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El vehículo no tiene garantía. No puede agendar la cita.");
        }
    }
}
