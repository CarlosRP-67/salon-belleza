package main.java.com.beauty.spa.salon.service;

import main.java.com.beauty.spa.salon.model.Servicio;
import main.java.com.beauty.spa.salon.repository.ServicioRepository;

import java.sql.SQLException;
import java.util.List;

public class ServicioService {

    private final ServicioRepository servicioRepository = new ServicioRepository();

    public List<Servicio> listarServicios() {
        try {
            return servicioRepository.findAll();
        } catch (SQLException e) {
            throw new IllegalStateException("No se pudieron cargar los servicios. Intenta de nuevo.", e);
        }
    }
}