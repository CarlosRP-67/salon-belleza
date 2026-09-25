package main.java.com.beauty.spa.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.beauty.spa.salon.model.Empleado;
import main.java.com.beauty.spa.salon.model.Usuario;
import main.java.com.beauty.spa.salon.repository.EmpleadoRepository;
import main.java.com.beauty.spa.salon.repository.UsuarioRepository;

import java.net.URL;
import java.util.ResourceBundle;

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
    private Empleado empleadoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarUsuarios();
        cargarDatos();

        if (tblEmpleados != null) {
            tblEmpleados.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarElemento(newValue)
            );
        }
    }

    private void configurarColumnas() {
        if (colIdEmpleado != null) colIdEmpleado.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        if (colIdUsuario != null) colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        if (colNombre != null) colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        if (colEspecialidad != null) colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));
        if (colTelefono != null) colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        if (colHorario != null) colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioTrabajo"));
    }

    private void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(usuarioRepository.listarTodos());
        if (cmbUsuario != null) {
            cmbUsuario.setItems(listaUsuarios);
        }
    }

    public void cargarDatos() {
        listaEmpleados = FXCollections.observableArrayList(empleadoRepository.listarTodos());
        if (tblEmpleados != null) {
            tblEmpleados.setItems(listaEmpleados);
        }
    }

    @FXML
    public void guardarEmpleado(ActionEvent event) {
        if (!validarCampos()) return;

        Usuario usuarioSeleccionado = cmbUsuario.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Seleccione un usuario válido.");
            return;
        }

        for (Empleado emp : listaEmpleados) {
            if (emp.getIdUsuario() == usuarioSeleccionado.getIdUsuario()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Usuario duplicado", "Este usuario ya tiene un empleado registrado.");
                return;
            }
        }

        try {
            Empleado nuevo = new Empleado(
                0, 
                usuarioSeleccionado.getIdUsuario(), 
                txtEspecialidad.getText(), 
                txtTelefono.getText(), 
                txtHorario.getText(), 
                "", "", "", ""
            );

            if (empleadoRepository.guardar(nuevo)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado registrado correctamente.");
                limpiarCampos(null);
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el empleado.");
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al guardar.");
        }
    }

    @FXML
    public void actualizarEmpleado(ActionEvent event) {
        if (empleadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un empleado de la tabla para actualizar.");
            return;
        }
        if (!validarCampos()) return;

        try {
            empleadoSeleccionado.setEspecialidad(txtEspecialidad.getText());
            empleadoSeleccionado.setTelefono(txtTelefono.getText());
            empleadoSeleccionado.setHorarioTrabajo(txtHorario.getText());

            if (empleadoRepository.actualizar(empleadoSeleccionado)) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado actualizado correctamente.");
                limpiarCampos(null);
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el empleado.");
            }
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al actualizar.");
        }
    }

    @FXML
    public void eliminarEmpleado(ActionEvent event) {
        if (empleadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un empleado de la tabla para eliminar.");
            return;
        }

        Alert alertaConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirm.setTitle("Confirmar eliminación");
        alertaConfirm.setHeaderText(null);
        alertaConfirm.setContentText("¿Estás seguro de que quieres eliminar este empleado?");

        if (alertaConfirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            if (empleadoRepository.eliminar(empleadoSeleccionado.getIdEmpleado())) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Empleado eliminado correctamente.");
                limpiarCampos(null);
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el empleado.");
            }
        }
    }

    @FXML
    public void limpiarCampos(ActionEvent event) {
        if (cmbUsuario != null) {
            cmbUsuario.getSelectionModel().clearSelection();
            cmbUsuario.setDisable(false);
        }
        if (txtEspecialidad != null) txtEspecialidad.clear();
        if (txtTelefono != null) txtTelefono.clear();
        if (txtHorario != null) txtHorario.clear();
        if (txtBuscar != null) txtBuscar.clear();
        if (tblEmpleados != null) tblEmpleados.getSelectionModel().clearSelection();
        empleadoSeleccionado = null;
    }

    private void seleccionarElemento(Empleado empleado) {
        if (empleado != null) {
            empleadoSeleccionado = empleado;
            if (cmbUsuario != null) {
                for (Usuario u : cmbUsuario.getItems()) {
                    if (u.getIdUsuario() == empleado.getIdUsuario()) {
                        cmbUsuario.getSelectionModel().select(u);
                        break;
                    }
                }
                cmbUsuario.setDisable(true); 
            }
            if (txtEspecialidad != null) txtEspecialidad.setText(empleado.getEspecialidad());
            if (txtTelefono != null) txtTelefono.setText(empleado.getTelefono());
            if (txtHorario != null) txtHorario.setText(empleado.getHorarioTrabajo());
        }
    }

    private boolean validarCampos() {
        if (cmbUsuario == null || cmbUsuario.getSelectionModel().getSelectedItem() == null ||
            txtEspecialidad == null || txtEspecialidad.getText().isEmpty() ||
            txtTelefono == null || txtTelefono.getText().isEmpty() || 
            txtHorario == null || txtHorario.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos requeridos.");
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