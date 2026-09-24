package main.java.com.beauty.spa.salon.model;

import java.math.BigDecimal;

public class Servicio {

    private int idServicio;
    private String nombreServicio;
    private String descripcion;
    private BigDecimal precio;
    private int duracionMinutos;

    public Servicio() {
    }

    public Servicio(int idServicio, String nombreServicio, String descripcion,
                    BigDecimal precio, int duracionMinutos) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
    }

    public int getIdServicio() {
        return idServicio; 
    }
    public void setIdServicio(int idServicio) { 
        this.idServicio = idServicio; 
    }

    public String getNombreServicio() { 
        return nombreServicio; 
    }
    
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio; 
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
        this.precio = precio; 
    }

    public int getDuracionMinutos() { 
        return duracionMinutos;
    }
    
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos; 
    }
}