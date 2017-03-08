package ru.fedichkindenis.sqlcmd.model;

/**
 * Класс для хранения поля таблицы
 */
public class DataField extends Field {

    private Object valueField;

    DataField(String nameField, Object valueField) {

        super(nameField);
        this.valueField = valueField;
    }

    Object getValueField() {
        return valueField;
    }

    @Override
    public String toString() {

        return "{nameField: " + getNameField() +
                ", value: " + getValueField().toString() + "}";
    }
}
