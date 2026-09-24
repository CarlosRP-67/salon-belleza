package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FacturaRepository {

    public boolean registrar(Factura factura) {
        String sql = "INSERT INTO facturas (id_cliente, fecha_factura, total) VALUES (?, ?, ?)";
        
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, factura.getIdCliente());
            ps.setString(2, factura.getFechaFactura());
            ps.setDouble(3, factura.getTotal());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar la factura: " + e.getMessage());
            return false;
        }
    }

    public List<Factura> listarTodas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT id_factura, id_cliente, fecha_factura, total FROM facturas";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
            System.err.println("Error al listar las facturas: " + e.getMessage());
        }

        return lista;
    }
}