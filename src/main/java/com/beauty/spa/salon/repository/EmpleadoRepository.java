package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.MySQLConnection;
import main.java.com.beauty.spa.salon.model.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepository {

    // Listar todos los empleados
    public List<Empleado> listarTodos() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT e.id_empleado, e.id_usuario, e.especialidad, e.telefono, e.horario_trabajo, "
                   + "u.nombre, u.apellido, u.correo, u.nombre_usuario "
                   + "FROM empleados e "
                   + "INNER JOIN usuarios u ON e.id_usuario = u.id_usuario";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                    rs.getInt("id_empleado"),
                    rs.getInt("id_usuario"),
                    rs.getString("especialidad"),
                    rs.getString("telefono"),
                    rs.getString("horario_trabajo"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo"),
                    rs.getString("nombre_usuario")
                );
                lista.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    // Insertar un nuevo empleado
    public boolean guardar(Empleado empleado) {
        String sql = "INSERT INTO empleados (id_usuario, especialidad, telefono, horario_trabajo) VALUES (?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empleado.getIdUsuario());
            stmt.setString(2, empleado.getEspecialidad());
            stmt.setString(3, empleado.getTelefono());
            stmt.setString(4, empleado.getHorarioTrabajo());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar empleado: " + e.getMessage());
            return false;
        }
    }

    // Actualizar datos de un empleado
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET especialidad = ?, telefono = ?, horario_trabajo = ? WHERE id_empleado = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getEspecialidad());
            stmt.setString(2, empleado.getTelefono());
            stmt.setString(3, empleado.getHorarioTrabajo());
            stmt.setInt(4, empleado.getIdEmpleado());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un empleado por ID
    public boolean eliminar(int idEmpleado) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    // Buscar empleado por ID
    public Empleado obtenerPorId(int idEmpleado) {
        String sql = "SELECT e.id_empleado, e.id_usuario, e.especialidad, e.telefono, e.horario_trabajo, "
                   + "u.nombre, u.apellido, u.correo, u.nombre_usuario "
                   + "FROM empleados e "
                   + "INNER JOIN usuarios u ON e.id_usuario = u.id_usuario "
                   + "WHERE e.id_empleado = ?";

        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getInt("id_usuario"),
                        rs.getString("especialidad"),
                        rs.getString("telefono"),
                        rs.getString("horario_trabajo"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        rs.getString("nombre_usuario")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por ID: " + e.getMessage());
        }
        return null;
    }
}