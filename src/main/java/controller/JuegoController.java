package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import model.Barco;
import model.Juego;
import model.Orientacion;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Controlador principal para la pantalla de desarrollo del juego de Batalla Naval.
 * <p>
 * Gestiona la interacción del jugador con el tablero enemigo, el turno automatizado
 * de la máquina, la renderización de las flotas y disparos, así como la persistencia
 * del estado del juego (guardar y reiniciar partida).
 * </p>
 *
 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class JuegoController {

    /**
     * Contenedor en cuadrícula para el tablero donde el jugador posiciona sus barcos.
     */
    @FXML
    private GridPane tableroJugador;

    /**
     * Contenedor en cuadrícula para el tablero donde el jugador realiza sus disparos a la máquina.
     */
    @FXML
    private GridPane tableroMaquina;

    /**
     * Etiqueta de la interfaz que indica visualmente de quién es el turno actual.
     */
    @FXML
    private Label lblTurno;

    /**
     * Instancia principal del juego que contiene la lógica y estados de ambos participantes.
     */
    private Juego juego;

    /**
     * Matriz de botones de 10x10 que representa las casillas interactuables del tablero enemigo.
     */
    private Button[][] botonesMaquina;

    /**
     * Matriz de botones de 10x10 que representa las casillas del tablero propio.
     */
    private Button[][] botonesJugador;

    /**
     * Conjunto de coordenadas ("fila-columna") previamente atacadas por la máquina para evitar repeticiones.
     */
    private Set<String> disparosMaquina = new HashSet<>();

    /**
     * Bandera que determina si actualmente es el turno del jugador humano.
     */
    private boolean turnoJugador = true;

    /**
     * Inicializa los componentes básicos de la vista.
     * <p>
     * Se invoca automáticamente al cargar la interfaz FXML. Prepara las matrices
     * de botones para ambos tableros.
     * </p>
     */
    @FXML
    public void initialize() {
        botonesJugador = new Button[10][10];
        botonesMaquina = new Button[10][10];

        crearTableroJugador();
        crearTableroMaquina();
    }

    /**
     * Construye las casillas del tablero del jugador en el {@link GridPane}.
     */
    private void crearTableroJugador() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Button boton = new Button();

                boton.setMinSize(45, 45);
                boton.setPrefSize(45, 45);
                boton.setMaxSize(45, 45);

                botonesJugador[fila][columna] = boton;
                tableroJugador.add(boton, columna, fila);
            }
        }
    }

    /**
     * Construye las casillas del tablero de la máquina y les asigna el evento de disparo.
     */
    private void crearTableroMaquina() {
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                int f = fila;
                int c = columna;

                Button boton = new Button();

                boton.setMinSize(45, 45);
                boton.setPrefSize(45, 45);
                boton.setMaxSize(45, 45);

                boton.setOnAction(e -> dispararJugador(f, c));

                botonesMaquina[fila][columna] = boton;
                tableroMaquina.add(boton, columna, fila);
            }
        }
    }

    /**
     * Procesa el disparo efectuado por el jugador humano sobre una casilla del tablero enemigo.
     * Actualiza la interfaz gráfica con el resultado (AGUA, TOCADO, HUNDIDO) y verifica victoria.
     *
     * @param fila    Fila de la casilla atacada.
     * @param columna Columna de la casilla atacada.
     */
    private void dispararJugador(int fila, int columna) {
        if (!turnoJugador) {
            return;
        }

        String resultado = juego.getMaquina()
                .getTablero()
                .disparar(fila, columna);

        switch (resultado) {
            case "AGUA":
                botonesMaquina[fila][columna].setGraphic(crearIcono("agua.png"));
                break;

            case "TOCADO":
                botonesMaquina[fila][columna].setGraphic(crearIcono("tocado.png"));
                break;

            case "HUNDIDO":
                botonesMaquina[fila][columna].setGraphic(crearIcono("hundido.png"));
                break;
        }

        if (juego.ganoJugador()) {
            juego.finalizarJuego();
            turnoJugador = false;

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Fin del juego");
            alerta.setHeaderText(null);
            alerta.setContentText("¡Felicidades! ¡Ganaste la partida!");
            alerta.showAndWait();
            return;
        }

        // Si fue agua, se le cede el turno a la máquina
        if ("AGUA".equals(resultado)) {
            esperarYJugarMaquina();
        }
    }

    /**
     * Ejecuta de forma aleatoria y no repetida la lógica de disparo de la máquina sobre el tablero del jugador.
     * Si la máquina impacta un barco, vuelve a disparar tras un intervalo de tiempo.
     */
    private void turnoMaquina() {
        int fila;
        int columna;

        do {
            fila = (int) (Math.random() * 10);
            columna = (int) (Math.random() * 10);
        } while (disparosMaquina.contains(fila + "-" + columna));

        disparosMaquina.add(fila + "-" + columna);

        String resultado = juego.getJugador()
                .getTablero()
                .disparar(fila, columna);

        switch (resultado) {
            case "AGUA":
                botonesJugador[fila][columna].setGraphic(crearIcono("agua.png"));
                break;

            case "TOCADO":
                botonesJugador[fila][columna].setStyle("-fx-background-color: orange;");
                break;

            case "HUNDIDO":
                botonesJugador[fila][columna].setStyle("-fx-background-color: red;");
                break;
        }

        if (juego.ganoMaquina()) {
            juego.finalizarJuego();
            turnoJugador = false;

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Fin del juego");
            alerta.setHeaderText(null);
            alerta.setContentText("La máquina ganó la partida.");
            alerta.showAndWait();
            return;
        }

        // Si la máquina falla, el turno vuelve al jugador humano
        if ("AGUA".equals(resultado)) {
            turnoJugador = true;
            actualizarTurno();
        } else {
            // Si acertó, espera 2 segundos y vuelve a atacar
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Platform.runLater(this::turnoMaquina);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Establece e inicializa la instancia de {@link Juego} activa para esta vista.
     * Redibuja los tableros y muestra la flota del jugador.
     *
     * @param juego Instancia del modelo del juego con los datos de la partida.
     */
    public void setJuego(Juego juego) {
        this.juego = juego;

        tableroJugador.getChildren().clear();
        tableroMaquina.getChildren().clear();

        botonesJugador = new Button[10][10];
        botonesMaquina = new Button[10][10];

        crearTableroJugador();
        crearTableroMaquina();

        mostrarBarcosJugador();
        turnoJugador = true;
        actualizarTurno();
    }

    /**
     * Recorre el tablero del jugador para identificar y posicionar gráficamente las imágenes de sus barcos.
     */
    private void mostrarBarcosJugador() {
        Set<Barco> barcosDibujados = new HashSet<>();

        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {
                Barco barco = juego.getJugador()
                        .getTablero()
                        .getCasillas()[fila][columna]
                        .getBarco();

                if (barco != null && !barcosDibujados.contains(barco)) {
                    barcosDibujados.add(barco);
                    dibujarBarco(barco);
                }
            }
        }
    }

    /**
     * Renderiza las partes individuales de un barco en las casillas correspondientes de la interfaz.
     *
     * @param barco Objeto {@link Barco} a ser graficado.
     */
    private void dibujarBarco(Barco barco) {
        int fila = barco.getFilaInicial();
        int columna = barco.getColumnaInicial();

        for (int i = 0; i < barco.getTamano(); i++) {
            int f = fila;
            int c = columna;

            if (barco.getOrientacion() == Orientacion.HORIZONTAL) {
                c += i;
            } else {
                f += i;
            }

            Button boton = botonesJugador[f][c];
            boton.setGraphic(crearImagenBarco(barco, i));
            boton.setStyle("-fx-padding:0;");
            boton.setText("");
        }
    }

    /**
     * Crea un objeto {@link ImageView} correspondiente a la sección y tipo de un barco determinado.
     *
     * @param barco Objeto {@link Barco} analizado.
     * @param parte Índice del segmento del barco (0 para la proa/inicio, etc.).
     * @return Una vista de imagen ajustada para la casilla.
     */
    private ImageView crearImagenBarco(Barco barco, int parte) {
        String ruta = "";

        switch (barco.getTipo()) {
            case FRAGATA:
                if (barco.getOrientacion() == Orientacion.HORIZONTAL) {
                    ruta = "/barcos/fragata_h.png";
                } else {
                    ruta = "/barcos/fragata_v.png";
                }
                break;

            case DESTRUCTOR:
                if (barco.getOrientacion() == Orientacion.HORIZONTAL) {
                    ruta = (parte == 0) ? "/barcos/destructor_h_inicio.png" : "/barcos/destructor_h_fin.png";
                } else {
                    ruta = (parte == 0) ? "/barcos/destructor_v_inicio.png" : "/barcos/destructor_v_fin.png";
                }
                break;

            case SUBMARINO:
                if (barco.getOrientacion() == Orientacion.HORIZONTAL) {
                    if (parte == 0) ruta = "/barcos/submarino_h_inicio.png";
                    else if (parte == 1) ruta = "/barcos/submarino_h_centro.png";
                    else ruta = "/barcos/submarino_h_fin.png";
                } else {
                    if (parte == 0) ruta = "/barcos/submarino_v_inicio.png";
                    else if (parte == 1) ruta = "/barcos/submarino_v_centro.png";
                    else ruta = "/barcos/submarino_v_fin.png";
                }
                break;

            case PORTAAVIONES:
                if (barco.getOrientacion() == Orientacion.HORIZONTAL) {
                    if (parte == 0) ruta = "/barcos/portaaviones_h_inicio.png";
                    else if (parte == 1) ruta = "/barcos/portaaviones_h_centro1.png";
                    else if (parte == 2) ruta = "/barcos/portaaviones_h_centro2.png";
                    else ruta = "/barcos/portaaviones_h_fin.png";
                } else {
                    if (parte == 0) ruta = "/barcos/portaaviones_v_inicio.png";
                    else if (parte == 1) ruta = "/barcos/portaaviones_v_centro1.png";
                    else if (parte == 2) ruta = "/barcos/portaaviones_v_centro2.png";
                    else ruta = "/barcos/portaaviones_v_fin.png";
                }
                break;
        }

        ImageView imagen = new ImageView(new Image(getClass().getResourceAsStream(ruta)));
        imagen.setFitWidth(40);
        imagen.setFitHeight(40);
        imagen.setPreserveRatio(false);

        return imagen;
    }

    /**
     * Genera un icono genérico ajustado a partir del nombre del archivo en el directorio de imágenes.
     *
     * @param nombreImagen Nombre del archivo de imagen (ej. "agua.png").
     * @return Objeto {@link ImageView} configurado.
     */
    private ImageView crearIcono(String nombreImagen) {
        Image imagen = new Image(getClass().getResourceAsStream("/Img/" + nombreImagen));
        ImageView icono = new ImageView(imagen);

        icono.setFitWidth(25);
        icono.setFitHeight(25);
        icono.setPreserveRatio(true);
        icono.setSmooth(true);

        return icono;
    }

    /**
     * Guarda el estado actual de la partida en el archivo local {@code partida.dat}.
     * Muestra una alerta informando el resultado del proceso.
     */
    @FXML
    private void guardarPartida() {
        try (FileOutputStream archivo = new FileOutputStream("partida.dat");
             ObjectOutputStream salida = new ObjectOutputStream(archivo)) {

            salida.writeObject(juego);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Guardar partida");
            alerta.setHeaderText(null);
            alerta.setContentText("La partida se guardó correctamente.");
            alerta.showAndWait();

        } catch (IOException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("No fue posible guardar la partida.");
            alerta.showAndWait();

            e.printStackTrace();
        }
    }

    /**
     * Cancela o reinicia la vista redirigiendo al usuario a la colocación de barcos.
     *
     * @param event Evento de acción provocado por el botón correspondiente.
     * @throws IOException Si ocurre un error al cargar la vista {@code ColocarBarcosView.fxml}.
     */
    @FXML
    private void otroJuegoNuevo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ColocarBarcosView.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Pausa temporalmente la interacción, transfiere el turno a la máquina y la ejecuta en un hilo secundario.
     */
    private void esperarYJugarMaquina() {
        turnoJugador = false;
        actualizarTurno();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(this::turnoMaquina);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Actualiza el texto y estilo visual de la etiqueta de turno según el jugador activo.
     */
    private void actualizarTurno() {
        if (turnoJugador) {
            lblTurno.setText("Turno tu flota");
            lblTurno.setStyle("-fx-text-fill: white; -fx-font-size: 32px; -fx-font-weight: bold;");
        } else {
            lblTurno.setText("Turno del Enemigo");
            lblTurno.setStyle("-fx-text-fill: #a9bffc; -fx-font-size: 32px; -fx-font-weight: bold;");
        }
    }
}