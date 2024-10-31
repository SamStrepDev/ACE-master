package com.backace.ace.repository;

import com.backace.ace.model.SolicitudServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<SolicitudServicio, Integer> {

    Optional<SolicitudServicio> findTopByPlacaVehiculoOrderByFechaDesc(String placaVehiculo);
}

