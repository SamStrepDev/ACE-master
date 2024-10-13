package com.backace.ace.controller;

import com.backace.ace.model.Usuario;
import com.backace.ace.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:63342")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        // Validar si los campos son nulos o vacíos
        if (usuario.getNombre() == null || usuario.getNombre().isEmpty() ||
                usuario.getCorreo() == null || usuario.getCorreo().isEmpty() ||
                usuario.getCedula() == null || usuario.getCedula().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los campos nombre, correo y cédula son obligatorios.");
        }

        try {
            Usuario authenticatedUser = usuarioService.autenticar(
                    usuario.getNombre(),
                    usuario.getCorreo(),
                    usuario.getCedula()
            );
            return ResponseEntity.ok("Inicio de sesión exitoso. Bienvenido, " + authenticatedUser.getNombre());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
        } catch (Exception e) {
            // Captura cualquier otra excepción no controlada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado. Por favor, intente más tarde.");
        }
    }
}
