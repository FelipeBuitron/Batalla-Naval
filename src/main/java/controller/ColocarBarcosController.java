package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import model.Barco;
import model.Juego;
import model.Orientacion;
import model.TipoBarco;

import java.io.IOException;

/**
 * Controlador para la vista de colocación de barcos en la fase de preparación del juego.
 * <p>
 * Permite al jugador seleccionar barcos, elegir su orientación (horizontal o vertical)
 * y posicionarlos sobre el tablero de juego antes de iniciar la partida.
 * </p>
 *
 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class ColocarBarcosController {

    /**
     * Contenedor en cuadrícula que representa el tablero del jugador en la interfaz.
     */
    @FXML
    private GridPane tableroJugador;

    /**
     * Desplegable para seleccionar la orientación del barco (Horizontal o Vertical).
     */
    @FXML
    private ComboBox<String> cmbOrientacion;

    /**
     * Instancia principal de la lógica del juego.
     */
    private Juego juego;

    /**
     * Barco actualmente seleccionado por el usuario para ser colocado.
     */
    private Barco barcoSeleccionado;

    /**
     * Matriz de botones de 10x10 que componen las casillas visuales del tablero del jugador.
     */
    private Button[][] botonesJugador;

    /**
     * Orientación seleccionada actualmente para la colocación del barco.
     */
    private Orientacion orientacionSeleccionada;

    /**
     * Referencia al botón de la interfaz que representa el barco seleccionado actualmente.
     */
    private Button botonBarcoSeleccionado;

    /**
     * Contador de barcos ubicados exitosamente en el tablero.
     */
    private int barcosColocados = 0;

    /**
     * Estilo CSS predeterminado para los botones de selección de barco.
     */
    private final String ESTILO_NORMAL = "";

    /**
     * Estilo CSS aplicado al botón del barco actualmente seleccionado.
     */
    private final String ESTILO_SELECCIONADO =
            "-fx-background-color: #FFD54F; -fx-border-color: black; -fx-border-width: 2;";

    /**
     * Inicializa la vista y sus componentes.
     * <p>
     * Se invoca automáticamente al cargar el archivo FXML. Configura la matriz
     * de botones, inicializa el juego y establece los eventos del selector de orientación.
     * </p>
     */
    @FXML
    public void initialize() {
        juego = new Juego("Felipe");
        botonesJugador = new Button[10][10];

        crearTablero(tableroJugador);

        // Configurar el ComboBox de orientación
        cmbOrientacion.getItems().addAll("Horizontal", "Vertical");
        orientacionSeleccionada = Orientacion.HORIZONTAL;

        cmbOrientacion.valueProperty().addListener((obs, anterior, nuevo) -> {
            if ("Horizontal".equals(nuevo)) {
                orientacionSeleccionada = Orientacion.HORIZONTAL;
            } else {
                orientacionSeleccionada = Orientacion.VERTICAL;
            }
        });
    }

    /**
     * Genera dinámicamente la cuadrícula de 10x10 botones en el {@link GridPane}.
     * Asigna a cada botón el evento de clic para intentar colocar el barco seleccionado.
     *
     * @param tablero El contenedor {@link GridPane} donde se construirán las casillas visuales.
     */
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

                        barcosColocados++;

                        barcoSeleccionado = null;
                        botonBarcoSeleccionado = null;
                    }
                });

                tablero.add(boton, columna, fila);
            }
        }
    }

    /**
     * Maneja el evento de selección de un tipo de barco desde los botones de la interfaz.
     * Instancia el {@link Barco} correspondiente según el texto del botón presionado.
     *
     * @param event Evento de acción disparado al presionar el botón de un barco.
     */
    @FXML
    private void seleccionarBarco(ActionEvent event) {
        // Si había un barco seleccionado previo, quitar el estilo destacado
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

    /**
     * Dibuja visualmente el barco en las casillas correspondientes del tablero,
     * asignando las imágenes del barco según su tamaño y orientación.
     *
     * @param fila    Fila de origen (coordenada inicial) donde inicia el barco.
     * @param columna Columna de origen (coordenada inicial) donde inicia el barco.
     */
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

    /**
     * Obtiene el arreglo con las rutas de las imágenes correspondientes al barco
     * actualmente seleccionado y su orientación.
     *
     * @return Arreglo de cadenas {@link String} con las rutas de los recursos de imagen.
     */
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

        return new String[0];
    }

    /**
     * Transiciona a la ventana principal del juego si todos los barcos han sido colocados.
     *
     * @param event Evento de acción disparado por el botón de iniciar partida.
     * @throws IOException Si ocurre un error al cargar el archivo FXML de la vista del juego.
     */
    @FXML
    private void iniciarPartida(ActionEvent event) throws IOException {
        if (barcosColocados != 10) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/JuegoView.fxml")
        );

        Parent root = loader.load();

        JuegoController controller = loader.getController();
        controller.setJuego(juego);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}