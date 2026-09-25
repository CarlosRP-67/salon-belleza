package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.util.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private AnchorPane contentArea;

    @FXML private Button btnUsuarios;
    @FXML private Button btnClientes;
    @FXML private Button btnEmpleados;
    @FXML private Button btnProductos;
    @FXML private Button btnServicios;
    @FXML private Button btnFacturas;
    @FXML private Button btnCitas;
    @FXML private Button btnCerrarSesion;

    private SceneManager sceneManager;

    @FXML
    public void initialize() {
        System.out.println("--- DIAGNOSTICO DE BOTONES ---");
        System.out.println("btnUsuarios es nulo?: " + (btnUsuarios == null));
        System.out.println("btnClientes es nulo?: " + (btnClientes == null));
        System.out.println("btnEmpleados es nulo?: " + (btnEmpleados == null));
        System.out.println("btnFacturas es nulo?: " + (btnFacturas == null));
    }


    public void setRolUsuario(String rol) {
        System.out.println(">>> ROL RECIBIDO EN EL DASHBOARD: [" + rol + "]");
        
        if (rol != null && (rol.equals("2") || rol.toLowerCase().contains("cliente"))) {
            System.out.println(">>> Aplicando restricciones de Cliente (Ocultando administración)");
            ocultarBoton(btnUsuarios);
            ocultarBoton(btnClientes);
            ocultarBoton(btnEmpleados);
            ocultarBoton(btnFacturas);
        } else {
            System.out.println(">>> Rol de Administrador detectado, mostrando todo.");
        }
    }

    private void ocultarBoton(Button boton) {
        if (boton != null) {
            boton.setVisible(false);
            boton.setManaged(false);
        }
    }

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