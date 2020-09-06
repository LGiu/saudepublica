package br.com.saudepublica.saudepublica.domain.dto.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeStockOutput {

    DISPEN("DISPEN", "Dispensa"),
    TRANS("TRANS", "Transferência");

    private final String key;

    private final String description;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    TypeStockOutput(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @JsonValue
    public String getValor() {
        switch (this) {
            case DISPEN:
            case TRANS:
                return getKey();
        }
        return null;
    }


}