<<<<<<< HEAD
package main.java.com.beauty.spa.salon.model;

/**
 * Representa a un cliente que puede comprar servicios del salón.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String telefono;
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.beauty.spa.salon.model;

/**
 *
 * @author informatica
 */
public class Usuario {
    
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String nombreUsuario;
    private String correo;
    private String contrasena;
    private int idRol;
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f

    public Usuario() {
    }

<<<<<<< HEAD
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
=======
    public Usuario(String nombre, String apellido, String nombreUsuario, String correo, String contrasena, int idRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }

    public Usuario(int idUsuario, String nombre, String apellido, String nombreUsuario, String correo, String contrasena, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.idRol = idRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

<<<<<<< HEAD
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
=======
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        // Así se ve bonito dentro de un ComboBox de JavaFX
        return nombre + " (" + email + ")";
    }
}
=======
        return nombre + " " + apellido;
    }
}
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
