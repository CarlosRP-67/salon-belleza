package main.java.com.beauty.spa.salon.controller;

<<<<<<< HEAD
import main.java.com.beauty.spa.salon.repository.FacturaRepository;
import main.java.com.beauty.spa.salon.repository.ServicioRepository;
import main.java.com.beauty.spa.salon.repository.UsuarioRepository;
import main.java.com.beauty.spa.salon.model.DetalleFactura;
import main.java.com.beauty.spa.salon.model.Factura;
import main.java.com.beauty.spa.salon.model.Servicio;
import main.java.com.beauty.spa.salon.model.Usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Controlador de factura.fxml. Permite elegir/crear un cliente,
 * armar un "carrito" de servicios y generar la factura.
 */
public class FacturaController {

    @FXML private ComboBox<Usuario> comboUsuarios;

    @FXML private TableView<Servicio> tablaServicios;
    @FXML private TableColumn<Servicio, String> colServicioNombre;
    @FXML private TableColumn<Servicio, Double> colServicioPrecio;

    @FXML private TableView<DetalleFactura> tablaCarrito;
    @FXML private TableColumn<DetalleFactura, String> colCarritoServicio;
    @FXML private TableColumn<DetalleFactura, Integer> colCarritoCantidad;
    @FXML private TableColumn<DetalleFactura, Double> colCarritoPrecioUnit;
    @FXML private TableColumn<DetalleFactura, Double> colCarritoSubtotal;

    @FXML private Spinner<Integer> spinnerCantidad;
    @FXML private Label lblTotal;

    private final UsuarioRepository usuarioDAO = new UsuarioRepository();
    private final ServicioRepository servicioDAO = new ServicioRepository();
    private final FacturaRepository facturaDAO = new FacturaRepository();

    private final ObservableList<Servicio> serviciosDisponibles = FXCollections.observableArrayList();
    private final ObservableList<DetalleFactura> carrito = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colServicioNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colServicioPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tablaServicios.setItems(serviciosDisponibles);

        colCarritoServicio.setCellValueFactory(new PropertyValueFactory<>("servicioNombre"));
        colCarritoCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCarritoPrecioUnit.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colCarritoSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        tablaCarrito.setItems(carrito);

        spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));

        cargarUsuarios();
        cargarServicios();
    }

    private void cargarUsuarios() {
        try {
            comboUsuarios.setItems(FXCollections.observableArrayList(usuarioDAO.listar()));
        } catch (SQLException ex) {
            mostrarAlerta(AlertType.ERROR, "No se pudieron cargar los usuarios:\n" + ex.getMessage());
        }
    }

    private void cargarServicios() {
        try {
            serviciosDisponibles.setAll(servicioDAO.listarActivos());
        } catch (SQLException ex) {
            mostrarAlerta(AlertType.ERROR, "No se pudieron cargar los servicios:\n" + ex.getMessage());
        }
    }

    @FXML
    private void crearUsuarioRapido(ActionEvent event) {
        TextInputDialog dialogNombre = new TextInputDialog();
        dialogNombre.setTitle("Nuevo cliente");
        dialogNombre.setHeaderText(null);
        dialogNombre.setContentText("Nombre del cliente:");
        Optional<String> nombreOpt = dialogNombre.showAndWait();
        if (nombreOpt.isEmpty() || nombreOpt.get().isBlank()) {
            return;
        }

        TextInputDialog dialogEmail = new TextInputDialog();
        dialogEmail.setTitle("Nuevo cliente");
        dialogEmail.setHeaderText(null);
        dialogEmail.setContentText("Email (opcional):");
        Optional<String> emailOpt = dialogEmail.showAndWait();

        TextInputDialog dialogTelefono = new TextInputDialog();
        dialogTelefono.setTitle("Nuevo cliente");
        dialogTelefono.setHeaderText(null);
        dialogTelefono.setContentText("Teléfono (opcional):");
        Optional<String> telefonoOpt = dialogTelefono.showAndWait();

        Usuario nuevo = new Usuario(
                nombreOpt.get().trim(),
                emailOpt.orElse("").trim(),
                telefonoOpt.orElse("").trim()
        );

        try {
            usuarioDAO.crear(nuevo);
            comboUsuarios.getItems().add(nuevo);
            comboUsuarios.getSelectionModel().select(nuevo);
        } catch (SQLException ex) {
            mostrarAlerta(AlertType.ERROR, "No se pudo crear el cliente:\n" + ex.getMessage());
=======
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
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
        }
    }

    @FXML
<<<<<<< HEAD
    private void agregarAlCarrito(ActionEvent event) {
        Servicio seleccionado = tablaServicios.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(AlertType.WARNING, "Selecciona un servicio de la lista.");
            return;
        }

        int cantidad = spinnerCantidad.getValue();
        carrito.add(new DetalleFactura(seleccionado, cantidad));
        actualizarTotal();
    }

    @FXML
    private void quitarDelCarrito(ActionEvent event) {
        DetalleFactura seleccionado = tablaCarrito.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            carrito.remove(seleccionado);
            actualizarTotal();
        }
    }

    private void actualizarTotal() {
        double total = carrito.stream().mapToDouble(DetalleFactura::getSubtotal).sum();
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    @FXML
    private void generarFactura(ActionEvent event) {
        Usuario usuario = comboUsuarios.getSelectionModel().getSelectedItem();
        if (usuario == null) {
            mostrarAlerta(AlertType.WARNING, "Selecciona (o crea) un cliente para facturar.");
            return;
        }
        if (carrito.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Agrega al menos un servicio antes de generar la factura.");
            return;
        }

        Factura factura = new Factura(usuario.getId());
        factura.setUsuarioNombre(usuario.getNombre());
        for (DetalleFactura detalle : carrito) {
            factura.agregarDetalle(detalle);
        }

        try {
            facturaDAO.crearFactura(factura);
            mostrarAlerta(AlertType.INFORMATION, String.format(
                    "Factura #%d generada para %s.\nTotal: $%.2f",
                    factura.getId(), usuario.getNombre(), factura.getTotal()));

            carrito.clear();
            actualizarTotal();
        } catch (SQLException ex) {
            mostrarAlerta(AlertType.ERROR, "No se pudo generar la factura:\n" + ex.getMessage());
        }
    }

    @FXML
    private void volverAlMenu(ActionEvent event) {
        Stage stage = (Stage) comboUsuarios.getScene().getWindow();
        SceneNavigator.cambiarA(stage, "/main/resources/fxml/main_menu.fxml");
    }

    private void mostrarAlerta(AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje).showAndWait();
    }
}
=======
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
>>>>>>> 2c0173fd990623de1dfa770a75ae84167881e15f
