package main.java.com.beauty.spa.salon.controller;

<<<<<<< HEAD
import main.java.com.beauty.spa.salon.repository.AdministradorRepository;
import main.java.com.beauty.spa.salon.model.Administrador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Controlador de login.fxml. Valida al administrador contra la base
 * de datos antes de dejarlo pasar a la pantalla de servicios.
=======
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.com.beauty.spa.salon.util.SceneManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Controlador para la vista de inicio de sesión de Beauty Spa.
 * @author informatica
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
 */
public class LoginController {

    @FXML
<<<<<<< HEAD
    private TextField txtUsuario;
=======
    private TextField txtEmail;
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f

    @FXML
    private PasswordField txtPassword;

<<<<<<< HEAD
    private final AdministradorRepository administradorDAO = new AdministradorRepository();

    @FXML
    private void ingresar(ActionEvent event) {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if (usuario == null || usuario.isBlank() || password == null || password.isBlank()) {
            mostrarAlerta(AlertType.WARNING, "Debes escribir usuario y contraseña.");
            return;
        }

        try {
            Administrador admin = administradorDAO.autenticar(usuario.trim(), password);
            if (admin == null) {
                mostrarAlerta(AlertType.ERROR, "Usuario o contraseña incorrectos.");
                return;
            }

            FXMLLoader loader = SceneNavigator.cargar("/main/resources/fxml/servicios.fxml");
            ServiciosController controller = loader.getController();
            controller.setAdministrador(admin);
            SceneNavigator.mostrar(obtenerStage(), loader);
        } catch (SQLException ex) {
            mostrarAlerta(AlertType.ERROR, "Error de conexión con la base de datos:\n" + ex.getMessage());
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        SceneNavigator.cambiarA(obtenerStage(), "/main/resources/fxml/main_menu.fxml");
    }

    private Stage obtenerStage() {
        return (Stage) txtUsuario.getScene().getWindow();
    }

    private void mostrarAlerta(AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje).showAndWait();
    }
}
=======
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnGoToRegister;

    @FXML
    private ImageView imgSpaBanner;

    /**
     * Método de inicialización que se ejecuta automáticamente al cargar la vista.
     */
    @FXML
    public void initialize() {
        try {
            // Intentamos primero por recursos por si acaso
            InputStream imageStream = getClass().getResourceAsStream("/images/spa-instalaciones.jpg");
            Image spaImage = null;

            if (imageStream != null) {
                spaImage = new Image(imageStream);
            } else {
                // Si el classpath falla, cargamos directamente desde la ruta física absoluta de tu proyecto
                File file = new File("src/main/resources/images/spa-instalaciones.jpg");
                if (file.exists()) {
                    spaImage = new Image(file.toURI().toString());
                }
            }

            if (spaImage != null && imgSpaBanner != null) {
                imgSpaBanner.setImage(spaImage);
            } else {
                System.out.println("Aviso: No se pudo localizar el archivo de imagen en la ruta física.");
            }
        } catch (Exception e) {
            System.out.println("Aviso: No se pudo cargar la imagen del banner automáticamente: " + e.getMessage());
        }
    }

    /**
     * Acción del botón para iniciar sesión en el sistema.
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        // Validación de campos vacíos con alerta modal gráfica
        if (email.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor, complete todos los campos para continuar.");
            return;
        }

        // Lógica de inicio de sesión y transición al Dashboard
        try {
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            SceneManager sceneManager = new SceneManager(currentStage);
            sceneManager.showMainDashboard();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Sistema", "No se pudo abrir el menú principal: " + e.getMessage());
        }
    }

    /**
     * Acción del botón para redirigir a la vista de registro de usuario.
     */
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

    /**
     * Método auxiliar para mostrar alertas modales en la interfaz gráfica.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
