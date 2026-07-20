package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {

    @FXML
    private void abrirInstrucciones(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/InstruccionesView.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void nuevoJuego(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/view/ColocarBarcosView.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
