package by.bstu.fit.savelev.busyday.models;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivityCategories {
    UNIVERSITY("Университет"),
    STUDYING("Учёба"),
    SLEEPING("Сон"),
    ENTERTAINING("Развлечение"),
    OUTDOOR("Прогулка"),
    SHOPPING("Шоппинг");
    private String value;

    ActivityCategories(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
