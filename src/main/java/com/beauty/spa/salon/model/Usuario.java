package main.java.com.beauty.spa.salon.model;

/**
 * Representa a un cliente que puede comprar servicios del salón.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String telefono;

    public Usuario() {
    }

    public Usuario(String nombre, String email, String telefono) {
        this(0, nombre, email, telefono);
    }

    public Usuario(int id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        // Así se ve bonito dentro de un ComboBox de JavaFX
        return nombre + " (" + email + ")";
    }
}
