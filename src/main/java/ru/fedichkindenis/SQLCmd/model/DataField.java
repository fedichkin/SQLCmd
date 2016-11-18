package ru.fedichkindenis.SQLCmd.model;

import java.sql.Types;

/**
 * Класс для хранения поля таблицы
 */
public class DataField {

    private String name;
    private Object value;
    private Integer type;

    public DataField(String name, Object value, Integer type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {

        return "{name: " + name + ", value: " + value.toString() + "}";
    }
}
