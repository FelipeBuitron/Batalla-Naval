package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para la pantalla de instrucciones del juego.
 * <p>
 * Se encarga de gestionar los eventos de navegación de la vista de instrucciones,
 * permitiendo al jugador volver al menú principal.
 * </p>
 *
 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class InstruccionesController {

    /**
     * Regresa a la pantalla del menú principal (Inicio).
     *
     * @param event Evento de acción disparado por el botón de regresar.
     * @throws IOException Si ocurre un error al cargar el archivo FXML {@code InicioView.fxml}.
     */
    @FXML
    private void regresar(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/InicioView.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}