package main.java.com.beauty.spa.salon.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utilidad para cargar pantallas FXML y mostrarlas en el Stage,
 * aplicando siempre la misma hoja de estilos.
 */
public final class SceneNavigator {

    private static final String CSS_PATH = "/main/resources/css/styles.css";

    private SceneNavigator() {
    }

    /**
     * Carga un FXML y devuelve su loader (con la vista y el controlador
     * ya creados), sin mostrarlo todavía. Útil cuando hay que pasarle
     * datos al controlador antes de que la pantalla aparezca.
     */
    public static FXMLLoader cargar(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
            loader.load();
            return loader;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar la pantalla: " + fxmlPath, e);
        }
    }

    /** Muestra en el stage la vista que ya cargó un FXMLLoader. */
    public static void mostrar(Stage stage, FXMLLoader loader) {
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneNavigator.class.getResource(CSS_PATH).toExternalForm());
        stage.setScene(scene);
    }

    /** Atajo para cuando no hay datos que pasarle al controlador destino. */
    public static void cambiarA(Stage stage, String fxmlPath) {
        mostrar(stage, cargar(fxmlPath));
    }
}
