package model;

import java.io.Serializable;

/**
 * Representa una casilla individual dentro del tablero del juego de Batalla Naval.
 * <p>
 * Cada casilla almacena su posición (fila y columna), su estado actual (por ejemplo,
 * VACIA, BARCO, AGUA, TOCADO, HUNDIDO), la referencia al {@link Barco} que pueda estar
 * alojado en ella y una bandera que indica si ya ha sido atacada.
 * Implementa {@link Serializable} para permitir la persistencia del tablero.
 * </p>

 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class Casilla implements Serializable {

    /**
     * Identificador único de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Coordenada de la fila donde se ubica la casilla en la matriz del tablero.
     */
    private int fila;

    /**
     * Coordenada de la columna donde se ubica la casilla en la matriz del tablero.
     */
    private int columna;

    /**
     * Estado actual de la casilla representado por la enumeración {@link EstadoCasilla}.
     */
    private EstadoCasilla estado;

    /**
     * Referencia al barco posicionado sobre esta casilla. Es {@code null} si la casilla está vacía.
     */
    private Barco barco;

    /**
     * Indica si la casilla ya ha sido objetivo de un disparo durante la partida.
     */
    private boolean disparada;

    /**
     * Construye una nueva casilla en las coordenadas especificadas.
     * Por defecto, nace vacía, sin barco asignado y sin ser disparada.
     *
     * @param fila    Coordenada de la fila.
     * @param columna Coordenada de la columna.
     */
    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.estado = EstadoCasilla.VACIA;
        this.barco = null;
        this.disparada = false;
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

    /**
     * Obtiene el índice de la fila donde está ubicada la casilla.
     *
     * @return Número de fila.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Establece el índice de la fila de la casilla.
     *
     * @param fila Número de fila a asignar.
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * Obtiene el índice de la columna donde está ubicada la casilla.
     *
     * @return Número de columna.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Establece el índice de la columna de la casilla.
     *
     * @param columna Número de columna a asignar.
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * Obtiene el estado actual de la casilla.
     *
     * @return Estado según la enumeración {@link EstadoCasilla}.
     */
    public EstadoCasilla getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la casilla.
     *
     * @param estado Nuevo estado a asignar.
     */
    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la referencia al barco que ocupa esta casilla.
     *
     * @return Objeto {@link Barco} alojado, o {@code null} si no hay ninguno.
     */
    public Barco getBarco() {
        return barco;
    }

    /**
     * Asigna un barco a esta casilla.
     *
     * @param barco Objeto {@link Barco} que ocupará la casilla.
     */
    public void setBarco(Barco barco) {
        this.barco = barco;
    }

    /**
     * Consulta si la casilla ya recibió un disparo.
     *
     * @return {@code true} si la casilla ya fue disparada; {@code false} en caso contrario.
     */
    public boolean isDisparada() {
        return disparada;
    }

    /**
     * Establece el estado de disparo de la casilla.
     *
     * @param disparada {@code true} para marcar que la casilla ya fue atacada.
     */
    public void setDisparada(boolean disparada) {
        this.disparada = disparada;
    }
}