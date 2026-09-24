package main.java.com.beauty.spa.salon.controller;

import main.java.com.beauty.spa.salon.model.Servicio;
import main.java.com.beauty.spa.salon.service.ServicioService;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ServicioController implements Initializable {

    private static final String RUTA_IMAGENES = "/main/resources/images/";
    private static final double ANCHO_TARJETA = 260;
    private static final double ANCHO_IMAGEN = ANCHO_TARJETA - 32; // menos el padding de la tarjeta
    private static final double ALTO_IMAGEN = 150;

    @FXML private FlowPane contenedorServicios;
    @FXML private Label lblEstado;

    private final ServicioService servicioService = new ServicioService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarServicios();
    }

    private void cargarServicios() {
        try {
            List<Servicio> servicios = servicioService.listarServicios();
            contenedorServicios.getChildren().clear();

            if (servicios.isEmpty()) {
                lblEstado.setText("Por el momento no hay servicios registrados.");
                return;
            }
            for (Servicio servicio : servicios) {
                contenedorServicios.getChildren().add(crearTarjeta(servicio));
            }
        } catch (IllegalStateException e) {
            lblEstado.setText(e.getMessage());
        }
    }

    // ------------------------------------------------------------ tarjeta

    private VBox crearTarjeta(Servicio servicio) {
        VBox tarjeta = new VBox(10);
        tarjeta.getStyleClass().add("tarjeta-servicio");
        tarjeta.setPrefWidth(ANCHO_TARJETA);
        tarjeta.setMinHeight(Region.USE_PREF_SIZE);

        ImageView imagen = crearImagen(servicio.getIdServicio());
        if (imagen != null) {
            tarjeta.getChildren().add(imagen);
        }

        Label nombre = new Label(servicio.getNombreServicio());
        nombre.getStyleClass().add("servicio-nombre");
        nombre.setWrapText(true);
        nombre.setMinHeight(Region.USE_PREF_SIZE);
        tarjeta.getChildren().add(nombre);

        String texto = servicio.getDescripcion();
        if (texto != null && !texto.trim().isEmpty()) {
            Label descripcion = new Label(texto.trim());
            descripcion.getStyleClass().add("servicio-descripcion");
            descripcion.setWrapText(true);
            descripcion.setMinHeight(Region.USE_PREF_SIZE);
            tarjeta.getChildren().add(descripcion);
        }

        // Empuja la fila de precio al fondo para que todas las tarjetas queden alineadas
        Region relleno = new Region();
        VBox.setVgrow(relleno, Priority.ALWAYS);
        tarjeta.getChildren().add(relleno);

        Label precio = new Label(String.format(Locale.US, "Q%,.2f", servicio.getPrecio()));
        precio.getStyleClass().add("servicio-precio");

        Region separador = new Region();
        HBox.setHgrow(separador, Priority.ALWAYS);

        Label duracion = new Label(servicio.getDuracionMinutos() + " min");
        duracion.getStyleClass().add("badge-duracion");

        HBox filaPrecio = new HBox(8, precio, separador, duracion);
        filaPrecio.setAlignment(Pos.CENTER_LEFT);
        filaPrecio.getStyleClass().add("fila-precio");
        tarjeta.getChildren().add(filaPrecio);

        return tarjeta;
    }

    // ------------------------------------------------------------- imagen

    /**
     * Busca /main/resources/images/servicio-<id>.jpg (por ejemplo servicio-1.jpg).
     * La foto se recorta al centro con la proporción de la tarjeta, sin deformarse.
     * Si no existe, la tarjeta se muestra sin foto.
     */
    private ImageView crearImagen(int idServicio) {
        String ruta = RUTA_IMAGENES + "servicio-" + idServicio + ".jpg";

        InputStream in = getClass().getResourceAsStream(ruta);
        if (in == null) {
            System.err.println("No se encontró la imagen: " + ruta);
            return null;
        }

        // Se carga manteniendo la proporción original (alto = 0 significa "automático")
        Image foto = new Image(in, ANCHO_IMAGEN * 2, 0, true, true);
        if (foto.isError()) {
            System.err.println("No se pudo leer la imagen " + ruta + ": " + foto.getException());
            return null;
        }

        // Recorte centrado con la proporción de la tarjeta (sin deformar)
        double ratioDestino = ANCHO_IMAGEN / ALTO_IMAGEN;
        double ratioFoto = foto.getWidth() / foto.getHeight();
        double w, h, x, y;
        if (ratioFoto > ratioDestino) {      // foto más ancha: se recortan los lados
            h = foto.getHeight();
            w = h * ratioDestino;
            x = (foto.getWidth() - w) / 2;
            y = 0;
        } else {                             // foto más alta: se recortan arriba y abajo
            w = foto.getWidth();
            h = w / ratioDestino;
            x = 0;
            y = (foto.getHeight() - h) / 2;
        }

        ImageView vista = new ImageView(foto);
        vista.setViewport(new Rectangle2D(x, y, w, h));
        vista.setFitWidth(ANCHO_IMAGEN);
        vista.setFitHeight(ALTO_IMAGEN);

        Rectangle esquinas = new Rectangle(ANCHO_IMAGEN, ALTO_IMAGEN);
        esquinas.setArcWidth(16);
        esquinas.setArcHeight(16);
        vista.setClip(esquinas);
        return vista;
    }
}