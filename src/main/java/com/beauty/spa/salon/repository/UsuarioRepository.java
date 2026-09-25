package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, nombre_usuario, correo, contrasena, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getNombreUsuario());
            pstmt.setString(4, usuario.getCorreo());
            pstmt.setString(5, usuario.getContrasena()); 
            pstmt.setInt(6, usuario.getIdRol());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                if (usuario.getIdRol() == 2) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            int idUsuario = rs.getInt(1);
                            String sqlCliente = "INSERT INTO clientes (id_usuario, telefono, direccion) VALUES (?, '', '')";
                            try (PreparedStatement pstmtCliente = conexion.prepareStatement(sqlCliente)) {
                                pstmtCliente.setInt(1, idUsuario);
                                pstmtCliente.executeUpdate();
                            }
                        }
                    }
                }
                return true;
            }
            return false;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario en la BD: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, nombre_usuario = ?, correo = ?, contrasena = ?, id_rol = ? WHERE id_usuario = ?";
        
        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getNombreUsuario());
            pstmt.setString(4, usuario.getCorreo());
            pstmt.setString(5, usuario.getContrasena());
            pstmt.setInt(6, usuario.getIdRol());
            pstmt.setInt(7, usuario.getIdUsuario());
            
            boolean actualizado = pstmt.executeUpdate() > 0;
            
            if (actualizado && usuario.getIdRol() == 2) {
                String checkSql = "SELECT COUNT(*) FROM clientes WHERE id_usuario = ?";
                try (PreparedStatement checkStmt = conexion.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, usuario.getIdUsuario());
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            String insertCliente = "INSERT INTO clientes (id_usuario, telefono, direccion) VALUES (?, '', '')";
                            try (PreparedStatement insertStmt = conexion.prepareStatement(insertCliente)) {
                                insertStmt.setInt(1, usuario.getIdUsuario());
                                insertStmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            
            return actualizado;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar el usuario en la BD: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        
        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setInt(1, idUsuario);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar el usuario en la BD: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, nombre, apellido, nombre_usuario, correo, contrasena, id_rol FROM usuarios";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("nombre_usuario"),
                    rs.getString("correo"),
                    rs.getString("contrasena"),
                    rs.getInt("id_rol")
                );
                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los usuarios desde la BD: " + e.getMessage());
        }

        return lista;
    }
}