package main.java.com.beauty.spa.salon;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.com.beauty.spa.salon.util.SceneManager;
import java.io.InputStream;

public class App extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        try {
            InputStream iconStream = getClass().getResourceAsStream("/images/beauty-logo.png");
            if (iconStream == null) {
                iconStream = getClass().getResourceAsStream("/main/resources/images/beauty-logo.png");
            }
            if (iconStream != null) {
                stage.getIcons().add(new Image(iconStream));
            }
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono: " + e.getMessage());
        }

        AnchorPane splashRoot = new AnchorPane();
        splashRoot.setStyle("-fx-background-color: #FFF0F5;"); 

        VBox contenedorCentrado = new VBox(30); 
        contenedorCentrado.setAlignment(Pos.CENTER);
        
        AnchorPane.setBottomAnchor(contenedorCentrado, 0.0);
        AnchorPane.setLeftAnchor(contenedorCentrado, 0.0);
        AnchorPane.setRightAnchor(contenedorCentrado, 0.0);
        AnchorPane.setTopAnchor(contenedorCentrado, 0.0);

        ImageView imgLogo = new ImageView();
        imgLogo.setPreserveRatio(true);
        
        try {
            InputStream logoStream = getClass().getResourceAsStream("/images/beauty-logo.png");
            if (logoStream == null) {
                logoStream = getClass().getResourceAsStream("/main/resources/images/beauty-logo.png");
            }
            if (logoStream != null) {
                imgLogo.setImage(new Image(logoStream));
            }
        } catch (Exception e) {
            System.out.println("No se pudo pintar el logo en el Splash: " + e.getMessage());
        }

        imgLogo.fitHeightProperty().bind(stage.heightProperty().multiply(0.30));
        imgLogo.fitWidthProperty().bind(stage.widthProperty().multiply(0.30));

        ProgressBar progressBar = new ProgressBar(0.0);
        progressBar.prefWidthProperty().bind(stage.widthProperty().multiply(0.40)); 
        progressBar.setMaxWidth(600); 
        progressBar.setMinWidth(250); 
        progressBar.setStyle("-fx-accent: #FFB6C1; -fx-control-inner-background: #FFF0F5;");

        contenedorCentrado.getChildren().addAll(imgLogo, progressBar);
        splashRoot.getChildren().add(contenedorCentrado);

        Scene splashScene = new Scene(splashRoot, 850, 500);
        stage.setScene(splashScene);
        stage.setTitle("Beauty Spa - Cargando componentes...");
        stage.setMinWidth(750); // Evita que rompan el diseño haciéndola muy chica
        stage.setMinHeight(450);
        stage.centerOnScreen();
        stage.show();

        Task<Void> tareaCarga = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(30); 
                    updateProgress(i, 100);
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(tareaCarga.progressProperty());

        tareaCarga.setOnSucceeded(workerStateEvent -> {
            SceneManager sceneManager = new SceneManager(stage);
            sceneManager.showLoginView();
        });

        new Thread(tareaCarga).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}