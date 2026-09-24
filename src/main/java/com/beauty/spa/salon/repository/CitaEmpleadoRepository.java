package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.CitaEmpleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CitaEmpleadoRepository {

    public boolean registrar(CitaEmpleado cita) {
        String sql = "INSERT INTO citas (id_cliente, id_empleado, id_servicio, fecha_hora_cita, estado_cita) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, cita.getIdCliente());
            pstmt.setInt(2, cita.getIdEmpleado());
            pstmt.setInt(3, cita.getIdServicio());
            pstmt.setString(4, cita.getFechaHora());
            pstmt.setString(5, cita.getEstado());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar cita: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(CitaEmpleado cita) {
        String sql = "UPDATE citas SET id_cliente = ?, id_empleado = ?, id_servicio = ?, fecha_hora_cita = ?, estado_cita = ? WHERE id_cita = ?";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, cita.getIdCliente());
            pstmt.setInt(2, cita.getIdEmpleado());
            pstmt.setInt(3, cita.getIdServicio());
            pstmt.setString(4, cita.getFechaHora());
            pstmt.setString(5, cita.getEstado());
            pstmt.setInt(6, cita.getIdCita());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar cita: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idCita) {
        String sql = "DELETE FROM citas WHERE id_cita = ?";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idCita);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar cita: " + e.getMessage());
            return false;
        }
    }

    public List<CitaEmpleado> listarTodas() {
        List<CitaEmpleado> lista = new ArrayList<>();
        String sql = "SELECT id_cita, id_cliente, id_empleado, id_servicio, fecha_hora_cita, estado_cita FROM citas";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                CitaEmpleado cita = new CitaEmpleado(
                    rs.getInt("id_cita"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_empleado"),
                    rs.getInt("id_servicio"),
                    rs.getString("fecha_hora_cita"),
                    rs.getString("estado_cita")
                );
                lista.add(cita);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar citas: " + e.getMessage());
        }

        return lista;
    }
}