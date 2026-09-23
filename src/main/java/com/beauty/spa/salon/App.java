package main.java.com.beauty.spa.salon;

import main.java.com.beauty.spa.salon.controller.SceneNavigator;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Punto de entrada de la aplicación JavaFX del Salón de Belleza.
 * Carga el menú principal desde FXML.
 *
 * @author Carlitos
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Salón de Belleza");
        SceneNavigator.cambiarA(primaryStage, "/main/resources/fxml/main_menu.fxml");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
