package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Usuario;
<<<<<<< HEAD

=======
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
<<<<<<< HEAD
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la tabla usuarios (clientes del salón).
 */
public class UsuarioRepository {

    public Usuario crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, email, telefono) VALUES (?, ?, ?)";
        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelefono());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    usuario.setId(keys.getInt(1));
                }
            }
        }
        return usuario;
    }

    public List<Usuario> listar() throws SQLException {
        String sql = "SELECT id, nombre, email, telefono FROM usuarios ORDER BY nombre";
        List<Usuario> usuarios = new ArrayList<>();

        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono")
                ));
            }
        }
        return usuarios;
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, email, telefono FROM usuarios WHERE id = ?";
        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("telefono")
                    );
                }
            }
        }
        return null;
    }
}
=======
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    public boolean registrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, apellido, nombre_usuario, correo, contrasena, id_rol) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getNombreUsuario());
            pstmt.setString(4, usuario.getCorreo());
            pstmt.setString(5, usuario.getContrasena()); 
            pstmt.setInt(6, usuario.getIdRol());
            
            return pstmt.executeUpdate() > 0;
            
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
            
            return pstmt.executeUpdate() > 0;
            
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
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
