package main.java.com.beauty.spa.salon.model;

/**
 * Representa un servicio del salón (por ejemplo: corte de cabello,
 * manicure, tinte, etc.). Solo un administrador puede crearlos.
 */
public class Servicio {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;
    private boolean activo;

    public Servicio() {
        this.activo = true;
    }

    public Servicio(String nombre, String descripcion, double precio, int duracionMinutos) {
        this(0, nombre, descripcion, precio, duracionMinutos, true);
    }

    public Servicio(int id, String nombre, String descripcion, double precio,
            int duracionMinutos, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
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
        return nombre + " - $" + precio;
    }
}
