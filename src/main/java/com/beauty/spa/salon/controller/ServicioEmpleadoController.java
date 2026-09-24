package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.repository.ServicioRepository;
import main.java.com.beauty.spa.salon.model.Administrador;
import main.java.com.beauty.spa.salon.model.Servicio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Controlador de servicios.fxml. Solo se llega aquí después de un
 * login de administrador correcto (ver LoginController).
 */
public class ServicioEmpleadoController {

    @FXML private Label lblTitulo;
    @FXML private TableView<Servicio> tabla;
    @FXML private TableColumn<Servicio, String> colNombre;
    @FXML private TableColumn<Servicio, String> colDescripcion;
    @FXML private TableColumn<Servicio, Double> colPrecio;
    @FXML private TableColumn<Servicio, Integer> colDuracion;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtDuracion;

    private final ServicioRepository servicioDAO = new ServicioRepository();
    private final ObservableList<Servicio> datos = FXCollections.observableArrayList();
    private Administrador admin;

    @FXML
    private void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));
        tabla.setItems(datos);
    }

    /** Llamado por LoginController justo después de crear esta pantalla. */
    public void setAdministrador(Administrador admin) {
        this.admin = admin;
        lblTitulo.setText("Servicios del Salón - Sesión: " + admin.getNombre());
        cargarServicios();
    }

    private void cargarServicios() {
        try {
            datos.setAll(servicioDAO.listarTodos());
        } catch (SQLException ex) {
            mostrarAlerta(AlertType.ERROR, "No se pudieron cargar los servicios:\n" + ex.getMessage());
        }
    }

    @FXML
    private void guardarServicio(ActionEvent event) {
        String nombre = txtNombre.getText() == null ? "" : txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText() == null ? "" : txtDescripcion.getText().trim();

        if (nombre.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "El nombre del servicio es obligatorio.");
            return;
        }

        double precio;
        int duracion;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim().replace(",", "."));
            duracion = Integer.parseInt(txtDuracion.getText().trim());
        } catch (NumberFormatException ex) {
            mostrarAlerta(AlertType.WARNING, "Precio y duración deben ser números válidos.");
            return;
        }

        if (precio < 0 || duracion < 0) {
            mostrarAlerta(AlertType.WARNING, "Precio y duración no pueden ser negativos.");
            return;
        }

        Servicio nuevo = new Servicio(nombre, descripcion, precio, duracion);

        try {
            servicioDAO.crear(nuevo, admin.getId());
            datos.add(nuevo);

            txtNombre.clear();
            txtDescripcion.clear();
            txtPrecio.clear();
            txtDuracion.clear();

            mostrarAlerta(AlertType.INFORMATION, "Servicio \"" + nuevo.getNombre() + "\" creado correctamente.");
        } catch (SQLException ex) {
            mostrarAlerta(AlertType.ERROR, "No se pudo guardar el servicio:\n" + ex.getMessage());
        }
    }


    private void mostrarAlerta(AlertType tipo, String mensaje) {
        new Alert(tipo, mensaje).showAndWait();
    }
}
