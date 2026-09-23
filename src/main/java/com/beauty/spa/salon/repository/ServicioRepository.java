package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Servicio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para la tabla servicios. Un servicio solo se crea
 * asociado a un administrador (administradorId).
 */
public class ServicioRepository {

    public Servicio crear(Servicio servicio, int administradorId) throws SQLException {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio, duracion_minutos, "
                + "activo, administrador_id) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecio());
            ps.setInt(4, servicio.getDuracionMinutos());
            ps.setBoolean(5, servicio.isActivo());
            ps.setInt(6, administradorId);
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    servicio.setId(keys.getInt(1));
                }
            }
        }
        return servicio;
    }

    /** Lista solo los servicios activos (disponibles para facturar). */
    public List<Servicio> listarActivos() throws SQLException {
        return listar("SELECT id, nombre, descripcion, precio, duracion_minutos, activo "
                + "FROM servicios WHERE activo = TRUE ORDER BY nombre");
    }

    /** Lista todos los servicios, activos e inactivos. */
    public List<Servicio> listarTodos() throws SQLException {
        return listar("SELECT id, nombre, descripcion, precio, duracion_minutos, activo "
                + "FROM servicios ORDER BY nombre");
    }

    private List<Servicio> listar(String sql) throws SQLException {
        List<Servicio> servicios = new ArrayList<>();
        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                servicios.add(new Servicio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("duracion_minutos"),
                        rs.getBoolean("activo")
                ));
            }
        }
        return servicios;
    }
}
