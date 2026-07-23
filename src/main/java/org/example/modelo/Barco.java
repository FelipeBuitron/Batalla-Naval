package org.example.modelo;

public class Barco {
    private String tipo;
    private int tamaño;
    private int impactosRecibidos;

    public Barco(String tipo, int tamaño) {
        this.tipo = tipo;
        this.tamaño = tamaño;
        this.impactosRecibidos = 0;
    }

    public void recibirImpacto() {
        this.impactosRecibidos++;
    }

    public boolean isHundido() {
        return impactosRecibidos >= tamaño;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTamaño() {
        return tamaño;
    }
}