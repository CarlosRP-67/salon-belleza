package main.java.com.beauty.spa.salon.service;

import main.java.com.beauty.spa.salon.model.CitaEmpleado;
import main.java.com.beauty.spa.salon.repository.CitaEmpleadoRepository;
import java.util.List;

public class CitaService {

    private final CitaEmpleadoRepository citaRepository = new CitaEmpleadoRepository();

    public boolean registrarCita(int idCliente, int idEmpleado, int idServicio, String fechaHora, String estado) {
        CitaEmpleado cita = new CitaEmpleado(idCliente, idEmpleado, idServicio, fechaHora, estado);
        return citaRepository.registrar(cita);
    }

    public boolean actualizarCita(int idCita, int idCliente, int idEmpleado, int idServicio, String fechaHora, String estado) {
        CitaEmpleado cita = new CitaEmpleado(idCita, idCliente, idEmpleado, idServicio, fechaHora, estado);
        return citaRepository.actualizar(cita);
    }

    public boolean eliminarCita(int idCita) {
        return citaRepository.eliminar(idCita);
    }

    public List<CitaEmpleado> obtenerTodasLasCitas() {
        return citaRepository.listarTodas();
    }
}