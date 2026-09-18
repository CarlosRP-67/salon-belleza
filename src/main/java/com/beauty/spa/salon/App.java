package main.java.com.beauty.spa.salon;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.com.beauty.spa.salon.util.SceneManager;

public class App extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        // 1. Configurar el icono de la ventana / barra de tareas
        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/main/resources/images/beauty-logo.png")));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + e.getMessage());
        }

        // 2. Instanciar el SceneManager pasando el stage y mostrando la vista inicial
        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.showLoginView(); // O la vista inicial configurada en tu SceneManager
        
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}