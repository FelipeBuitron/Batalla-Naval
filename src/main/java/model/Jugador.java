package model;

import java.util.ArrayList;

public class Jugador {
    private String nickname;
    private Tablero tablero;
    private ArrayList<Barco> barcos;

    public Jugador(String nickname) {
        this.nickname = nickname;
        this. tablero = new Tablero();
        this.barcos = new ArrayList<>();

    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public ArrayList<Barco> getBarcos() {
        return barcos;
    }

    public void setBarcos(ArrayList<Barco> barcos) {
        this.barcos = barcos;
    }
}
