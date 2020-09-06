package br.com.saudepublica.saudepublica.domain.dto.enumerator;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeUser {

    ADMIN("ADMIN", "Administrador"),
    USER("USER", "Usuário");

    private final String key;

    private final String description;

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    TypeUser(String key, String description) {
        this.key = key;
        this.description = description;
    }

    @JsonValue
    public String getValor() {
        switch (this) {
            case ADMIN:
            case USER:
                return getKey();
        }
        return null;
    }


}