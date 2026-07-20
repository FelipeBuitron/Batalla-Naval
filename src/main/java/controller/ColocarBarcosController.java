package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import model.Barco;
import model.Juego;
import model.Orientacion;
import model.TipoBarco;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;

public class ColocarBarcosController {
    @FXML private GridPane tableroJugador;
    @FXML private ComboBox<String> cmbOrientacion;
    private Juego juego;
    private Barco barcoSeleccionado;
    private Button[][] botonesJugador;
    private Orientacion orientacionSeleccionada;
    private Button botonBarcoSeleccionado;
    private final String ESTILO_NORMAL = "";
    private final String ESTILO_SELECCIONADO =
            "-fx-background-color: #FFD54F; -fx-border-color: black; -fx-border-width: 2;";



    @FXML
    public void initialize() {

        juego = new Juego("Felipe");

        botonesJugador = new Button[10][10];

        crearTablero(tableroJugador);

        // Configurar el ComboBox de orientación
        cmbOrientacion.getItems().addAll("Horizontal", "Vertical");
        cmbOrientacion.setValue("Horizontal");

        orientacionSeleccionada = Orientacion.HORIZONTAL;
    }

    private void crearTablero(GridPane tablero) {

        for (int fila = 0; fila < 10; fila++) {

            for (int columna = 0; columna < 10; columna++) {

                int filaActual = fila;
                int columnaActual = columna;
                Button boton = new Button();

                boton.setMinSize(40, 40);
                boton.setPrefSize(40, 40);
                boton.setMaxSize(40, 40);

                boton.setGraphicTextGap(0);
                boton.getStyleClass().add("casilla-tablero");

                botonesJugador[fila][columna] = boton;

                boton.setOnAction(event -> {

                    if (barcoSeleccionado == null) {
                        return;
                    }

                    boolean colocado = juego.getJugador()
                            .getTablero()
                            .colocarBarco(barcoSeleccionado, filaActual, columnaActual, orientacionSeleccionada);

                    if (colocado) {

                        dibujarBarco(filaActual, columnaActual);

                        botonBarcoSeleccionado.setStyle(ESTILO_NORMAL);
                        botonBarcoSeleccionado.setDisable(true);

                        barcoSeleccionado = null;
                        botonBarcoSeleccionado = null;
                    }

                });

                tablero.add(boton, columna, fila);

            }

        }
    }

    @FXML
    private void seleccionarBarco(javafx.event.ActionEvent event) {

        // Si había un barco seleccionado, solo quitar el color
        if (botonBarcoSeleccionado != null) {
            botonBarcoSeleccionado.setStyle(ESTILO_NORMAL);
        }

        botonBarcoSeleccionado = (Button) event.getSource();

        botonBarcoSeleccionado.setStyle(ESTILO_SELECCIONADO);

        String texto = botonBarcoSeleccionado.getText();

        if (texto.contains("Portaaviones")) {

            barcoSeleccionado = new Barco(TipoBarco.PORTAAVIONES, 4);

        } else if (texto.contains("Submarino")) {

            barcoSeleccionado = new Barco(TipoBarco.SUBMARINO, 3);

        } else if (texto.contains("Destructor")) {

            barcoSeleccionado = new Barco(TipoBarco.DESTRUCTOR, 2);

        } else if (texto.contains("Fragata")) {

            barcoSeleccionado = new Barco(TipoBarco.FRAGATA, 1);

        }
    }
    private void dibujarBarco(int fila, int columna) {

        String[] imagenes = obtenerImagenesBarco();

        for (int i = 0; i < barcoSeleccionado.getTamano(); i++) {

            Image imagen = new Image(getClass().getResourceAsStream(imagenes[i]));

            ImageView imageView = new ImageView(imagen);

            imageView.setFitWidth(36);
            imageView.setFitHeight(36);
            imageView.setPreserveRatio(false);

            if (orientacionSeleccionada == Orientacion.HORIZONTAL) {
                botonesJugador[fila][columna + i].setGraphic(imageView);
            } else {
                botonesJugador[fila + i][columna].setGraphic(imageView);
            }

        }

    }
    private String[] obtenerImagenesBarco() {

        switch (barcoSeleccionado.getTipo()) {

            case PORTAAVIONES:

                if (orientacionSeleccionada == Orientacion.HORIZONTAL) {
                    return new String[]{
                            "/barcos/portaaviones_h_inicio.png",
                            "/barcos/portaaviones_h_centro1.png",
                            "/barcos/portaaviones_h_centro2.png",
                            "/barcos/portaaviones_h_fin.png"
                    };
                } else {
                    return new String[]{
                            "/barcos/portaaviones_v_inicio.png",
                            "/barcos/portaaviones_v_centro1.png",
                            "/barcos/portaaviones_v_centro2.png",
                            "/barcos/portaaviones_v_fin.png"
                    };
                }

            case SUBMARINO:

                if (orientacionSeleccionada == Orientacion.HORIZONTAL) {
                    return new String[]{
                            "/barcos/submarino_h_inicio.png",
                            "/barcos/submarino_h_centro.png",
                            "/barcos/submarino_h_fin.png"
                    };
                } else {
                    return new String[]{
                            "/barcos/submarino_v_inicio.png",
                            "/barcos/submarino_v_centro.png",
                            "/barcos/submarino_v_fin.png"
                    };
                }

            case DESTRUCTOR:

                if (orientacionSeleccionada == Orientacion.HORIZONTAL) {
                    return new String[]{
                            "/barcos/destructor_h_inicio.png",
                            "/barcos/destructor_h_fin.png"
                    };
                } else {
                    return new String[]{
                            "/barcos/destructor_v_inicio.png",
                            "/barcos/destructor_v_fin.png"
                    };
                }

            case FRAGATA:

                if (orientacionSeleccionada == Orientacion.HORIZONTAL) {
                    return new String[]{
                            "/barcos/fragata_h.png"
                    };
                } else {
                    return new String[]{
                            "/barcos/fragata_v.png"
                    };
                }
        }

        return null;
    }
    @FXML
    private void cambiarOrientacion() {

        if (cmbOrientacion.getValue().equals("Horizontal")) {
            orientacionSeleccionada = Orientacion.HORIZONTAL;
        } else {
            orientacionSeleccionada = Orientacion.VERTICAL;
        }
    }
}