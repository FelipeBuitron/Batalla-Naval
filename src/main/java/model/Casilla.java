package model;


public class Casilla {
    private int fila;
    private int columna;
    private EstadoCasilla estado;
    private Barco barco;

    public Casilla (int fila, int columna){
        this.fila =fila;
        this.columna = columna;
        this.estado =EstadoCasilla.VACIA;
        this.barco = null;


    }

    public int getFila() {
        return fila;
    }

    public Barco getBarco() {
        return barco;
    }

    public void setBarco(Barco barco) {
        this.barco = barco;
    }



    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public EstadoCasilla getEstado() {
        return estado;
    }

    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }
}
