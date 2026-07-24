package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación Batalla Naval.
 * <p>
 * Esta clase inicia la aplicación JavaFX, carga la vista principal
 * (InicioView.fxml) y muestra la ventana inicial del juego.
 * </p>
 *
 * @author Andrés
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Método que inicia la aplicación JavaFX.
     * Carga la interfaz gráfica inicial desde el archivo FXML,
     * configura el escenario principal y lo muestra al usuario.
     *
     * @param stage Escenario principal de la aplicación.
     * @throws Exception Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InicioView.fxml"));

        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("BATALLA NAVAL");
        stage.show();
    }

    /**
     * Punto de entrada de la aplicación.
     * Lanza la aplicación JavaFX.
     *
     * @param args Argumentos enviados desde la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}