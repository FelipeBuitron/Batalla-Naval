package model;

import java.io.Serializable;

/**
 * Representa un barco dentro del juego de Batalla Naval.
 * <p>
 * Un barco se define por su tipo, su tamaño en casillas, la cantidad de impactos
 * recibidos, su orientación en el tablero y su posición inicial (coordenadas de origen).
 * Implementa {@link Serializable} para permitir la persistencia del estado de la partida.
 * </p>
 *
 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class Barco implements Serializable {

    /**
     * Identificador único de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tipo de barco (por ejemplo, FRAGATA, DESTRUCTOR, SUBMARINO, PORTAAVIONES).
     */
    private TipoBarco tipo;

    /**
     * Número de casillas que ocupa el barco en el tablero.
     */
    private int tamano;

    /**
     * Número de veces que el barco ha sido impactado por disparos enemigos.
     */
    private int impactos;

    /**
     * Orientación espacial del barco en el tablero (HORIZONTAL o VERTICAL).
     */
    private Orientacion orientacion;

    /**
     * Coordenada inicial correspondiente a la fila donde comienza el barco.
     */
    private int filaInicial;

    /**
     * Coordenada inicial correspondiente a la columna donde comienza el barco.
     */
    private int columnaInicial;

    /**
     * Construye un nuevo barco especificado por su tipo y tamaño.
     * Inicializa el contador de impactos en cero.
     *
     * @param tipo   Tipo de barco según la enumeración {@link TipoBarco}.
     * @param tamano Longitud o número de casillas que ocupa el barco.
     */
    public Barco(TipoBarco tipo, int tamano) {
        this.tipo = tipo;
        this.tamano = tamano;
        this.impactos = 0;
    }

    /**
     * Incrementa en uno el contador de impactos recibidos por el barco.
     */
    public void recibirImpacto() {
        impactos++;
    }

    /**
     * Evalúa si el barco ha sido completamente destruido.
     *
     * @return {@code true} si la cantidad de impactos recibidos es igual o mayor a su tamaño; {@code false} en caso contrario.
     */
    public boolean estaHundido() {
        return impactos >= tamano;
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

    /**
     * Obtiene el tipo de barco.
     *
     * @return Tipo de barco actual.
     */
    public TipoBarco getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de barco.
     *
     * @param tipo Nuevo tipo de barco.
     */
    public void setTipo(TipoBarco tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el tamaño del barco.
     *
     * @return Número de casillas que ocupa el barco.
     */
    public int getTamano() {
        return tamano;
    }

    /**
     * Establece el tamaño del barco.
     *
     * @param tamano Nuevo tamaño del barco.
     */
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    /**
     * Obtiene la cantidad de impactos recibidos.
     *
     * @return Número de impactos actuales.
     */
    public int getImpactos() {
        return impactos;
    }

    /**
     * Establece manualmente la cantidad de impactos recibidos.
     *
     * @param impactos Número de impactos a asignar.
     */
    public void setImpactos(int impactos) {
        this.impactos = impactos;
    }

    /**
     * Obtiene la orientación del barco en el tablero.
     *
     * @return Orientación actual del barco.
     */
    public Orientacion getOrientacion() {
        return orientacion;
    }

    /**
     * Establece la orientación del barco en el tablero.
     *
     * @param orientacion Nueva orientación del barco.
     */
    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    /**
     * Obtiene la fila inicial donde se ubica el origen del barco.
     *
     * @return Índice de la fila inicial.
     */
    public int getFilaInicial() {
        return filaInicial;
    }

    /**
     * Establece la fila inicial donde se ubica el origen del barco.
     *
     * @param filaInicial Índice de la fila inicial.
     */
    public void setFilaInicial(int filaInicial) {
        this.filaInicial = filaInicial;
    }

    /**
     * Obtiene la columna inicial donde se ubica el origen del barco.
     *
     * @return Índice de la columna inicial.
     */
    public int getColumnaInicial() {
        return columnaInicial;
    }

    /**
     * Establece la columna inicial donde se ubica el origen del barco.
     *
     * @param columnaInicial Índice de la columna inicial.
     */
    public void setColumnaInicial(int columnaInicial) {
        this.columnaInicial = columnaInicial;
    }
}