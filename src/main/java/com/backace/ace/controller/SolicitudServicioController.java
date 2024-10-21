package com.backace.ace.controller;

import com.backace.ace.model.SolicitudServicio;
import com.backace.ace.service.EmailService;
import com.backace.ace.service.SolicitudServicioService;
import com.backace.ace.service.UsuarioService;
import com.backace.ace.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
@CrossOrigin(origins = "http://localhost:5500")
public class SolicitudServicioController {

    @Autowired
    private SolicitudServicioService solicitudServicioService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> solicitarServicio(@RequestBody SolicitudServicio solicitud) {
        // Validar campos obligatorios
        if (solicitud.getCedula() == null || solicitud.getPlacaVehiculo() == null ||
                solicitud.getTipoServicio() == null || solicitud.getFecha() == null ||
                solicitud.getHora() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"Todos los campos son obligatorios.\"}");
        }

        // Verificar si el usuario existe usando solo la cédula
        Usuario usuario;
        try {
            usuario = usuarioService.autenticarPorCedula(solicitud.getCedula());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }

        // Guardar la solicitud
        SolicitudServicio nuevaSolicitud = solicitudServicioService.crearSolicitud(solicitud);

        // Enviar correo electrónico
        String asunto = "Solicitud de Servicio Recibida";
        String mensaje = "Estimado " + usuario.getNombre() + ", su solicitud de servicio para el vehículo con placa " +
                nuevaSolicitud.getPlacaVehiculo() + " ha sido recibida exitosamente. " +
                "Fecha del servicio: " + nuevaSolicitud.getFecha() +
                " a las " + nuevaSolicitud.getHora() + ".";
        emailService.enviarEmail(usuario.getCorreo(), asunto, mensaje); // Enviar al correo del usuario

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("{\"message\": \"Solicitud de servicio creada con éxito.\"}");
    }
}
