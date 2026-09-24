package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Acceso a datos para la tabla administradores. Se usa para validar
 * el inicio de sesión antes de permitir crear servicios.
 */
public class AdministradorRepository {

    /**
     * Valida usuario y contraseña.
     * @return el Administrador si las credenciales son correctas, o null si no.
     */
    public Administrador autenticar(String usuario, String password) throws SQLException {
        String sql = "SELECT id, nombre, usuario, password FROM administradores "
                + "WHERE usuario = ? AND password = ?";

        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Administrador(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("usuario"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null;
    }
}
