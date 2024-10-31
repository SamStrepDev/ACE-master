package com.backace.ace.controller;

import com.backace.ace.model.LoginDto;
import com.backace.ace.model.Usuario;
import com.backace.ace.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:5500")
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            Usuario authenticatedUser = usuarioService.autenticar(loginDto.getNombre(), loginDto.getCorreo(), loginDto.getCedula());
            // Devolver los datos del usuario autenticado
            return ResponseEntity.ok().body(authenticatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado.");
        }
    }
}


