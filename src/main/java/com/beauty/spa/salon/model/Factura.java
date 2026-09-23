package main.java.com.beauty.spa.salon.model;

<<<<<<< HEAD
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la factura generada cuando un usuario compra uno o
 * varios servicios.
 */
public class Factura {

    private int id;
    private int usuarioId;
    private String usuarioNombre;
    private LocalDateTime fecha;
    private double total;
    private List<DetalleFactura> detalles;

    public Factura() {
        this.detalles = new ArrayList<>();
    }

    public Factura(int usuarioId) {
        this();
        this.usuarioId = usuarioId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
    }

    public void agregarDetalle(DetalleFactura detalle) {
        this.detalles.add(detalle);
        recalcularTotal();
    }

    public void recalcularTotal() {
        this.total = detalles.stream()
                .mapToDouble(DetalleFactura::getSubtotal)
                .sum();
    }
}
=======
public class Factura {
    private int idFactura;
    private int idCliente;
    private String fechaFactura;
    private double total;

    public Factura() {}

    public Factura(int idFactura, int idCliente, String fechaFactura, double total) {
        this.idFactura = idFactura;
        this.idCliente = idCliente;
        this.fechaFactura = fechaFactura;
        this.total = total;
    }

    public Factura(int idCliente, String fechaFactura, double total) {
        this.idCliente = idCliente;
        this.fechaFactura = fechaFactura;
        this.total = total;
    }

    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getFechaFactura() { return fechaFactura; }
    public void setFechaFactura(String fechaFactura) { this.fechaFactura = fechaFactura; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
