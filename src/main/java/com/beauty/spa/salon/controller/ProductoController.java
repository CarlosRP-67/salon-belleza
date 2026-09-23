package main.java.com.beauty.spa.salon.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import main.java.com.beauty.spa.salon.model.Productos;
import main.java.com.beauty.spa.salon.repository.ProductoRepository;
import main.java.com.beauty.spa.salon.util.SceneManager; 

public class ProductoController {

    @FXML private ComboBox<Productos> cmbProductos;
    @FXML private Label lblDescripcion;
    @FXML private Label lblPrecioUnitario;
    @FXML private Spinner<Integer> spCantidad;
    @FXML private Label lblTotal;

    private final ProductoRepository productoRepository = new ProductoRepository();
    private final ObservableList<Productos> listaProductos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarComboBoxProductos();
        
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        spCantidad.setValueFactory(valueFactory);

        cargarProductos();

        cmbProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            actualizarDetallesProducto(newVal);
        });

        spCantidad.valueProperty().addListener((obs, oldVal, newVal) -> {
            recalcularTotal();
        });
    }

    private void configurarComboBoxProductos() {
        StringConverter<Productos> converter = new StringConverter<>() {
            @Override
            public String toString(Productos producto) {
                return (producto != null) ? producto.getNombreProducto() : "";
            }

            @Override
            public Productos fromString(String string) {
                return null;
            }
        };

        cmbProductos.setConverter(converter);

        cmbProductos.setCellFactory(cell -> new ListCell<Productos>() {
            @Override
            protected void updateItem(Productos item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombreProducto());
                }
            }
        });
    }

    private void cargarProductos() {
        listaProductos.clear();
   
        listaProductos.addAll(productoRepository.obtenerProductosDisponibles());
        cmbProductos.setItems(listaProductos);
    }

    private void actualizarDetallesProducto(Productos prod) {
        if (prod != null) {
            lblDescripcion.setText(prod.getDescripcion());
            lblPrecioUnitario.setText(String.format("Q%.2f", prod.getPrecio()));
            recalcularTotal();
        } else {
            lblDescripcion.setText("-");
            lblPrecioUnitario.setText("Q0.00");
            lblTotal.setText("Q0.00");
        }
    }

    private void recalcularTotal() {
        Productos prod = cmbProductos.getValue();
        if (prod != null && spCantidad.getValue() != null) {
            double total = prod.getPrecio() * spCantidad.getValue();
            lblTotal.setText(String.format("Q%.2f", total));
        }
    }

    @FXML
    private void handleComprarAction(ActionEvent event) {
        Productos prod = cmbProductos.getValue();
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
            
            cargarProductos(); 
            limpiarFormulario();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Transacción", "No se pudo procesar la compra en la base de datos.");
        }
    }

    private void limpiarFormulario() {
        cmbProductos.setValue(null);
        lblDescripcion.setText("-");
        lblPrecioUnitario.setText("Q0.00");
        lblTotal.setText("Q0.00");
        spCantidad.getValueFactory().setValue(1);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
