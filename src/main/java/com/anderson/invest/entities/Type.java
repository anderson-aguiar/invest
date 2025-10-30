package com.anderson.invest.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {

    ACOES("Ações"),
    RENDA_FIXA("Renda Fixa"),
    FUNDOS("Fundos"),
    CRYPTO("Crypto");

    public final String type;

    Type(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static Type fromValue(String value) {
        for (Type type : Type.values()) {
            if (type.type.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor de tipo inválido: " + value);
    }
}
