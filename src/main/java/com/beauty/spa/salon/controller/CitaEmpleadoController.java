package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.CitaEmpleado;
import main.java.com.beauty.spa.salon.service.CitaService;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CitaEmpleadoController implements Initializable {

    @FXML private TableView<CitaEmpleado> tblCitas;
    @FXML private TableColumn<CitaEmpleado, Integer> colIdCita;
    @FXML private TableColumn<CitaEmpleado, Integer> colIdCliente;
    @FXML private TableColumn<CitaEmpleado, Integer> colIdEmpleado;
    @FXML private TableColumn<CitaEmpleado, Integer> colIdServicio;
    @FXML private TableColumn<CitaEmpleado, String> colFechaHora;
    @FXML private TableColumn<CitaEmpleado, String> colEstado;

    @FXML private TextField txtIdCliente;
    @FXML private TextField txtIdEmpleado;
    @FXML private TextField txtIdServicio;
    @FXML private DatePicker dpFecha;
    @FXML private TextField txtHora;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private TextField txtBuscar;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final CitaService citaService = new CitaService();
    private ObservableList<CitaEmpleado> listaCitas;
    private FilteredList<CitaEmpleado> filteredData;
    private CitaEmpleado citaSeleccionada;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarEstados();
        cargarDatos();

        tblCitas.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionarElemento(newValue)
        );
    }

    private void configurarColumnas() {
        colIdCita.setCellValueFactory(new PropertyValueFactory<>("idCita"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colIdServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        colFechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    private void cargarEstados() {
        cmbEstado.setItems(FXCollections.observableArrayList("PENDIENTE", "CONFIRMADA", "COMPLETADA", "CANCELADA"));
    }

    public void cargarDatos() {
        listaCitas = FXCollections.observableArrayList(citaService.obtenerTodasLasCitas());
        configurarBuscador();
    }

    private void configurarBuscador() {
        filteredData = new FilteredList<>(listaCitas, p -> true);

        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(c -> {
                    if (newValue == null || newValue.isEmpty()) return true;
                    String filter = newValue.toLowerCase();
                    return c.getEstado().toLowerCase().contains(filter) ||
                           String.valueOf(c.getIdCita()).contains(filter);
                });
            });
        }

        SortedList<CitaEmpleado> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblCitas.comparatorProperty());
        tblCitas.setItems(sortedData);
    }

    @FXML
    public void guardarCita() {
        if (!validarCampos()) return;

        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText());
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            int idServicio = Integer.parseInt(txtIdServicio.getText());
            String fechaHora = dpFecha.getValue().toString() + " " + txtHora.getText();
            String estado = cmbEstado.getValue();

            if (citaService.registrarCita(idCliente, idEmpleado, idServicio, fechaHora, estado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cita agendada correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agendar la cita.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los IDs deben ser números enteros.");
        }
    }

    @FXML
    public void actualizarCita() {
        if (citaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona una cita de la tabla.");
            return;
        }

        if (!validarCampos()) return;

        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText());
            int idEmpleado = Integer.parseInt(txtIdEmpleado.getText());
            int idServicio = Integer.parseInt(txtIdServicio.getText());
            String fechaHora = dpFecha.getValue().toString() + " " + txtHora.getText();
            String estado = cmbEstado.getValue();

            if (citaService.actualizarCita(citaSeleccionada.getIdCita(), idCliente, idEmpleado, idServicio, fechaHora, estado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cita actualizada correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar la cita.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Los IDs deben ser números enteros.");
        }
    }

    @FXML
    public void eliminarCita() {
        if (citaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona una cita de la tabla.");
            return;
        }

        if (citaService.eliminarCita(citaSeleccionada.getIdCita())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cita eliminada correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la cita.");
        }
    }

    @FXML
    public void limpiarCampos() {
        txtIdCliente.clear();
        txtIdEmpleado.clear();
        txtIdServicio.clear();
        dpFecha.setValue(null);
        txtHora.clear();
        cmbEstado.getSelectionModel().clearSelection();
        if (txtBuscar != null) txtBuscar.clear();
        tblCitas.getSelectionModel().clearSelection();
        citaSeleccionada = null;
    }

    private void seleccionarElemento(CitaEmpleado cita) {
        if (cita != null) {
            citaSeleccionada = cita;
            txtIdCliente.setText(String.valueOf(cita.getIdCliente()));
            txtIdEmpleado.setText(String.valueOf(cita.getIdEmpleado()));
            txtIdServicio.setText(String.valueOf(cita.getIdServicio()));
            
            if (cita.getFechaHora() != null && cita.getFechaHora().contains(" ")) {
                String[] partes = cita.getFechaHora().split(" ");
                try {
                    dpFecha.setValue(LocalDate.parse(partes[0]));
                    txtHora.setText(partes[1]);
                } catch (Exception e) {
                    txtHora.setText(cita.getFechaHora());
                }
            }
            cmbEstado.getSelectionModel().select(cita.getEstado());
        }
    }

    private boolean validarCampos() {
        if (txtIdCliente.getText().isEmpty() || txtIdEmpleado.getText().isEmpty() ||
            txtIdServicio.getText().isEmpty() || dpFecha.getValue() == null ||
            txtHora.getText().isEmpty() || cmbEstado.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos.");
            return false;
        }
        return true;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}