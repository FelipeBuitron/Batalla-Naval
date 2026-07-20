package model;

public class Tablero {
    private Casilla[][] casillas;


    public Tablero () {
        casillas = new Casilla [10][10];
        for (int fila = 0; fila < 10; fila++) {
            for (int columna = 0; columna < 10; columna++) {

                casillas[fila][columna] = new Casilla(fila, columna);

            }
        }
    }
    public Casilla[][] getCasillas() {
        return casillas;
    }

    public boolean colocarBarco(Barco barco, int fila, int columna, Orientacion orientacion) {

        if (orientacion == Orientacion.HORIZONTAL) {

            if (columna + barco.getTamano() > 10) {
                return false;
            }

            // Revisar si las casillas están libres
            for (int i = 0; i < barco.getTamano(); i++) {
                if (casillas[fila][columna + i].getBarco() != null) {
                    return false;
                }
            }

            // Colocar el barco
            for (int i = 0; i < barco.getTamano(); i++) {
                casillas[fila][columna + i].setBarco(barco);
            }

        } else {

            if (fila + barco.getTamano() > 10) {
                return false;
            }

            // Revisar si las casillas están libres
            for (int i = 0; i < barco.getTamano(); i++) {
                if (casillas[fila + i][columna].getBarco() != null) {
                    return false;
                }
            }

            // Colocar el barco
            for (int i = 0; i < barco.getTamano(); i++) {
                casillas[fila + i][columna].setBarco(barco);
            }

        }

        barco.setOrientacion(orientacion);
        return true;
    }

}
