package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.Cliente;
import main.java.com.beauty.spa.salon.model.Usuario;
import main.java.com.beauty.spa.salon.repository.ClienteRepository;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController implements Initializable {

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, Integer> colIdCliente;
    @FXML private TableColumn<Cliente, Integer> colIdUsuario;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colApellido;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<Cliente, String> colDireccion;

    @FXML private ComboBox<Usuario> cmbUsuario;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtBuscar;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private ObservableList<Cliente> listaClientes;
    private ObservableList<Usuario> listaUsuarios;
    private FilteredList<Cliente> filteredData;
    private Cliente clienteSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarUsuarios();
        cargarDatos();

        tblClientes.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionarElemento(newValue)
        );
    }

    private void configurarColumnas() {
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
    }

    private void cargarUsuarios() {
        listaUsuarios = FXCollections.observableArrayList(usuarioRepository.listarTodos());
        cmbUsuario.setItems(listaUsuarios);
    }

    public void cargarDatos() {
        listaClientes = FXCollections.observableArrayList(clienteRepository.listarTodos());
        configurarBuscador();
    }

    private void configurarBuscador() {
        filteredData = new FilteredList<>(listaClientes, p -> true);

        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(cliente -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (cliente.getNombre() != null && cliente.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cliente.getApellido() != null && cliente.getApellido().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cliente.getTelefono() != null && cliente.getTelefono().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (cliente.getDireccion() != null && cliente.getDireccion().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
        }

        SortedList<Cliente> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblClientes.comparatorProperty());
        tblClientes.setItems(sortedData);
    }

    @FXML
    public void guardarCliente() {
        if (!validarCampos()) return;

        Usuario usuarioSeleccionado = cmbUsuario.getSelectionModel().getSelectedItem();

        Cliente nuevo = new Cliente(
            usuarioSeleccionado.getIdUsuario(),
            txtTelefono.getText(),
            txtDireccion.getText()
        );

        if (clienteRepository.guardar(nuevo)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente registrado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el cliente.");
        }
    }

    @FXML
    public void actualizarCliente() {
        if (clienteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un cliente de la tabla para actualizar.");
            return;
        }

        if (!validarCampos()) return;

        Usuario usuarioSeleccionado = cmbUsuario.getSelectionModel().getSelectedItem();

        clienteSeleccionado.setIdUsuario(usuarioSeleccionado.getIdUsuario());
        clienteSeleccionado.setTelefono(txtTelefono.getText());
        clienteSeleccionado.setDireccion(txtDireccion.getText());

        if (clienteRepository.actualizar(clienteSeleccionado)) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el cliente.");
        }
    }

    @FXML
    public void eliminarCliente() {
        if (clienteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un cliente de la tabla para eliminar.");
            return;
        }

        Alert alertaConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertaConfirm.setTitle("Confirmar eliminación");
        alertaConfirm.setHeaderText(null);
        alertaConfirm.setContentText("¿Estás seguro de que quieres eliminar este cliente?");

        if (alertaConfirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            if (clienteRepository.eliminar(clienteSeleccionado.getIdCliente())) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente eliminado correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el cliente.");
            }
        }
    }

    @FXML
    public void limpiarCampos() {
        cmbUsuario.getSelectionModel().clearSelection();
        txtTelefono.clear();
        txtDireccion.clear();
        if (txtBuscar != null) txtBuscar.clear();
        tblClientes.getSelectionModel().clearSelection();
        clienteSeleccionado = null;
    }

    private void seleccionarElemento(Cliente cliente) {
        if (cliente != null) {
            clienteSeleccionado = cliente;
            txtTelefono.setText(cliente.getTelefono());
            txtDireccion.setText(cliente.getDireccion());

            for (Usuario u : cmbUsuario.getItems()) {
                if (u.getIdUsuario() == cliente.getIdUsuario()) {
                    cmbUsuario.getSelectionModel().select(u);
                    break;
                }
            }
        }
    }

    private boolean validarCampos() {
        if (cmbUsuario.getSelectionModel().getSelectedItem() == null ||
            txtTelefono.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor selecciona un usuario e ingresa teléfono y dirección.");
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