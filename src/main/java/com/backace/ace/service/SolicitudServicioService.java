package com.backace.ace.service;

import com.backace.ace.model.SolicitudServicio;
import com.backace.ace.repository.SolicitudServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudServicioService {

    @Autowired
    private SolicitudServicioRepository solicitudServicioRepository;

    public SolicitudServicio crearSolicitud(SolicitudServicio solicitud) {
        return solicitudServicioRepository.save(solicitud);
    }
}
