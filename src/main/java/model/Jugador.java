package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa al jugador humano dentro del juego de Batalla Naval.
 * <p>
 * Cada jugador posee un apodo o nickname, su propio {@link Tablero} de juego
 * y una lista de objetos {@link Barco} que integran su flota.
 * Implementa {@link Serializable} para hacer posible la persistencia de los datos.
 * </p>
 *
 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class Jugador implements Serializable {

    /**
     * Identificador único de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Apodo o nombre que identifica al jugador.
     */
    private String nickname;

    /**
     * Tablero propio del jugador donde posiciona sus barcos y recibe los ataques del enemigo.
     */
    private Tablero tablero;

    /**
     * Lista que contiene los barcos pertenecientes a la flota del jugador.
     */
    private List<Barco> barcos;

    /**
     * Construye un nuevo jugador asignándole un apodo e inicializando su tablero
     * y su lista de barcos vacía.
     *
     * @param nickname Nombre o apodo para identificar al jugador.
     */
    public Jugador(String nickname) {
        this.nickname = nickname;
        this.tablero = new Tablero();
        this.barcos = new ArrayList<>();
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

    /**
     * Obtiene el apodo del jugador.
     *
     * @return Cadena con el nickname actual.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Establece el apodo del jugador.
     *
     * @param nickname Nuevo nickname a asignar.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Obtiene el tablero asociado al jugador.
     *
     * @return Objeto {@link Tablero} del jugador.
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Asigna un nuevo tablero al jugador.
     *
     * @param tablero Nuevo {@link Tablero} a asignar.
     */
    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Obtiene la lista de barcos pertenecientes a la flota del jugador.
     *
     * @return Lista de objetos {@link Barco}.
     */
    public List<Barco> getBarcos() {
        return barcos;
    }

    /**
     * Asigna la lista de barcos de la flota del jugador.
     *
     * @param barcos Nueva lista de objetos {@link Barco}.
     */
    public void setBarcos(List<Barco> barcos) {
        this.barcos = barcos;
    }
}