package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.Usuario;
import main.java.com.beauty.spa.salon.service.UsuarioService;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsuarioController implements Initializable {

    @FXML private TableView<Usuario> tblUsuarios;
    @FXML private TableColumn<Usuario, Integer> colIdUsuario;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colApellido;
    @FXML private TableColumn<Usuario, String> colNombreUsuario;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, Integer> colRol;

    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private ComboBox<String> cmbRol;
    @FXML private TextField txtBuscar;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final UsuarioService usuarioService = new UsuarioService();
    private ObservableList<Usuario> listaUsuarios;
    private FilteredList<Usuario> filteredData;
    private Usuario usuarioSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarRoles();
        cargarDatos();

        tblUsuarios.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionarElemento(newValue)
        );
    }

    private void configurarColumnas() {
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("idRol"));
    }

    private void cargarRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList(
            "1 - Administrador",
            "2 - Cliente"
        );
        cmbRol.setItems(roles);
    }

    public void cargarDatos() {
        listaUsuarios = FXCollections.observableArrayList(usuarioService.obtenerTodosLosUsuarios());
        configurarBuscador();
    }

    private void configurarBuscador() {
        filteredData = new FilteredList<>(listaUsuarios, p -> true);

        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(usr -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (usr.getNombre() != null && usr.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (usr.getApellido() != null && usr.getApellido().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (usr.getNombreUsuario() != null && usr.getNombreUsuario().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (usr.getCorreo() != null && usr.getCorreo().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
        }

        SortedList<Usuario> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblUsuarios.comparatorProperty());
        tblUsuarios.setItems(sortedData);
    }

    @FXML
    public void guardarUsuario() {
        if (!validarCamposGuardar()) return;

        int idRolSeleccionado = obtenerIdRolSeleccionado();

        boolean guardado = usuarioService.registrarUsuario(
            txtNombre.getText(),
            txtApellido.getText(),
            txtNombreUsuario.getText(),
            txtCorreo.getText(),
            txtContrasena.getText(),
            idRolSeleccionado
        );

        if (guardado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el usuario.");
        }
    }

    @FXML
    public void actualizarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un usuario de la tabla para actualizar.");
            return;
        }

        if (!validarCamposActualizar()) return;

        boolean actualizado = usuarioService.actualizarUsuario(
            usuarioSeleccionado.getIdUsuario(),
            txtNombre.getText(),
            txtApellido.getText(),
            txtNombreUsuario.getText(),
            txtCorreo.getText(),
            txtContrasena.getText(),
            usuarioSeleccionado.getContrasena(),
            obtenerIdRolSeleccionado()
        );

        if (actualizado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario actualizado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el usuario.");
        }
    }

    @FXML
    public void eliminarUsuario() {
        if (usuarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un usuario de la tabla para eliminar.");
            return;
        }

        if (usuarioService.eliminarUsuario(usuarioSeleccionado.getIdUsuario())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el usuario.");
        }
    }

    @FXML
    public void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtNombreUsuario.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        cmbRol.getSelectionModel().clearSelection();
        if (txtBuscar != null) txtBuscar.clear();
        tblUsuarios.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    private void seleccionarElemento(Usuario usuario) {
        if (usuario != null) {
            usuarioSeleccionado = usuario;
            txtNombre.setText(usuario.getNombre());
            txtApellido.setText(usuario.getApellido());
            txtNombreUsuario.setText(usuario.getNombreUsuario());
            txtCorreo.setText(usuario.getCorreo());
            txtContrasena.clear();

            for (String item : cmbRol.getItems()) {
                if (item.startsWith(usuario.getIdRol() + " -")) {
                    cmbRol.getSelectionModel().select(item);
                    break;
                }
            }
        }
    }

    private int obtenerIdRolSeleccionado() {
        String seleccion = cmbRol.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            return Integer.parseInt(seleccion.split(" -")[0]);
        }
        return 2;
    }

    private boolean validarCamposGuardar() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
            txtNombreUsuario.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
            txtContrasena.getText().isEmpty() || cmbRol.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa todos los campos.");
            return false;
        }
        return true;
    }

    private boolean validarCamposActualizar() {
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
            txtNombreUsuario.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
            cmbRol.getSelectionModel().getSelectedItem() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa los campos requeridos.");
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