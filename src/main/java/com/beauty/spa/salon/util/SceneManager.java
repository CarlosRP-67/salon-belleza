package main.java.com.beauty.spa.salon.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SceneManager {

    private final Stage stage;
    private static int idClienteActual;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public static void setIdClienteActual(int idCliente) {
        idClienteActual = idCliente;
    }

    public static int getIdClienteActual() {
        return idClienteActual;
    }


    public void showLoginView() {
        cargarVistaFisica("src/main/resources/view/login-view.fxml", "src/main/resources/css/login-view.css", "Beauty Spa - Iniciar Sesión");
    }

    public void showMainDashboard() {

        cargarVistaFisica("src/main/resources/view/dashboard-view.fxml", "src/main/resources/css/usuarios-citas-view.css", "Beauty Spa - Menú Principal");
    }

    public void showProductosView() {
        cargarVistaFisica("src/main/resources/view/venta-productos-view.fxml", "src/main/resources/css/venta-produtos-view.css", "Beauty Spa - Catálogo de Productos");
    }

    public void cargarVistaClasspath(String rutaFxml, String rutaCss, String titulo) {
        // Redirige llamadas de classpath a la ruta física real
        String fxmlFisico = "src/main/resources" + (rutaFxml.startsWith("/") ? rutaFxml : "/" + rutaFxml);
        String cssFisico = (rutaCss != null) ? "src/main/resources" + (rutaCss.startsWith("/") ? rutaCss : "/" + rutaCss) : null;
        
        cargarVistaFisica(fxmlFisico, cssFisico, titulo);
    }

    public void cargarVistaFisica(String rutaFxml, String rutaCss, String titulo) {
        File archivoFxml = new File(rutaFxml);
        
        if (!archivoFxml.exists()) {
            System.err.println("¡ERROR! No existe el archivo físico en la ruta: " + archivoFxml.getAbsolutePath());
            return;
        }

        try (FileInputStream fis = new FileInputStream(archivoFxml)) {
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(archivoFxml.getParentFile().toURI().toURL());

            Parent root = loader.load(fis);

            Scene scene = new Scene(root, 880, 550);

            if (rutaCss != null) {
                File cssFile = new File(rutaCss);
                if (cssFile.exists()) {
                    scene.getStylesheets().add(cssFile.toURI().toString());
                } else {
                    System.out.println("Advertencia: No se encontró el CSS en: " + rutaCss);
                }
            }

            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setMinWidth(750);
            stage.setMinHeight(450);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            System.err.println("¡ERROR CRÍTICO! Falló la carga de la vista: " + rutaFxml);
            e.printStackTrace();
        }
    }
}
