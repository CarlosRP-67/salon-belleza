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

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void showLoginView() {
        cargarVistaFisica("src/main/resources/view/login-view.fxml", "src/main/resources/css/login-view.css", "Beauty Spa - Iniciar Sesión");
    }

    public void showMainDashboard() {
        cargarVistaFisica("src/main/resources/view/dashboard-view.fxml", null, "Beauty Spa - Menú Principal");
    }

    public void cargarVistaFisica(String rutaFxml, String rutaCss, String titulo) {
        try (FileInputStream archivoFisico = new FileInputStream(rutaFxml)) {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(archivoFisico);
            
            Scene scene = new Scene(root, 850, 500);
            
            if (rutaCss != null) {
                File cssFile = new File(rutaCss);
                if (cssFile.exists()) {
                    scene.getStylesheets().add(cssFile.toURI().toString());
                }
            }
            
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.setMinWidth(750);
            stage.setMinHeight(450);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.out.println("¡ERROR CRÍTICO! No se pudo cargar la vista en la ruta: " + rutaFxml);
            e.printStackTrace();
        }
    
    
    }
    
    public void showRegistroView() {
        cargarVistaFisica("src/main/resources/view/registro-view.fxml", "src/main/resources/css/registro-styles.css", "Beauty Spa - Registro de Usuario");
    }
    
}