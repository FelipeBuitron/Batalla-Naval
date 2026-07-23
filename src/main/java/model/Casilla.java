package org.example.modelo;

public class Casilla {
    private int fila;
    private int columna;
    private boolean disparada;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.disparada = false;
    }

    public boolean isDisparada() {
        return disparada;
    }

    public void registrarDisparo() {
        this.disparada = true;
    }
}