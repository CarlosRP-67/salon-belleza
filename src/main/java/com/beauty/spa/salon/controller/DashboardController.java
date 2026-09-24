package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private AnchorPane contentArea;

    private SceneManager sceneManager;

    private SceneManager getSceneManager() {
        if (sceneManager == null) {
            Stage stage = (Stage) contentArea.getScene().getWindow();
            sceneManager = new SceneManager(stage);
        }
        return sceneManager;
    }

    @FXML
    private void cargarUsuarios(ActionEvent event) {
        getSceneManager().cargarVistaEnCentro(contentArea, "src/main/resources/view/usuario-view.fxml", null);
    }

    @FXML
    private void cargarClientes(ActionEvent event) {
        getSceneManager().cargarVistaEnCentro(contentArea, "src/main/resources/view/cliente-view.fxml", null);
    }

    @FXML
    private void cargarEmpleados(ActionEvent event) {
        getSceneManager().cargarVistaEnCentro(contentArea, "src/main/resources/view/empleado-view.fxml", null);
    }

    @FXML
    private void cargarProductos(ActionEvent event) {
        getSceneManager().cargarVistaEnCentro(contentArea, "src/main/resources/view/venta-productos-view.fxml", "src/main/resources/css/venta-produtos-view.css");
    }

    @FXML
    private void cargarServicios(ActionEvent event) {
        getSceneManager().cargarVistaEnCentro(contentArea, "src/main/resources/view/servicio-view.fxml", "src/main/resources/css/servicio-view.css");
    }

    @FXML
    private void cargarFacturas(ActionEvent event) {
        getSceneManager().cargarVistaEnCentro(contentArea, "src/main/resources/view/factura-view.fxml", null);
    }

    @FXML
    private void cargarCitas(ActionEvent event) {
        getSceneManager().cargarVistaEnCentro(contentArea, "src/main/resources/view/usuarios-citas-view.fxml", "src/main/resources/css/usuarios-citas-view.css");
    }
    
    @FXML
    private void regresarLogin(ActionEvent event) {
        getSceneManager().showLoginView();
    }
    
}