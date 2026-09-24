package main.java.com.beauty.spa.salon.model;

import java.math.BigDecimal;

/**
 * Representa una línea dentro de una factura: un servicio comprado,
 * con su cantidad, precio unitario y subtotal.
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
        this.servicioId = servicio.getIdServicio(); 
        this.servicioNombre = servicio.getNombreServicio();
        this.cantidad = cantidad;
        
        
        if (servicio.getPrecio() != null) {
            this.precioUnitario = servicio.getPrecio().doubleValue();
        } else {
            this.precioUnitario = 0.0;
        }
        
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
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}