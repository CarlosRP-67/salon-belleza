package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.util.SceneManager;
import main.java.com.beauty.spa.salon.dto.request.LoginDTORequest;
import main.java.com.beauty.spa.salon.dto.response.LoginDTOResponse;
import main.java.com.beauty.spa.salon.repository.AuthRepository;
import main.java.com.beauty.spa.salon.service.AuthService;

import java.io.File;
import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnGoToRegister;

    @FXML
    private ImageView imgSpaBanner;

    private final AuthService authService = new AuthService(new AuthRepository());

    @FXML
    public void initialize() {
        try {
            InputStream imageStream = getClass().getResourceAsStream("/images/spa-instalaciones.jpg");
            Image spaImage = null;

            if (imageStream != null) {
                spaImage = new Image(imageStream);
            } else {
                File file = new File("src/main/resources/images/spa-instalaciones.jpg");
                if (file.exists()) {
                    spaImage = new Image(file.toURI().toString());
                }
            }

            if (spaImage != null && imgSpaBanner != null) {
                imgSpaBanner.setImage(spaImage);
            }
        } catch (Exception e) {
            System.out.println("Aviso: No se pudo cargar la imagen del banner: " + e.getMessage());
        }
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor, complete todos los campos para continuar.");
            return;
        }

        try {
            LoginDTORequest request = new LoginDTORequest(email, password);
            
            LoginDTOResponse response = authService.login(request);

            if (response == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Acceso Denegado", "Correo o contraseña incorrectos.");
                return;
            }

            String rolUsuario = response.getNombreRol();
            System.out.println(">>> Login exitoso para: " + response.getNombre() + " | Rol obtenido de BD: [" + rolUsuario + "]");

            SceneManager.setRolUsuarioActual(rolUsuario);

            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            SceneManager sceneManager = new SceneManager(currentStage);
            sceneManager.showMainDashboard();
            
        } catch (RuntimeException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Autenticación", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Sistema", "Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        try {
            Stage currentStage = (Stage) btnGoToRegister.getScene().getWindow();
            SceneManager sceneManager = new SceneManager(currentStage);
            sceneManager.cargarVistaFisica("src/main/resources/view/registro-view.fxml", null, "Beauty Spa - Registro de Usuario");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Navegación", "No se pudo abrir la vista de registro: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}