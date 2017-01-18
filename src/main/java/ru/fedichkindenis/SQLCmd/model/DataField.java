package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения поля таблицы
 */
public class DataField extends Field {

    DataField(String nameField, Object valueField) {

        super(nameField, valueField);
    }

    @Override
    public String toString() {

        return "{nameField: " + getNameField() +
                ", value: " + getValueField().toString() + "}";
    }
}
