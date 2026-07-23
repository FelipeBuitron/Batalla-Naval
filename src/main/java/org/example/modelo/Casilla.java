package org.example.modelo;

public class Casilla {
    private int fila;
    private int columna;
    private boolean disparada;
    private Barco barco;

    public Casilla(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.disparada = false;
        this.barco = null;
    }


    public void setBarco(Barco barco) {
        this.barco = barco;
    }


    public boolean tieneBarco() {
        return this.barco != null;
    }

    public boolean isDisparada() {
        return disparada;
    }

    public void registrarDisparo() {
        this.disparada = true;
    }
}