package br.com.saudepublica.saudepublica.domain.dto.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StockStatus {

    SAVED("SAVED", "Salvo"),
    FINALIZED("FINALIZED", "Finalizado");

    private final String key;

    private final String description;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    StockStatus(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @JsonValue
    public String getValor() {
        switch (this) {
            case SAVED:
            case FINALIZED:
                return getKey();
        }
        return null;
    }


}