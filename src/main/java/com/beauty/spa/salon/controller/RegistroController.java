
package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.service.UsuarioService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class RegistroController implements Initializable {

    // Campos vinculados a la vista FXML (Asegúrate de que tengan estos fx:id en Scene Builder)
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;

    private final UsuarioService usuarioService = new UsuarioService();

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    // Método que se ejecutará al hacer clic en el botón de registrar en tu vista
    @FXML
    private void handleRegistrar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        int rolCliente = 2;

        // Llamamos al servicio para validar y registrar
        boolean registrado = usuarioService.registrarUsuario(nombre, apellido, nombreUsuario, correo, contrasena, rolCliente);

        if (registrado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Usuario registrado correctamente en la base de datos!");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Registro", "No se pudo registrar. Verifique que los campos no estén vacíos o que el correo/usuario no existan ya.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtNombreUsuario.clear();
        txtCorreo.clear();
        txtContrasena.clear();
    }
}