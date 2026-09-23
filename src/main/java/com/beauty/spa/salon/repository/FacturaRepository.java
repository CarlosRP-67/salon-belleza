package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FacturaRepository {

    public boolean registrar(Factura factura) {
        String sql = "INSERT INTO facturas (id_cliente, fecha_factura, total) VALUES (?, ?, ?)";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, factura.getIdCliente());
            pstmt.setString(2, factura.getFechaFactura());
            pstmt.setDouble(3, factura.getTotal());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar factura: " + e.getMessage());
            return false;
        }
    }

    public List<Factura> listarTodas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT id_factura, id_cliente, fecha_factura, total FROM facturas";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Factura factura = new Factura(
                    rs.getInt("id_factura"),
                    rs.getInt("id_cliente"),
                    rs.getString("fecha_factura"),
                    rs.getDouble("total")
                );
                lista.add(factura);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar facturas: " + e.getMessage());
        }

        return lista;
    }
}