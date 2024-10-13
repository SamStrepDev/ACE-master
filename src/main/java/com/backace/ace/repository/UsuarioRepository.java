package com.backace.ace.repository;

import com.backace.ace.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método para buscar por nombre, correo y cédula (ya existente)
    Optional<Usuario> findByNombreAndCorreoAndCedula(String nombre, String correo, String cedula);

    // Nuevo método para buscar solo por cédula
    Optional<Usuario> findByCedula(String cedula);
}
