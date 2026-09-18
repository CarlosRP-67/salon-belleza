/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.beauty.spa.salon.service;

import main.java.com.beauty.spa.salon.model.Usuario;
import main.java.com.beauty.spa.salon.repository.UsuarioRepository;
import main.java.com.beauty.spa.salon.security.jbcrypt.BCrypt;
/**
 *
 * @author informatica
 */
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public boolean registrarUsuario(String nombre, String apellido, String nombreUsuario, String correo, String contrasenaPlana, int idRol) {
        
        if (nombre == null || nombre.isEmpty() || 
            apellido == null || apellido.isEmpty() || 
            nombreUsuario == null || nombreUsuario.isEmpty() || 
            correo == null || correo.isEmpty() || 
            contrasenaPlana == null || contrasenaPlana.isEmpty()) {
            return false;
        }

        String contrasenaEncriptada = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasenaEncriptada);
        usuario.setIdRol(idRol);

        return usuarioRepository.registrar(usuario);
    }
}