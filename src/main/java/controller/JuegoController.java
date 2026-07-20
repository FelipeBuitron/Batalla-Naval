package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class JuegoController {

    @FXML
    private GridPane tableroJugador;

    @FXML
    private GridPane tableroMaquina;

    @FXML
    public void initialize() {
        crearTablero(tableroJugador);
        crearTablero(tableroMaquina);
    }

    private void crearTablero(GridPane tablero) {

        for (int fila = 0; fila < 10; fila++) {

            for (int columna = 0; columna < 10; columna++) {

                Button boton = new Button();
                boton.setPrefSize(40, 40);

                tablero.add(boton, columna, fila);

            }

        }
    }
}