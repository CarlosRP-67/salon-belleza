package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.service.UsuarioService;
import main.java.com.beauty.spa.salon.util.SceneManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegistroController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private TextField txtContrasenaVisible;
    @FXML private Button btnTogglePassword;
    @FXML private ComboBox<String> cmbRol;
    @FXML private Button btnRegresarLogin;
    @FXML private ImageView imgBanner;

    private boolean mostrandoPassword = false;
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Carga de la imagen del banner lateral
        try {
            Image image = new Image(getClass().getResourceAsStream("/images/salon-registro.png"));
            if (image != null && imgBanner != null) {
                imgBanner.setImage(image);
            }
        } catch (Exception e) {
            try {
                Image image2 = new Image(getClass().getResourceAsStream("/main/resources/images/salon-registro.png"));
                if (image2 != null && imgBanner != null) {
                    imgBanner.setImage(image2);
                }
            } catch (Exception ex) {
                System.out.println("No se pudo cargar la imagen del banner: " + ex.getMessage());
            }
        }

        // Llenado del ComboBox de roles
        cmbRol.setItems(FXCollections.observableArrayList("1 - Administrador", "2 - Cliente"));
    }    

    @FXML
    private void handleTogglePassword(ActionEvent event) {
        mostrandoPassword = !mostrandoPassword;
        
        if (mostrandoPassword) {
            txtContrasenaVisible.setText(txtContrasena.getText());
            txtContrasenaVisible.setVisible(true);
            txtContrasenaVisible.setManaged(true);
            
            txtContrasena.setVisible(false);
            txtContrasena.setManaged(false);
            
            btnTogglePassword.setText("🔒");
        } else {
            txtContrasena.setText(txtContrasenaVisible.getText());
            txtContrasena.setVisible(true);
            txtContrasena.setManaged(true);
            
            txtContrasenaVisible.setVisible(false);
            txtContrasenaVisible.setManaged(false);
            
            btnTogglePassword.setText("👁");
        }
    }

    @FXML
    private void handleRegistrar() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String nombreUsuario = txtNombreUsuario.getText().trim();
        String correo = txtCorreo.getText().trim();
        
        String contrasena = mostrandoPassword ? txtContrasenaVisible.getText().trim() : txtContrasena.getText().trim();

        String rolSeleccionado = cmbRol.getValue();
        if (rolSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campo Requerido", "Por favor seleccione un rol para el usuario.");
            return;
        }

        int idRol = Character.getNumericValue(rolSeleccionado.charAt(0));
        boolean registrado = usuarioService.registrarUsuario(nombre, apellido, nombreUsuario, correo, contrasena, idRol);

        if (registrado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "¡Usuario registrado correctamente con rol " + idRol + "!");
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Registro", "No se pudo registrar. Verifique los campos o si el correo/usuario ya existen.");
        }
    }

    @FXML
    private void handleRegresarLogin(ActionEvent event) {
        try {
            Stage stageActual = (Stage) btnRegresarLogin.getScene().getWindow();
            SceneManager sceneManager = new SceneManager(stageActual);
            sceneManager.showLoginView();
        } catch (Exception e) {
            System.err.println("Error al regresar al login: " + e.getMessage());
            e.printStackTrace();
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
        txtContrasenaVisible.clear();
        cmbRol.getSelectionModel().clearSelection();
        if (mostrandoPassword) {
            handleTogglePassword(null);
        }
    }
}