package com.backace.ace.service;

import com.backace.ace.model.Usuario;
import com.backace.ace.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para autenticar por nombre, correo y cédula
    public Usuario autenticar(String nombre, String correo, String cedula) {
        logger.info("Intentando autenticar usuario con Nombre: {}, Correo: {}, Cédula: {}", nombre, correo, cedula);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByNombreAndCorreoAndCedula(nombre, correo, cedula);

        if (!optionalUsuario.isPresent()) {
            logger.warn("Credenciales incorrectas.");
            throw new IllegalArgumentException("Credenciales incorrectas.");
        }

        logger.info("Usuario autenticado: {}", optionalUsuario.get());
        return optionalUsuario.get();
    }

    // Método para autenticar solo por cédula
    public Usuario autenticarPorCedula(String cedula) {
        logger.info("Intentando autenticar usuario con Cédula: {}", cedula);
        Optional<Usuario> optionalUsuario = usuarioRepository.findByCedula(cedula);

        if (!optionalUsuario.isPresent()) {
            logger.warn("Usuario no encontrado con Cédula: {}", cedula);
            throw new IllegalArgumentException("Usuario no encontrado.");
        }

        logger.info("Usuario autenticado por cédula: {}", optionalUsuario.get());
        return optionalUsuario.get();
    }
}
