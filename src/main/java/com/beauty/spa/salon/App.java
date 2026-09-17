
package main.java.com.beauty.spa.salon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Carlitos
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carga la vista FXML desde la carpeta resources/view
            Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/registro-view.fxml"));
            
            Scene scene = new Scene(root);
            primaryStage.setTitle("Salón de Belleza - Registro de Usuarios");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}