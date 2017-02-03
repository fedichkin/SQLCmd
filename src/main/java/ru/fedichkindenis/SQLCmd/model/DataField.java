package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения поля таблицы
 */
public class DataField extends Field {

    private Object valueField;

    DataField(String nameField, Object valueField) {

        super(nameField);
        this.valueField = valueField;
    }

    public Object getValueField() {
        return valueField;
    }

    @Override
    public String toString() {

        return "{nameField: " + getNameField() +
                ", value: " + getValueField().toString() + "}";
    }
}
