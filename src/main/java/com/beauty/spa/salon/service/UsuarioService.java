package main.java.com.beauty.spa.salon.service;

import main.java.com.beauty.spa.salon.model.Usuario;
import main.java.com.beauty.spa.salon.repository.UsuarioRepository;
import main.java.com.beauty.spa.salon.security.jbcrypt.BCrypt;
import java.util.List;

public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();

    public boolean registrarUsuario(String nombre, String apellido, String nombreUsuario, String correo, String contrasenaPlana, int idRol) {
        if (camposInvalidos(nombre, apellido, nombreUsuario, correo, contrasenaPlana)) {
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

    public boolean actualizarUsuario(int idUsuario, String nombre, String apellido, String nombreUsuario, String correo, String contrasenaPlana, String contrasenaActualBD, int idRol) {
        if (idUsuario <= 0 || camposInvalidos(nombre, apellido, nombreUsuario, correo, "validado")) {
            return false;
        }

        String contrasenaFinal;
        
        if (contrasenaPlana != null && !contrasenaPlana.trim().isEmpty()) {
            contrasenaFinal = BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt());
        } else {
            contrasenaFinal = contrasenaActualBD;
        }

        Usuario usuario = new Usuario(idUsuario, nombre, apellido, nombreUsuario, correo, contrasenaFinal, idRol);
        return usuarioRepository.actualizar(usuario);
    }

    public boolean eliminarUsuario(int idUsuario) {
        if (idUsuario <= 0) return false;
        return usuarioRepository.eliminar(idUsuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.listarTodos();
    }

    private boolean camposInvalidos(String nombre, String apellido, String nombreUsuario, String correo, String contrasena) {
        return nombre == null || nombre.trim().isEmpty() ||
               apellido == null || apellido.trim().isEmpty() ||
               nombreUsuario == null || nombreUsuario.trim().isEmpty() ||
               correo == null || correo.trim().isEmpty() ||
               contrasena == null || contrasena.trim().isEmpty();
    }
}