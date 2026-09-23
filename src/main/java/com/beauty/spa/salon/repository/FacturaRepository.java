package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.DetalleFactura;
import main.java.com.beauty.spa.salon.model.Factura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Acceso a datos para facturas (compras de servicios). La creación de
 * una factura inserta la cabecera y todos sus detalles dentro de una
 * misma transacción, para que no queden facturas a medias si algo falla.
 */
public class FacturaRepository {

    public Factura crearFactura(Factura factura) throws SQLException {
        if (factura.getDetalles() == null || factura.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La factura debe tener al menos un servicio.");
        }

        factura.recalcularTotal();

        String sqlFactura = "INSERT INTO facturas (usuario_id, total) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO detalle_factura "
                + "(factura_id, servicio_id, cantidad, precio_unitario, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        Connection conn = DataBaseConnection.getConnection();
        boolean autoCommitOriginal = conn.getAutoCommit();

        try {
            conn.setAutoCommit(false);

            int facturaId;
            try (PreparedStatement ps = conn.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, factura.getUsuarioId());
                ps.setDouble(2, factura.getTotal());
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    facturaId = keys.getInt(1);
                }
            }
            factura.setId(facturaId);

            try (PreparedStatement ps = conn.prepareStatement(sqlDetalle)) {
                for (DetalleFactura detalle : factura.getDetalles()) {
                    ps.setInt(1, facturaId);
                    ps.setInt(2, detalle.getServicioId());
                    ps.setInt(3, detalle.getCantidad());
                    ps.setDouble(4, detalle.getPrecioUnitario());
                    ps.setDouble(5, detalle.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(autoCommitOriginal);
        }

        return factura;
    }

    /** Lista las facturas con el nombre del usuario, de más reciente a más antigua. */
    public List<Factura> listar() throws SQLException {
        String sql = "SELECT f.id, f.usuario_id, u.nombre AS usuario_nombre, f.fecha, f.total "
                + "FROM facturas f JOIN usuarios u ON f.usuario_id = u.id "
                + "ORDER BY f.fecha DESC";

        List<Factura> facturas = new ArrayList<>();
        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Factura factura = new Factura(rs.getInt("usuario_id"));
                factura.setId(rs.getInt("id"));
                factura.setUsuarioNombre(rs.getString("usuario_nombre"));
                Timestamp fecha = rs.getTimestamp("fecha");
                if (fecha != null) {
                    factura.setFecha(fecha.toLocalDateTime());
                }
                factura.setTotal(rs.getDouble("total"));
                facturas.add(factura);
            }
        }
        return facturas;
    }

    /** Trae el detalle (líneas) de una factura específica. */
    public List<DetalleFactura> listarDetalles(int facturaId) throws SQLException {
        String sql = "SELECT d.id, d.servicio_id, s.nombre AS servicio_nombre, d.cantidad, "
                + "d.precio_unitario, d.subtotal "
                + "FROM detalle_factura d JOIN servicios s ON d.servicio_id = s.id "
                + "WHERE d.factura_id = ?";

        List<DetalleFactura> detalles = new ArrayList<>();
        Connection conn = DataBaseConnection.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, facturaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleFactura detalle = new DetalleFactura();
                    detalle.setId(rs.getInt("id"));
                    detalle.setServicioId(rs.getInt("servicio_id"));
                    detalle.setServicioNombre(rs.getString("servicio_nombre"));
                    detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    detalle.setCantidad(rs.getInt("cantidad"));
                    detalle.setSubtotal(rs.getDouble("subtotal"));
                    detalles.add(detalle);
                }
            }
        }
        return detalles;
    }
}
