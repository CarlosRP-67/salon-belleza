package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {

    public boolean registrar(Producto producto) {
        String sql = "INSERT INTO productos (nombre_producto, descripcion, precio, stock) VALUES (?, ?, ?, ?)";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al registrar el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre_producto = ?, descripcion = ?, precio = ?, stock = ? WHERE id_producto = ?";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setInt(4, producto.getStock());
            pstmt.setInt(5, producto.getIdProducto());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el producto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setInt(1, idProducto);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre_producto, descripcion, precio, stock FROM productos";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(producto);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los productos: " + e.getMessage());
        }

        return lista;
    }
}