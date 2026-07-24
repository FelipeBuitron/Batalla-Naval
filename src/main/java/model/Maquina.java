package model;

import java.io.Serializable;
import java.util.Random;

/**
 * Representa al oponente controlado por la inteligencia artificial / computadora.
 * <p>
 * Hereda de {@link Jugador} e incluye la lógica necesaria para posicionar de forma
 * aleatoria e idónea la flota completa de barcos al momento de su creación.
 * </p>
 *
 * @author Andres Felipe Escobar
 * @author Carlos Delgado
 * @version 1.0
 */
public class Maquina extends Jugador implements Serializable {

    /**
     * Identificador único de versión para la serialización.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Generador de números aleatorios para determinar la ubicación y orientación de los barcos.
     */
    private final Random random;

    /**
     * Construye una nueva instancia de la máquina con el apodo "Computador"
     * y posiciona automáticamente su flota sobre el tablero.
     */
    public Maquina() {
        super("Computador");
        this.random = new Random();
        colocarBarcosAutomaticamente();
    }

    /**
     * Instancia y ubica automáticamente toda la flota reglamentaria en el tablero.
     * <p>
     * La flota consta de:
     * <ul>
     *   <li>1 Portaaviones (tamaño 4)</li>
     *   <li>2 Submarinos (tamaño 3)</li>
     *   <li>3 Destructores (tamaño 2)</li>
     *   <li>4 Fragatas (tamaño 1)</li>
     * </ul>
     * </p>
     */
    private void colocarBarcosAutomaticamente() {
        // 1 Portaaviones de tamaño 4
        colocarBarco(new Barco(TipoBarco.PORTAAVIONES, 4));

        // 2 Submarinos de tamaño 3
        colocarBarco(new Barco(TipoBarco.SUBMARINO, 3));
        colocarBarco(new Barco(TipoBarco.SUBMARINO, 3));

        // 3 Destructores de tamaño 2
        colocarBarco(new Barco(TipoBarco.DESTRUCTOR, 2));
        colocarBarco(new Barco(TipoBarco.DESTRUCTOR, 2));
        colocarBarco(new Barco(TipoBarco.DESTRUCTOR, 2));

        // 4 Fragatas de tamaño 1
        colocarBarco(new Barco(TipoBarco.FRAGATA, 1));
        colocarBarco(new Barco(TipoBarco.FRAGATA, 1));
        colocarBarco(new Barco(TipoBarco.FRAGATA, 1));
        colocarBarco(new Barco(TipoBarco.FRAGATA, 1));
    }

    /**
     * Intenta posicionar un barco en el tablero de forma aleatoria.
     * <p>
     * Genera coordenadas y orientaciones al azar repetidamente hasta encontrar
     * una posición válida dentro de los límites y sin colisionar con otros barcos.
     * Una vez ubicado con éxito, lo agrega a la lista de barcos de la máquina.
     * </p>
     *
     * @param barco Objeto {@link Barco} que se desea ubicar en el tablero.
     */
    private void colocarBarco(Barco barco) {
        boolean colocado = false;

        while (!colocado) {
            int fila = random.nextInt(10);
            int columna = random.nextInt(10);
            Orientacion orientacion = random.nextBoolean() ? Orientacion.HORIZONTAL : Orientacion.VERTICAL;

            colocado = getTablero().colocarBarco(barco, fila, columna, orientacion);
        }

        getBarcos().add(barco);
    }
}