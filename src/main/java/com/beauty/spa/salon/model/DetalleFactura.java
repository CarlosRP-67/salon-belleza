package main.java.com.beauty.spa.salon.model;

/**
 * Representa una línea dentro de una factura: un servicio comprado,
 * con su cantidad y subtotal.
 */
public class DetalleFactura {

    private int id;
    private int servicioId;
    private String servicioNombre;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public DetalleFactura() {
    }

    public DetalleFactura(Servicio servicio, int cantidad) {
        this.servicioId = servicio.getId();
        this.servicioNombre = servicio.getNombre();
        this.precioUnitario = servicio.getPrecio() != null ? servicio.getPrecio().doubleValue() : 0.0;
        this.cantidad = cantidad;
        this.subtotal = this.precioUnitario * cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public String getServicioNombre() {
        return servicioNombre;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = this.precioUnitario * cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}