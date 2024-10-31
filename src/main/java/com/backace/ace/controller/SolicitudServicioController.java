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

        // Configurar el estado de la solicitud
        solicitud.setEstado("Ingresado");

        // Guardar la solicitud
        SolicitudServicio nuevaSolicitud = solicitudServicioService.crearSolicitud(solicitud);

        // Enviar correo electrónico
        String asunto = "Solicitud de Servicio Recibida";

        String mensaje = "<html>" +
                "<body style='font-family: Arial, sans-serif; color: #333;'>" +
                "<h2 style='color: #202C45;'>Estimado " + usuario.getNombre() + ",</h2>" +
                "<p>Su solicitud de servicio para el vehículo con placa <strong>" + nuevaSolicitud.getPlacaVehiculo() + "</strong> ha sido recibida exitosamente.</p>" +
                "<p><strong>Tipo de Servicio:</strong> " + solicitud.getTipoServicio() + "</p>"+
                "<p><strong>Fecha del servicio:</strong> " + nuevaSolicitud.getFecha() + " a las " + nuevaSolicitud.getHora() + ".</p>" +
                "<p style='color: #202C45;'>Gracias por confiar en nuestros servicios. 😊</p>" +
                "<p style='color: #555; font-size: 12px;'>Este es un mensaje generado automáticamente, por favor no responda a este correo.</p>" +
                "<br><br>" +
                "<hr>" +
                "<p style='font-size: 14px; color: #333;'> <br>" +
                "<strong style='color: #d08a2c; font-size: 18px;'>AutoCareEase</strong><br>" +
                "<span style='color: #666; font-size: 14px;'>Optimización de procesos postventa en concesionarias</span><br><br>" +
                "<span style='font-size: 14px;'>" +
                "<strong>Móvil:</strong> <a href='tel:+573205205497' style='color: #2E86C1;'>+57 320 5205497</a> | " +
                "<strong>Teléfono:</strong> <a href='tel:+573054756496' style='color: #2E86C1;'>+57 305 4756496</a><br>" +
                "<strong>Correo electrónico:</strong> <a href='mailto:autocareeasemail@gmail.com' style='color: #2E86C1;'>autocareeasemail@gmail.com</a><br>" +
                "AutoCareEase, Diagonal 30 50, Cartagena, Bolívar 130015<br>" +
                "<a href='http://www.autocareease.com' style='color: #d08a2c;'>www.autocareease.com</a></span><br><br>" +
                "<span style='font-size: 20px; color: #d08a2c;'>Síguenos:</span><br>" +
                "<a href='https://www.facebook.com/people/Auto-Care-Ease/61567536633007/'><img src='https://i.imgur.com/A5fU5Wf.png' alt='Facebook' style='width: 20px; height: 20px;'></a> " +
                "<a href='https://www.instagram.com/autocareease/'><img src='https://i.imgur.com/2kJeBhE.png' alt='Instagram' style='width: 20px; height: 20px;'></a> " +
                "<a href='https://www.youtube.com/channel/UCxJ-eUYAhK1Q72a7Jxgpkdg'><img src='https://i.imgur.com/rK1sRql.png' alt='YouTube' style='width: 20px; height: 20px;'></a> " +
                "<a href='https://www.linkedin.com/in/autocareease-autocareease-171413335/'><img src='https://i.imgur.com/mT54n0e.png' alt='LinkedIn' style='width: 20px; height: 20px;'></a> " +
                "<a href='https://x.com/AutoCareEase'><img src='https://i.imgur.com/ZHN8zr2.png' alt='Twitter' style='width: 20px; height: 20px;'></a>" +
                "</p>" +
                "</body>" +
                "</html>";

        // Enviar el correo usando el servicio de email con formato HTML
        emailService.enviarEmail(usuario.getCorreo(), asunto, mensaje);

        // Retornar respuesta exitosa
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("{\"message\": \"Solicitud de servicio creada con éxito.\"}");
    }
}
