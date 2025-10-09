package com.example.eureka.enums;

public enum TipoEvento {
    PRESENCIAL("presencial"),
    VIRTUAL("virtual");

    private final String valor;

    TipoEvento(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoEvento fromValor(String valor) {
        for (TipoEvento tipo : TipoEvento.values()) {
            if (tipo.valor.equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de evento no válido: " + valor);
    }
}
