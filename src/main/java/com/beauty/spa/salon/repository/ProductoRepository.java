package main.java.com.beauty.spa.salon.repository;


import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {

    public List<Productos> obtenerProductosDisponibles() {
        List<Productos> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre_producto, descripcion, precio, stock FROM productos WHERE stock > 0";

        try (Connection conn = DataBaseConnection.getDataBaseConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                Productos p = new Productos(
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar productos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }


    public boolean realizarCompra(int idCliente, int idProducto, int cantidad, double total) {
        String sqlVenta = "INSERT INTO ventas (id_cliente, id_producto, cantidad, total, fecha_venta) VALUES (?, ?, ?, ?, NOW())";
        String sqlUpdateStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";

        try (Connection conn = DataBaseConnection.getDataBaseConnection()) {
            conn.setAutoCommit(false); 

            try (PreparedStatement stmVenta = conn.prepareStatement(sqlVenta);
                 PreparedStatement stmStock = conn.prepareStatement(sqlUpdateStock)) {

                stmVenta.setInt(1, idCliente);
                stmVenta.setInt(2, idProducto);
                stmVenta.setInt(3, cantidad);
                stmVenta.setDouble(4, total);
                stmVenta.executeUpdate();

                stmStock.setInt(1, cantidad);
                stmStock.setInt(2, idProducto);
                stmStock.executeUpdate();

                conn.commit();
                return true;

            } catch (Exception e) {
                conn.rollback();
                System.err.println("Error al ejecutar la compra: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
