package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.Producto;
import main.java.com.beauty.spa.salon.service.ProductoService;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductoController implements Initializable {

    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<Producto, Integer> colIdProducto;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colDescripcion;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtBuscar;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;

    private final ProductoService productoService = new ProductoService();
    private ObservableList<Producto> listaProductos;
    private FilteredList<Producto> filteredData;
    private Producto productoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDatos();

        tblProductos.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> seleccionarElemento(newValue)
        );
    }

    private void configurarColumnas() {
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    public void cargarDatos() {
        listaProductos = FXCollections.observableArrayList(productoService.obtenerTodosLosProductos());
        configurarBuscador();
    }

    private void configurarBuscador() {
        filteredData = new FilteredList<>(listaProductos, p -> true);

        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(prod -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (prod.getNombre() != null && prod.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (prod.getDescripcion() != null && prod.getDescripcion().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
        }

        SortedList<Producto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblProductos.comparatorProperty());
        tblProductos.setItems(sortedData);
    }

    @FXML
    public void guardarProducto() {
        if (!validarCampos()) return;

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            boolean guardado = productoService.registrarProducto(
                txtNombre.getText(),
                txtDescripcion.getText(),
                precio,
                stock
            );

            if (guardado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto registrado correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo registrar el producto.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Formato incorrecto", "El precio y el stock deben ser valores numéricos válidos.");
        }
    }

    @FXML
    public void actualizarProducto() {
        if (productoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un producto de la tabla para actualizar.");
            return;
        }

        if (!validarCampos()) return;

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            boolean actualizado = productoService.actualizarProducto(
                productoSeleccionado.getIdProducto(),
                txtNombre.getText(),
                txtDescripcion.getText(),
                precio,
                stock
            );

            if (actualizado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto actualizado correctamente.");
                limpiarCampos();
                cargarDatos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el producto.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Formato incorrecto", "El precio y el stock deben ser valores numéricos válidos.");
        }
    }

    @FXML
    public void eliminarProducto() {
        if (productoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un producto de la tabla para eliminar.");
            return;
        }

        if (productoService.eliminarProducto(productoSeleccionado.getIdProducto())) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto eliminado correctamente.");
            limpiarCampos();
            cargarDatos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el producto.");
        }
    }

    @FXML
    public void limpiarCampos() {
        txtNombre.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtStock.clear();
        if (txtBuscar != null) txtBuscar.clear();
        tblProductos.getSelectionModel().clearSelection();
        productoSeleccionado = null;
    }

    private void seleccionarElemento(Producto producto) {
        if (producto != null) {
            productoSeleccionado = producto;
            txtNombre.setText(producto.getNombre());
            txtDescripcion.setText(producto.getDescripcion());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtStock.setText(String.valueOf(producto.getStock()));
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty() || txtStock.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa Nombre, Precio y Stock.");
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