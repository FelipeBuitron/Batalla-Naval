package org.example.excepciones;

public class PosicionInvalidaException extends Exception {
    public PosicionInvalidaException(String mensaje) {
        super(mensaje);
    }
}