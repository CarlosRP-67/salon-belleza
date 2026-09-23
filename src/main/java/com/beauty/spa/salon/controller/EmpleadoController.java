package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.Empleado;
import main.java.com.beauty.spa.salon.model.Usuario;
import main.java.com.beauty.spa.salon.repository.EmpleadoRepository;
import main.java.com.beauty.spa.salon.repository.UsuarioRepository;

import java.net.URL;
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

    @FXML private ComboBox<Usuario> cmbUsuario;
    @FXML private TextField txtEspecialidad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtHorario;
    @FXML private TextField txtBuscar;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final EmpleadoRepository empleadoRepository = new EmpleadoRepository();
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    
    private ObservableList<Empleado> listaEmpleados;
    private ObservableList<Usuario> listaUsuarios;
    private FilteredList<Empleado> filteredData;
    private Empleado empleadoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarUsuarios();
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

    private void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(usuarioRepository.listarTodos());
        cmbUsuario.setItems(listaUsuarios);
    }

    public void cargarDatos() {
        listaEmpleados = FXCollections.observableArrayList(empleadoRepository.listarTodos());
        configurarBuscador();
    }

    private void configurarBuscador() {
        filteredData = new FilteredList<>(listaEmpleados, p -> true);

        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(empleado -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (empleado.getNombreCompleto() != null && empleado.getNombreCompleto().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (empleado.getEspecialidad() != null && empleado.getEspecialidad().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (empleado.getTelefono() != null && empleado.getTelefono().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
        }

        SortedList<Empleado> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblEmpleados.comparatorProperty());
        tblEmpleados.setItems(sortedData);
    }

    @FXML
    public void guardarEmpleado() {
        if (!validarCampos()) return;

        Usuario usuarioSeleccionado = cmbUsuario.getValue();
        Empleado nuevo = new Empleado(
            usuarioSeleccionado.getIdUsuario(), 
            txtEspecialidad.getText(), 
            txtTelefono.getText(), 
            txtHorario.getText()
        );

        if (empleadoRepository.guardar(nuevo)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado registrado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el empleado.");
        }
    }

    @FXML
    public void actualizarEmpleado() {
        if (empleadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un empleado de la tabla para actualizar.");
            return;
        }

        if (!validarCampos()) return;

        Usuario usuarioSeleccionado = cmbUsuario.getValue();
        if (usuarioSeleccionado != null) {
            empleadoSeleccionado.setIdUsuario(usuarioSeleccionado.getIdUsuario());
        }
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
        cmbUsuario.getSelectionModel().clearSelection();
        txtEspecialidad.clear();
        txtTelefono.clear();
        txtHorario.clear();
        if (txtBuscar != null) txtBuscar.clear();
        tblEmpleados.getSelectionModel().clearSelection();
        empleadoSeleccionado = null;
        cmbUsuario.setDisable(false);
    }

    private void seleccionarElemento(Empleado empleado) {
        if (empleado != null) {
            empleadoSeleccionado = empleado;
            
            if (listaUsuarios != null) {
                for (Usuario u : listaUsuarios) {
                    if (u.getIdUsuario() == empleado.getIdUsuario()) {
                        cmbUsuario.setValue(u);
                        break;
                    }
                }
            }
            
            txtEspecialidad.setText(empleado.getEspecialidad());
            txtTelefono.setText(empleado.getTelefono());
            txtHorario.setText(empleado.getHorarioTrabajo());
            cmbUsuario.setDisable(true);
        }
    }

    private boolean validarCampos() {
        if (cmbUsuario.getValue() == null || txtEspecialidad.getText().isEmpty() ||
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