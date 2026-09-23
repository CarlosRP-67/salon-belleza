package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.Factura;
import main.java.com.beauty.spa.salon.service.FacturaService;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FacturaController implements Initializable {

    @FXML private TextField txtIdCliente;
    @FXML private DatePicker dpFechaFactura;
    @FXML private TextField txtTotal;
    @FXML private Button btnGenerar;
    @FXML private Button btnLimpiar;

    @FXML private TableView<Factura> tblFacturas;
    @FXML private TableColumn<Factura, Integer> colIdFactura;
    @FXML private TableColumn<Factura, Integer> colIdCliente;
    @FXML private TableColumn<Factura, String> colFechaFactura;
    @FXML private TableColumn<Factura, Double> colTotal;

    private final FacturaService facturaService = new FacturaService();
    private ObservableList<Factura> listaFacturas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDatos();
    }

    private void configurarColumnas() {
        colIdFactura.setCellValueFactory(new PropertyValueFactory<>("idFactura"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colFechaFactura.setCellValueFactory(new PropertyValueFactory<>("fechaFactura"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void cargarDatos() {
        listaFacturas = FXCollections.observableArrayList(facturaService.obtenerTodasLasFacturas());
        tblFacturas.setItems(listaFacturas);
    }

    @FXML
    public void guardarFactura() {
        if (txtIdCliente.getText().isEmpty() || dpFechaFactura.getValue() == null || txtTotal.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos.");
            return;
        }

        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText());
            String fecha = dpFechaFactura.getValue().toString();
            double total = Double.parseDouble(txtTotal.getText());

            if (facturaService.registrarFactura(idCliente, fecha, total)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Factura registrada correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar la factura.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ingresa formatos numéricos válidos.");
        }
    }

    @FXML
    public void limpiarCampos() {
        if (txtIdCliente != null) txtIdCliente.clear();
        if (dpFechaFactura != null) dpFechaFactura.setValue(null);
        if (txtTotal != null) txtTotal.clear();
        if (tblFacturas != null) tblFacturas.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}