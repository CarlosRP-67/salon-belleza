package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.Empleado;
import main.java.com.beauty.spa.salon.repository.EmpleadoRepository;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class EmpleadoController implements Initializable {

    @FXML private TableView<Empleado> tblEmpleados;
    @FXML private TableColumn<Empleado, Integer> colIdEmpleado;
    @FXML private TableColumn<Empleado, Integer> colIdUsuario;
    @FXML private TableColumn<Empleado, String> colNombre;
    @FXML private TableColumn<Empleado, String> colEspecialidad;
    @FXML private TableColumn<Empleado, String> colTelefono;
    @FXML private TableColumn<Empleado, String> colHorario;

    @FXML private TextField txtIdUsuario;
    @FXML private TextField txtEspecialidad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtHorario;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private ObservableList<Empleado> listaEmpleados;
    private Empleado empleadoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDatos();

        tblEmpleados.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionarElemento(newValue)
        );
    }

    private void configurarColumnas() {
        colIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioTrabajo"));
    }

    public void cargarDatos() {
        listaEmpleados = FXCollections.observableArrayList(empleadoRepository.listarTodos());
        tblEmpleados.setItems(listaEmpleados);
    }

    @FXML
    public void guardarEmpleado() {
        if (!validarCampos()) return;

        try {
            int idUsuario = Integer.parseInt(txtIdUsuario.getText());
            Empleado nuevo = new Empleado(idUsuario, txtEspecialidad.getText(), txtTelefono.getText(), txtHorario.getText());

            if (empleadoRepository.guardar(nuevo)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado registrado correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el empleado.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "El ID de usuario debe ser un número entero.");
        }
    }

    @FXML
    public void actualizarEmpleado() {
        if (empleadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un empleado de la tabla para actualizar.");
            return;
        }

        if (!validarCampos()) return;

        empleadoSeleccionado.setEspecialidad(txtEspecialidad.getText());
        empleadoSeleccionado.setTelefono(txtTelefono.getText());
        empleadoSeleccionado.setHorarioTrabajo(txtHorario.getText());

        if (empleadoRepository.actualizar(empleadoSeleccionado)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el empleado.");
        }
    }

    @FXML
    public void eliminarEmpleado() {
        if (empleadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un empleado de la tabla para eliminar.");
            return;
        }

        if (empleadoRepository.eliminar(empleadoSeleccionado.getIdEmpleado())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el empleado.");
        }
    }

    @FXML
    public void limpiarCampos() {
        txtIdUsuario.clear();
        txtEspecialidad.clear();
        txtTelefono.clear();
        txtHorario.clear();
        tblEmpleados.getSelectionModel().clearSelection();
        empleadoSeleccionado = null;
        txtIdUsuario.setDisable(false);
    }

    private void seleccionarElemento(Empleado empleado) {
        if (empleado != null) {
            empleadoSeleccionado = empleado;
            txtIdUsuario.setText(String.valueOf(empleado.getIdUsuario()));
            txtEspecialidad.setText(empleado.getEspecialidad());
            txtTelefono.setText(empleado.getTelefono());
            txtHorario.setText(empleado.getHorarioTrabajo());
            txtIdUsuario.setDisable(true);
        }
    }

    private boolean validarCampos() {
        if (txtIdUsuario.getText().isEmpty() || txtEspecialidad.getText().isEmpty() ||
            txtTelefono.getText().isEmpty() || txtHorario.getText().isEmpty()) {
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