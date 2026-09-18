package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    public boolean guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (id_usuario, telefono, direccion) VALUES (?, ?, ?)";
        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, cliente.getIdUsuario());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getDireccion());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET id_usuario = ?, telefono = ?, direccion = ? WHERE id_cliente = ?";
        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, cliente.getIdUsuario());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getDireccion());
            pstmt.setInt(4, cliente.getIdCliente());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idCliente);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT c.id_cliente, c.id_usuario, u.nombre, u.apellido, c.telefono, c.direccion " +
                     "FROM clientes c " +
                     "INNER JOIN usuarios u ON c.id_usuario = u.id_usuario";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                    rs.getInt("id_cliente"),
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("direccion")
                );
                lista.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }
}