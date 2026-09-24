package main.java.com.beauty.spa.salon.model;

import java.math.BigDecimal;

/**
 * Representa un servicio del salón.
 */
public class Servicio {

    private int idServicio;
    private String nombreServicio;
    private String descripcion;
    private BigDecimal precio;
    private int duracionMinutos;
    private boolean activo;

    public Servicio() {
        this.activo = true;
    }

    // Constructor completo usado por el repositorio
    public Servicio(int idServicio, String nombreServicio, String descripcion, BigDecimal precio, int duracionMinutos, boolean activo) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.precio = precio != null ? precio : BigDecimal.ZERO;
        this.duracionMinutos = duracionMinutos;
        this.activo = activo;
    }

    public Servicio(int idServicio, String nombreServicio, String descripcion, double precioDouble, int duracionMinutos, boolean activo) {
        this(idServicio, nombreServicio, descripcion, BigDecimal.valueOf(precioDouble), duracionMinutos, activo);
    }

    public Servicio(String nombreServicio, String descripcion, BigDecimal precio, int duracionMinutos) {
        this(0, nombreServicio, descripcion, precio, duracionMinutos, true);
    }

    public Servicio(String nombreServicio, String descripcion, double precioDouble, int duracionMinutos) {
        this(0, nombreServicio, descripcion, BigDecimal.valueOf(precioDouble), duracionMinutos, true);
    }


    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public int getId() {
        return idServicio;
    }

    public void setId(int id) {
        this.idServicio = id;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getNombre() {
        return nombreServicio;
    }

    public void setNombre(String nombre) {
        this.nombreServicio = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio != null ? precio : BigDecimal.ZERO;
    }

    public double getPrecioDouble() {
        return precio != null ? precio.doubleValue() : 0.0;
    }

    public void setPrecio(double precioDouble) {
        this.precio = BigDecimal.valueOf(precioDouble);
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return nombreServicio + " - Q" + precio;
    }
}