package org.example.modelo;

import org.example.excepciones.PosicionInvalidaException;

public class Tablero {
    private Casilla[][] cuadricula;
    private final int FILAS = 10;
    private final int COLUMNAS = 10;

    public Tablero() {
        cuadricula = new Casilla[FILAS][COLUMNAS];
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                cuadricula[i][j] = new Casilla(i, j);
            }
        }
    }

    public Casilla getCasilla(int fila, int columna) {
        return cuadricula[fila][columna];
    }

    public void colocarBarco(Barco barco, int filaInicio, int columnaInicio, boolean esHorizontal) throws PosicionInvalidaException {
        int tamaño = barco.getTamaño();


        if (esHorizontal && columnaInicio + tamaño > COLUMNAS) {
            throw new PosicionInvalidaException("El barco se sale del tablero por la derecha.");
        }
        if (!esHorizontal && filaInicio + tamaño > FILAS) {
            throw new PosicionInvalidaException("El barco se sale del tablero por abajo.");
        }

        for (int i = 0; i < tamaño; i++) {
            int f = esHorizontal ? filaInicio : filaInicio + i;
            int c = esHorizontal ? columnaInicio + i : columnaInicio;

            if (cuadricula[f][c].tieneBarco()) {
                throw new PosicionInvalidaException("Ya hay un barco en esa posición. Hay superposición.");
            }
        }

        for (int i = 0; i < tamaño; i++) {
            int f = esHorizontal ? filaInicio : filaInicio + i;
            int c = esHorizontal ? columnaInicio + i : columnaInicio;

            cuadricula[f][c].setBarco(barco);
        }
    }
}