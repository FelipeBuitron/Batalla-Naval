package model;

import java.io.Serializable;

/**
 * Representa el tablero de juego de 10x10 casillas en la Batalla Naval.
 * <p>
 * Se encarga de gestionar la matriz de {@link Casilla}, controlar la ubicación
 * válida de los objetos {@link Barco}, procesar los disparos recibidos y determinar
 * el estado de la flota (si quedan barcos a flote o están todos hundidos).
 * Implementa {@link Serializable} para la persistencia del estado de juego.
 * </p>
 *
 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class Tablero implements Serializable {

    /**
     * Identificador único de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Número constante de filas del tablero.
     */
    public static final int FILAS = 10;

    /**
     * Número constante de columnas del tablero.
     */
    public static final int COLUMNAS = 10;

    /**
     * Matriz bidimensional que contiene las casillas del tablero.
     */
    private Casilla[][] casillas;

    /**
     * Construye un nuevo tablero inicializando la matriz de 10x10
     * con instancias individuales de {@link Casilla}.
     */
    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                casillas[fila][columna] = new Casilla(fila, columna);
            }
        }
    }

    /**
     * Intenta colocar un barco en el tablero dada una posición inicial y una orientación.
     * <p>
     * Verifica que el barco no exceda las dimensiones del tablero y que las casillas
     * requeridas estén completamente libres antes de asignarlo.
     * </p>
     *
     * @param barco       Objeto {@link Barco} a ubicar.
     * @param fila        Fila de origen.
     * @param columna     Columna de origen.
     * @param orientacion Orientación (HORIZONTAL o VERTICAL) del barco.
     * @return {@code true} si se colocó exitosamente; {@code false} si la posición no es válida o está ocupada.
     */
    public boolean colocarBarco(Barco barco, int fila, int columna, Orientacion orientacion) {
        if (orientacion == Orientacion.HORIZONTAL) {
            if (columna + barco.getTamano() > COLUMNAS) {
                return false;
            }

            // Revisar si las casillas están libres
            for (int i = 0; i < barco.getTamano(); i++) {
                if (casillas[fila][columna + i].getBarco() != null) {
                    return false;
                }
            }

            // Colocar el barco
            for (int i = 0; i < barco.getTamano(); i++) {
                casillas[fila][columna + i].setBarco(barco);
            }

        } else {
            if (fila + barco.getTamano() > FILAS) {
                return false;
            }

            // Revisar si las casillas están libres
            for (int i = 0; i < barco.getTamano(); i++) {
                if (casillas[fila + i][columna].getBarco() != null) {
                    return false;
                }
            }

            // Colocar el barco
            for (int i = 0; i < barco.getTamano(); i++) {
                casillas[fila + i][columna].setBarco(barco);
            }
        }

        barco.setFilaInicial(fila);
        barco.setColumnaInicial(columna);
        barco.setOrientacion(orientacion);
        return true;
    }

    /**
     * Procesa la acción de disparar a una casilla específica del tablero.
     * <p>
     * Si la casilla ya fue disparada previamente, retorna "REPETIDO". Si no hay barco,
     * cambia el estado a {@code AGUA}. Si impacta un barco, registra el daño y actualiza
     * el estado a {@code TOCADO} o {@code HUNDIDO} según corresponda. Si el barco se hunde,
     * marca todas las casillas del mismo como {@code HUNDIDO}.
     * </p>
     *
     * @param fila    Índice de la fila objetivo.
     * @param columna Índice de la columna objetivo.
     * @return Resultado del disparo: "REPETIDO", "AGUA", "TOCADO" o "HUNDIDO".
     */
    public String disparar(int fila, int columna) {
        Casilla casilla = casillas[fila][columna];

        if (casilla.isDisparada()) {
            return "REPETIDO";
        }

        casilla.setDisparada(true);

        if (casilla.getBarco() == null) {
            casilla.setEstado(EstadoCasilla.AGUA);
            return "AGUA";
        }

        Barco barco = casilla.getBarco();
        barco.recibirImpacto();

        if (barco.estaHundido()) {
            marcarBarcoComoHundido(barco);
            return "HUNDIDO";
        }

        casilla.setEstado(EstadoCasilla.TOCADO);
        return "TOCADO";
    }

    /**
     * Actualiza el estado de todas las casillas ocupadas por un barco a {@link EstadoCasilla#HUNDIDO}.
     *
     * @param barco Objeto {@link Barco} que ha sido completamente destruido.
     */
    private void marcarBarcoComoHundido(Barco barco) {
        int f = barco.getFilaInicial();
        int c = barco.getColumnaInicial();

        for (int i = 0; i < barco.getTamano(); i++) {
            if (barco.getOrientacion() == Orientacion.HORIZONTAL) {
                casillas[f][c + i].setEstado(EstadoCasilla.HUNDIDO);
            } else {
                casillas[f + i][c].setEstado(EstadoCasilla.HUNDIDO);
            }
        }
    }

    /**
     * Evalúa si todos los barcos colocados en este tablero han sido hundidos.
     *
     * @return {@code true} si no queda ningún barco a flote; {@code false} si aún hay barcos operativos.
     */
    public boolean todosLosBarcosHundidos() {
        for (int fila = 0; fila < FILAS; fila++) {
            for (int columna = 0; columna < COLUMNAS; columna++) {
                Barco barco = casillas[fila][columna].getBarco();
                if (barco != null && !barco.estaHundido()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Consulta si la casilla en las coordenadas especificadas ya ha sido atacada.
     *
     * @param fila    Índice de la fila.
     * @param columna Índice de la columna.
     * @return {@code true} si la casilla ya fue disparada; {@code false} de lo contrario.
     */
    public boolean yaDisparo(int fila, int columna) {
        return casillas[fila][columna].isDisparada();
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

    /**
     * Obtiene la matriz bidimensional de casillas del tablero.
     *
     * @return Matriz de objetos {@link Casilla}.
     */
    public Casilla[][] getCasillas() {
        return casillas;
    }
}