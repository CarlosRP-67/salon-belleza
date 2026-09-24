package main.java.com.beauty.spa.salon.service;

import main.java.com.beauty.spa.salon.model.ProductoEmpleado;
import main.java.com.beauty.spa.salon.repository.ProductoRepository;
import java.util.List;

public class ProductoService {

    private final ProductoRepository productoRepository = new ProductoRepository();

    public boolean registrarProducto(String nombre, String descripcion, double precio, int stock) {
        if (nombre == null || nombre.trim().isEmpty() || precio < 0 || stock < 0) {
            return false;
        }

        ProductoEmpleado producto = new ProductoEmpleado(nombre.trim(), descripcion != null ? descripcion.trim() : "", precio, stock);
        return productoRepository.registrar(producto);
    }

    public boolean actualizarProducto(int idProducto, String nombre, String descripcion, double precio, int stock) {
        if (idProducto <= 0 || nombre == null || nombre.trim().isEmpty() || precio < 0 || stock < 0) {
            return false;
        }

        ProductoEmpleado producto = new ProductoEmpleado(idProducto, nombre.trim(), descripcion != null ? descripcion.trim() : "", precio, stock);
        return productoRepository.actualizar(producto);
    }

    public boolean eliminarProducto(int idProducto) {
        if (idProducto <= 0) return false;
        return productoRepository.eliminar(idProducto);
    }

    public List<ProductoEmpleado> obtenerTodosLosProductos() {
        return productoRepository.listarTodos();
    }
}