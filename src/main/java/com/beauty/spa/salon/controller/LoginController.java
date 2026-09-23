package main.java.com.beauty.spa.salon.controller;

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
 */
public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

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
