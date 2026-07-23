package org.example.modelo;

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
}