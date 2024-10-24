package com.backace.ace.controller;

import com.backace.ace.model.Vehiculo;
import com.backace.ace.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/garantia")
@CrossOrigin(origins = "http://localhost:5500")
public class GarantiaController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/{placaVehiculo}")
    public ResponseEntity<String> validarGarantia(@PathVariable String placaVehiculo) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoPorPlaca(placaVehiculo);

        if (vehiculo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehículo no encontrado.");
        }

        String fechaCompraStr = vehiculo.getFechaCompra();
        LocalDate fechaCompra;

        // Intentar parsear la fecha de compra
        try {
            fechaCompra = LocalDate.parse(fechaCompraStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de fecha de compra inválido.");
        }

        int anioCompra = fechaCompra.getYear();
        int anioActual = LocalDate.now().getYear();
        int diferenciaAnos = anioActual - anioCompra;

        int añosGarantia;

        // Manejo del tipo de vehículo
        switch (vehiculo.getTipo().toLowerCase()) {
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

        // Verificación de garantía
        if (diferenciaAnos < añosGarantia) {
            return ResponseEntity.ok("El vehículo tiene garantía.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("El vehículo no tiene garantía.");
        }
    }
}
