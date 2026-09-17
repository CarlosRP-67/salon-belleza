/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.beauty.spa.salon.dto.response;

/**
 *
 * @author informatica
 */
public class LoginDTOResponse {
    private String nombre;
    private String apellido;
    private String contrasena;
    private String nombreRol;
    
    //sobrecarga de metodos
    public LoginDTOResponse(){
    }

    public LoginDTOResponse(String nombre, String apellido, String contrasena, String nombreRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.nombreRol = nombreRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    
    
}
