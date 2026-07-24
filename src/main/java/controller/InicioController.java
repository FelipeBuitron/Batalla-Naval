package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Juego;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Controlador para la pantalla de inicio del juego (Menú Principal).
 * <p>
 * Gestiona las opciones del menú principal, incluyendo la navegación hacia la
 * vista de instrucciones, la creación de una nueva partida y la carga
 * de una partida previamente guardada.
 * </p>

 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class InicioController {

    /**
     * Muestra la pantalla de instrucciones del juego.
     *
     * @param event Evento de acción provocado por el botón de instrucciones.
     * @throws IOException Si ocurre un problema al cargar el archivo FXML {@code InstruccionesView.fxml}.
     */
    @FXML
    private void abrirInstrucciones(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/InstruccionesView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Inicia el proceso de creación de una nueva partida cambiando la escena
     * a la vista de colocación de barcos.
     *
     * @param event Evento de acción provocado por el botón de nuevo juego.
     * @throws IOException Si ocurre un problema al cargar el archivo FXML {@code ColocarBarcosView.fxml}.
     */
    @FXML
    private void nuevoJuego(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ColocarBarcosView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Carga una partida guardada previamente desde el archivo local {@code partida.dat}.
     * <p>
     * Si la deserialización del objeto {@link Juego} es exitosa, se inicia la vista
     * principal de juego reanudando el estado anterior. En caso de fallo o de no
     * existir el archivo, se despliega una alerta de error en la interfaz.
     * </p>
     *
     * @param event Evento de acción provocado por el botón de cargar juego.
     */
    @FXML
    private void cargarJuego(ActionEvent event) {
        try (FileInputStream archivo = new FileInputStream("partida.dat");
             ObjectInputStream entrada = new ObjectInputStream(archivo)) {

            Juego juego = (Juego) entrada.readObject();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/JuegoView.fxml"));
            Parent root = loader.load();

            JuegoController controller = loader.getController();
            controller.setJuego(juego);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException | ClassNotFoundException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("No fue posible cargar la partida.");
            alerta.showAndWait();

            e.printStackTrace();
        }
    }
}