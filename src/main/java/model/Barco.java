package model;

public class Barco {
    private TipoBarco tipo;
    private int tamano;
    private int impactos;
    private Orientacion orientacion;

    public Barco (TipoBarco tipo, int tamano){
        this.tipo = tipo;
        this.tamano = tamano;
        this.impactos = 0;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }


    public int getImpactos() {
        return impactos;
    }

    public void setImpactos(int impactos) {
        this.impactos = impactos;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public TipoBarco getTipo() {
        return tipo;
    }

    public void setTipo(TipoBarco tipo) {
        this.tipo = tipo;
    }
}
