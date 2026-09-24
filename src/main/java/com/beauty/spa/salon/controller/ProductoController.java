package main.java.com.beauty.spa.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import main.java.com.beauty.spa.salon.model.ProductoEmpleado;
import main.java.com.beauty.spa.salon.model.ProductoUsuario;
import main.java.com.beauty.spa.salon.repository.ProductoRepository;
import main.java.com.beauty.spa.salon.service.ProductoService;
import main.java.com.beauty.spa.salon.util.SceneManager;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductoController implements Initializable {


    @FXML private TableView<ProductoEmpleado> tblProductos;
    @FXML private TableColumn<ProductoEmpleado, Integer> colIdProducto;
    @FXML private TableColumn<ProductoEmpleado, String> colNombre;
    @FXML private TableColumn<ProductoEmpleado, String> colDescripcion;
    @FXML private TableColumn<ProductoEmpleado, Double> colPrecio;
    @FXML private TableColumn<ProductoEmpleado, Integer> colStock;

    @FXML private TextField txtNombre;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private TextField txtBuscar;

    @FXML private Button btnGuardar;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private Button btnLimpiar;


    @FXML private ComboBox<ProductoUsuario> cmbProductos;
    @FXML private Label lblDescripcion;
    @FXML private Label lblPrecioUnitario;
    @FXML private Spinner<Integer> spCantidad;
    @FXML private Label lblTotal;

   
    private final ProductoService productoService = new ProductoService();
    private final ProductoRepository productoRepository = new ProductoRepository();
    
    private ObservableList<ProductoEmpleado> listaProductosCrud;
    private final ObservableList<ProductoUsuario> listaProductosDisponibles = FXCollections.observableArrayList();
    private FilteredList<ProductoEmpleado> filteredData;
    private ProductoEmpleado productoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (tblProductos != null) {
            configurarColumnas();
            cargarDatosCrud();
            tblProductos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> seleccionarElementoCrud(newValue)
            );
        }

       
        if (cmbProductos != null) {
            configurarComboBoxProductos();
            if (spCantidad != null) {
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
                spCantidad.setValueFactory(valueFactory);
                spCantidad.valueProperty().addListener((obs, oldVal, newVal) -> recalcularTotalCompra());
            }
            cargarProductosDisponibles();
            cmbProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                actualizarDetallesProducto(newVal);
            });
        }
    }

    // --- MÉTODOS CRUD ---
    private void configurarColumnas() {
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    public void cargarDatosCrud() {
        listaProductosCrud = FXCollections.observableArrayList(productoService.obtenerTodosLosProductos());
        configurarBuscadorCrud();
    }

    private void configurarBuscadorCrud() {
        filteredData = new FilteredList<>(listaProductosCrud, p -> true);

        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(prod -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (prod.getNombre() != null && prod.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return prod.getDescripcion() != null && prod.getDescripcion().toLowerCase().contains(lowerCaseFilter);
                });
            });
        }

        SortedList<ProductoEmpleado> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblProductos.comparatorProperty());
        tblProductos.setItems(sortedData);
    }

    @FXML
    public void guardarProducto() {
        if (!validarCamposCrud()) return;
        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            boolean guardado = productoService.registrarProducto(
                txtNombre.getText(), txtDescripcion.getText(), precio, stock
            );

            if (guardado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto registrado correctamente.");
                limpiarCamposCrud();
                cargarDatosCrud();
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
        if (!validarCamposCrud()) return;

        try {
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            boolean actualizado = productoService.actualizarProducto(
                productoSeleccionado.getIdProducto(), txtNombre.getText(), txtDescripcion.getText(), precio, stock
            );

            if (actualizado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto actualizado correctamente.");
                limpiarCamposCrud();
                cargarDatosCrud();
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
            limpiarCamposCrud();
            cargarDatosCrud();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el producto.");
        }
    }

    @FXML
    public void limpiarCamposCrud() {
        if (txtNombre != null) txtNombre.clear();
        if (txtDescripcion != null) txtDescripcion.clear();
        if (txtPrecio != null) txtPrecio.clear();
        if (txtStock != null) txtStock.clear();
        if (txtBuscar != null) txtBuscar.clear();
        if (tblProductos != null) tblProductos.getSelectionModel().clearSelection();
        productoSeleccionado = null;
    }

    private void seleccionarElementoCrud(ProductoEmpleado producto) {
        if (producto != null) {
            productoSeleccionado = producto;
            if (txtNombre != null) txtNombre.setText(producto.getNombre());
            if (txtDescripcion != null) txtDescripcion.setText(producto.getDescripcion());
            if (txtPrecio != null) txtPrecio.setText(String.valueOf(producto.getPrecio()));
            if (txtStock != null) txtStock.setText(String.valueOf(producto.getStock()));
        }
    }

    private boolean validarCamposCrud() {
        if (txtNombre == null || txtPrecio == null || txtStock == null) return true;
        if (txtNombre.getText().isEmpty() || txtPrecio.getText().isEmpty() || txtStock.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor completa Nombre, Precio y Stock.");
            return false;
        }
        return true;
    }

    // --- MÉTODOS COMPRA / CATÁLOGO ---
    private void configurarComboBoxProductos() {
        StringConverter<ProductoUsuario> converter = new StringConverter<>() {
            @Override
            public String toString(ProductoUsuario producto) {
                return (producto != null) ? producto.getNombreProducto() : "";
            }
            @Override
            public ProductoUsuario fromString(String string) {
                return null;
            }
        };
        cmbProductos.setConverter(converter);
        cmbProductos.setCellFactory(cell -> new ListCell<ProductoUsuario>() {
            @Override
            protected void updateItem(ProductoUsuario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombreProducto());
                }
            }
        });
    }

    private void cargarProductosDisponibles() {
        listaProductosDisponibles.clear();
        listaProductosDisponibles.addAll(productoRepository.obtenerProductosDisponibles());
        if (cmbProductos != null) {
            cmbProductos.setItems(listaProductosDisponibles);
        }
    }

    private void actualizarDetallesProducto(ProductoUsuario prod) {
        if (prod != null) {
            if (lblDescripcion != null) lblDescripcion.setText(prod.getDescripcion());
            if (lblPrecioUnitario != null) lblPrecioUnitario.setText(String.format("Q%.2f", prod.getPrecio()));
            recalcularTotalCompra();
        } else {
            if (lblDescripcion != null) lblDescripcion.setText("-");
            if (lblPrecioUnitario != null) lblPrecioUnitario.setText("Q0.00");
            if (lblTotal != null) lblTotal.setText("Q0.00");
        }
    }

    private void recalcularTotalCompra() {
        if (cmbProductos == null || spCantidad == null || lblTotal == null) return;
        ProductoUsuario prod = cmbProductos.getValue();
        if (prod != null && spCantidad.getValue() != null) {
            double total = prod.getPrecio() * spCantidad.getValue();
            lblTotal.setText(String.format("Q%.2f", total));
        }
    }

    @FXML
    private void handleComprarAction(ActionEvent event) {
        if (cmbProductos == null || spCantidad == null) return;
        ProductoUsuario prod = cmbProductos.getValue();
        if (prod == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atención", "Seleccione un producto para comprar.");
            return;
        }

        int cantidad = spCantidad.getValue();
        if (cantidad > prod.getStock()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Stock Insuficiente", "Solo quedan " + prod.getStock() + " unidades disponibles.");
            return;
        }

        double total = prod.getPrecio() * cantidad;
        int idClienteActual = SceneManager.getIdClienteActual();

        boolean exito = productoRepository.realizarCompra(idClienteActual, prod.getIdProducto(), cantidad, total);

        if (exito) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Compra Exitosa", 
                String.format("¡Compra realizada con éxito!\nProducto: %s\nCantidad: %d\nTotal: Q%.2f", 
                prod.getNombreProducto(), cantidad, total));
            cargarProductosDisponibles();
            limpiarFormularioCompra();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Transacción", "No se pudo procesar la compra en la base de datos.");
        }
    }

    private void limpiarFormularioCompra() {
        if (cmbProductos != null) cmbProductos.setValue(null);
        if (lblDescripcion != null) lblDescripcion.setText("-");
        if (lblPrecioUnitario != null) lblPrecioUnitario.setText("Q0.00");
        if (lblTotal != null) lblTotal.setText("Q0.00");
        if (spCantidad != null && spCantidad.getValueFactory() != null) spCantidad.getValueFactory().setValue(1);
    }

    // --- UTILIDAD GENERAL ---
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}