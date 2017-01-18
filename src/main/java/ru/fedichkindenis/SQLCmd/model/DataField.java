package ru.fedichkindenis.SQLCmd.model;

import java.sql.Types;

/**
 * Класс для хранения поля таблицы
 */
public class DataField {

    private String nameField;
    private Object value;
    private Integer type;

    public DataField(String nameField, Object value, Integer type) {
        this.nameField = nameField;
        this.value = value;
        this.type = type;
    }

    public String getNameField() {
        return nameField;
    }

    public Object getValue() {
        return value;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {

        return "{nameField: " + nameField + ", value: " + value.toString() + "}";
    }
}
