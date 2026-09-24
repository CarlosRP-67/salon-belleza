package main.java.com.beauty.spa.salon.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controlador de main_menu.fxml.
 */
public class MainMenuController {

    @FXML
    private Button btnServicios;

    @FXML
    private void irAServicios(ActionEvent event) {
        SceneNavigator.cambiarA(obtenerStage(), "/main/resources/fxml/login.fxml");
    }

    @FXML
    private void irAFactura(ActionEvent event) {
        SceneNavigator.cambiarA(obtenerStage(), "/main/resources/fxml/factura.fxml");
    }

    private Stage obtenerStage() {
        return (Stage) btnServicios.getScene().getWindow();
    }
}
