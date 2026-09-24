package main.java.com.beauty.spa.salon.model;

import java.math.BigDecimal;

/**
 * Representa un servicio del salón (por ejemplo: corte de cabello,
 * manicure, tinte, etc.).
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

    public Servicio(int idServicio, String nombreServicio, String descripcion,
                    BigDecimal precio, int duracionMinutos) {
        this(idServicio, nombreServicio, descripcion, precio, duracionMinutos, true);
    }

    public Servicio(int idServicio, String nombreServicio, String descripcion,
                    BigDecimal precio, int duracionMinutos, boolean activo) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
        this.activo = activo;
    }

    // Constructor opcional usando double por si en un DAO o vista se pasa double
    public Servicio(int idServicio, String nombreServicio, String descripcion,
                    double precioDouble, int duracionMinutos, boolean activo) {
        this(idServicio, nombreServicio, descripcion, BigDecimal.valueOf(precioDouble), duracionMinutos, activo);
    }

    public int getIdServicio() {
        return idServicio; 
    }
    public void setIdServicio(int idServicio) { 
        this.idServicio = idServicio; 
    }

    // Métodos de compatibilidad corta (por si otras vistas llaman getId o getNombre)
    public int getId() { return idServicio; }
    public void setId(int id) { this.idServicio = id; }

    public String getNombreServicio() { 
        return nombreServicio; 
    }
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio; 
    }

    public String getNombre() { return nombreServicio; }
    public void setNombre(String nombre) { this.nombreServicio = nombre; }

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
        this.precio = precio; 
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
        return nombreServicio + " - $" + precio;
    }
}