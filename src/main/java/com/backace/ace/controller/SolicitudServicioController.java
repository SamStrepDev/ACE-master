package com.backace.ace.controller;

import com.backace.ace.model.SolicitudServicio;
import com.backace.ace.model.Usuario;
import com.backace.ace.service.SolicitudServicioService;
import com.backace.ace.service.EmailService;
import com.backace.ace.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "http://localhost:63342")
public class SolicitudServicioController {

    @Autowired
    private SolicitudServicioService solicitudServicioService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService; // Agregar el servicio de usuario

    @PostMapping
    public ResponseEntity<?> solicitarServicio(@RequestBody SolicitudServicio solicitud) {
        // Validar campos obligatorios
        if (solicitud.getCedula() == null || solicitud.getPlacaVehiculo() == null ||
                solicitud.getTipoServicio() == null || solicitud.getFecha() == null ||
                solicitud.getHora() == null || solicitud.getEmail() == null) {
            StringBuilder mensajeError = new StringBuilder("Faltan los siguientes campos: ");

            if (solicitud.getCedula() == null) {
                mensajeError.append("cedula, ");
            }
            if (solicitud.getPlacaVehiculo() == null) {
                mensajeError.append("placaVehiculo, ");
            }
            if (solicitud.getTipoServicio() == null) {
                mensajeError.append("tipoServicio, ");
            }
            if (solicitud.getFecha() == null) {
                mensajeError.append("fecha, ");
            }
            if (solicitud.getHora() == null) {
                mensajeError.append("hora, ");
            }
            if (solicitud.getEmail() == null) {
                mensajeError.append("email, ");
            }

            // Elimina la última coma y espacio
            mensajeError.setLength(mensajeError.length() - 2);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensajeError.toString());
        }

        // Verificar si el usuario existe usando solo el número de documento
        try {
            Usuario usuario = usuarioService.autenticarPorCedula(solicitud.getCedula());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

        // Guardar la solicitud
        SolicitudServicio nuevaSolicitud = solicitudServicioService.crearSolicitud(solicitud);

        // Enviar correo electrónico
        String asunto = "Solicitud de Servicio Recibida";
        String mensaje = "Estimado cliente, su solicitud de servicio para el vehículo con placa " +
                nuevaSolicitud.getPlacaVehiculo() + " ha sido recibida exitosamente. " +
                "Fecha del servicio: " + nuevaSolicitud.getFecha().toString() +
                " a las " + nuevaSolicitud.getHora() + ".";
        emailService.enviarEmail(nuevaSolicitud.getEmail(), asunto, mensaje);

        return ResponseEntity.status(HttpStatus.CREATED).body("Solicitud de servicio creada con éxito.");
    }
}

