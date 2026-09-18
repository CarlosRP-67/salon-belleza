package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author informatica
 */
public class UsuarioRepository {

    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, nombre_usuario, correo, contrasena, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = DataBaseConnection.getDataBaseConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getNombreUsuario());
            pstmt.setString(4, usuario.getCorreo());
            pstmt.setString(5, usuario.getContrasena()); 
            pstmt.setInt(6, usuario.getIdRol());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar el usuario en la BD: " + e.getMessage());
            return false;
        }
    }
}