package main.java.com.beauty.spa.salon.service;

import main.java.com.beauty.spa.salon.model.Cita;
import main.java.com.beauty.spa.salon.repository.CitaRepository;
import java.util.List;

public class CitaService {

    private final CitaRepository citaRepository = new CitaRepository();

    public boolean registrarCita(int idCliente, int idEmpleado, int idServicio, String fechaHora, String estado) {
        Cita cita = new Cita(idCliente, idEmpleado, idServicio, fechaHora, estado);
        return citaRepository.registrar(cita);
    }

    public boolean actualizarCita(int idCita, int idCliente, int idEmpleado, int idServicio, String fechaHora, String estado) {
        Cita cita = new Cita(idCita, idCliente, idEmpleado, idServicio, fechaHora, estado);
        return citaRepository.actualizar(cita);
    }

    public boolean eliminarCita(int idCita) {
        return citaRepository.eliminar(idCita);
    }

    public List<Cita> obtenerTodasLasCitas() {
        return citaRepository.listarTodas();
    }
}