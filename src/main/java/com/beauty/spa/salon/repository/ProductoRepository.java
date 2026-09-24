package main.java.com.beauty.spa.salon.repository;

import main.java.com.beauty.spa.salon.config.DataBaseConnection;
import main.java.com.beauty.spa.salon.model.ProductoEmpleado;
import main.java.com.beauty.spa.salon.model.ProductoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {

    public boolean registrar(ProductoEmpleado producto) {
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

    public boolean actualizar(ProductoEmpleado producto) {
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

    public List<ProductoEmpleado> listarTodos() {
        List<ProductoEmpleado> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre_producto, descripcion, precio, stock FROM productos";

        try (Connection conexion = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ProductoEmpleado producto = new ProductoEmpleado(
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

    public List<ProductoUsuario> obtenerProductosDisponibles() {
        List<ProductoUsuario> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre_producto, descripcion, precio, stock FROM productos WHERE stock > 0";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                ProductoUsuario p = new ProductoUsuario(
                    rs.getInt("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar productos disponibles: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public boolean realizarCompra(int idCliente, int idProducto, int cantidad, double total) {
        // Si el idCliente es 0 o negativo (porque no hay sesión iniciada), 
        // asignamos por defecto el ID 1 para que cualquier usuario pueda comprar sin restricciones.
        if (idCliente <= 0) {
            idCliente = 1; 
        }

        System.out.println("DEBUG - Realizando compra con idCliente: " + idCliente + ", idProducto: " + idProducto);

        String sqlFactura = "INSERT INTO facturas (id_cliente, fecha_factura, total) VALUES (?, NOW(), ?)";
        String sqlDetalle = "INSERT INTO detalles_facturas (id_factura, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        String sqlUpdateStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";

        try (Connection conn = DataBaseConnection.getConnection()) {
            conn.setAutoCommit(false); 

            try (PreparedStatement stmFactura = conn.prepareStatement(sqlFactura, PreparedStatement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmDetalle = conn.prepareStatement(sqlDetalle);
                 PreparedStatement stmStock = conn.prepareStatement(sqlUpdateStock)) {

                // 1. Insertar la cabecera de la factura
                stmFactura.setInt(1, idCliente);
                stmFactura.setDouble(2, total);
                stmFactura.executeUpdate();

                // Obtener el ID de la factura generada automáticamente
                ResultSet generatedKeys = stmFactura.getGeneratedKeys();
                int idFactura = -1;
                if (generatedKeys.next()) {
                    idFactura = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID de la factura generada.");
                }

                // 2. Insertar el detalle de la compra vinculado
                double precioUnitario = total / cantidad;
                stmDetalle.setInt(1, idFactura);
                stmDetalle.setInt(2, idProducto);
                stmDetalle.setInt(3, cantidad);
                stmDetalle.setDouble(4, precioUnitario);
                stmDetalle.executeUpdate();

                // 3. Descontar el stock del producto
                stmStock.setInt(1, cantidad);
                stmStock.setInt(2, idProducto);
                stmStock.executeUpdate();

                // Confirmar transacción
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