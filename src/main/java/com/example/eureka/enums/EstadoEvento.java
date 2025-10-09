package com.example.eureka.enums;

public enum EstadoEvento {
    PROGRAMADO("programado"),
    CANCELADO("cancelado"),
    TERMINADO("terminado");

    private final String valor;

    EstadoEvento(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static EstadoEvento fromValor(String valor) {
        for (EstadoEvento estado : EstadoEvento.values()) {
            if (estado.valor.equalsIgnoreCase(valor)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de evento no válido: " + valor);
    }
}