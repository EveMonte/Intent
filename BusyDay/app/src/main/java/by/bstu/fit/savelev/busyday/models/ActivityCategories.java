package by.bstu.fit.savelev.busyday.models;

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

    public String getValue() {
        return value;
    }
}
