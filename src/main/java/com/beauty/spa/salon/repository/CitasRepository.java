package main.java.com.beauty.spa.salon.repository;
import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Citas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CitasRepository {

    public boolean guardarCita(Citas citas) {
        String sql = "INSERT INTO citas (id_cliente, id_empleado, id_servicio, fecha_hora_cita, estado_cita) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DataBaseConnection.getDataBaseConnection(); 
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setInt(1, citas.getIdCliente());
            stm.setInt(2, citas.getIdEmpleado());
            stm.setInt(3, citas.getIdServicio());
            stm.setTimestamp(4, Timestamp.valueOf(citas.getFechaHoraCita()));
            stm.setString(5, citas.getEstadoCita());
            
            int filasAfectadas = stm.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (Exception e) {
            System.err.println("Error al insertar cita en la BD: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<String> obtenerCitasPorCliente(int idCliente) {
        List<String> listaCitas = new ArrayList<>();
        String sql = "SELECT s.nombre_servicio, c.fecha_hora_cita, c.estado_cita " +
                     "FROM citas c " +
                     "INNER JOIN servicios s ON c.id_servicio = s.id_servicio " +
                     "WHERE c.id_cliente = ?";

        try (Connection conn = DataBaseConnection.getDataBaseConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, idCliente);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String servicio = rs.getString("nombre_servicio");
                Timestamp fechaHora = rs.getTimestamp("fecha_hora_cita");
                String estado = rs.getString("estado_cita");
                
                String citaFormateada = servicio + " - " + fechaHora.toString().substring(0, 16) + " [" + estado + "]";
                listaCitas.add(citaFormateada);
            }

        } catch (Exception e) {
            System.err.println("Error al cargar las citas desde la BD: " + e.getMessage());
            e.printStackTrace();
        }

        return listaCitas;
    }
}
