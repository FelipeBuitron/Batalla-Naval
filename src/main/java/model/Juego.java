package model;

public class Juego {

    private Jugador jugador;
    private Maquina maquina;

    public Juego(String nickname) {
        this.jugador = new Jugador(nickname);
        this.maquina = new Maquina();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void iniciarJuego() {

    }
}
