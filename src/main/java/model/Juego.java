package model;

import java.io.Serializable;

/**
 * Representa la sesión o partida activa del juego de Batalla Naval.
 * <p>
 * Se encarga de administrar y coordinar a los dos participantes (el {@link Jugador} humano
 * y la {@link Maquina}), gestionar el control de turnos y validar las condiciones de
 * fin de juego o victoria. Implementa {@link Serializable} para hacer posible el guardado
 * y carga completa de la partida.
 * </p>

 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class Juego implements Serializable {

    /**
     * Identificador único de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Instancia que representa al jugador humano de la partida.
     */
    private Jugador jugador;

    /**
     * Instancia que representa al oponente controlado por la computadora.
     */
    private Maquina maquina;

    /**
     * Indica si el turno actual pertenece al jugador humano.
     */
    private boolean turnoJugador;

    /**
     * Indica si la partida ya ha concluido.
     */
    private boolean juegoTerminado;

    /**
     * Construye e inicializa una nueva partida de Batalla Naval.
     * Crea las instancias del jugador con su apodo o nickname y la máquina con sus
     * barcos ubicados por defecto.
     *
     * @param nickname Nombre o apodo asignado al jugador humano.
     */
    public Juego(String nickname) {
        this.jugador = new Jugador(nickname);
        this.maquina = new Maquina();
        this.turnoJugador = true;
        this.juegoTerminado = false;
    }

    /**
     * Reinicia las banderas de control para comenzar el juego.
     * Establece el turno inicial para el jugador humano y marca la partida como activa.
     */
    public void iniciarJuego() {
        turnoJugador = true;
        juegoTerminado = false;
    }

    /**
     * Alterna el turno actual entre el jugador humano y la máquina.
     */
    public void cambiarTurno() {
        turnoJugador = !turnoJugador;
    }

    /**
     * Marca la partida como finalizada.
     */
    public void finalizarJuego() {
        juegoTerminado = true;
    }

    /**
     * Evalúa si el jugador humano ha ganado la partida.
     *
     * @return {@code true} si todos los barcos del tablero de la máquina han sido hundidos; {@code false} en caso contrario.
     */
    public boolean ganoJugador() {
        return maquina.getTablero().todosLosBarcosHundidos();
    }

    /**
     * Evalúa si la máquina ha ganado la partida.
     *
     * @return {@code true} si todos los barcos del tablero del jugador humano han sido hundidos; {@code false} en caso contrario.
     */
    public boolean ganoMaquina() {
        return jugador.getTablero().todosLosBarcosHundidos();
    }

    // =========================================================================
    // GETTERS Y SETTERS
    // =========================================================================

    /**
     * Obtiene el jugador humano asociado a esta partida.
     *
     * @return Instancia de {@link Jugador}.
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Obtiene la máquina oponente asociada a esta partida.
     *
     * @return Instancia de {@link Maquina}.
     */
    public Maquina getMaquina() {
        return maquina;
    }

    /**
     * Consulta si actualmente es el turno del jugador humano.
     *
     * @return {@code true} si es el turno del jugador; {@code false} si es de la máquina.
     */
    public boolean isTurnoJugador() {
        return turnoJugador;
    }

    /**
     * Consulta si la partida ha finalizado.
     *
     * @return {@code true} si la partida ya terminó; {@code false} si continúa en curso.
     */
    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}