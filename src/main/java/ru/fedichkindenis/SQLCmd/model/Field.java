package ru.fedichkindenis.SQLCmd.model;

/**
 * База для поля таблицы
 */
public abstract class Field {

    private String nameFiled;
    private Object valueField;

    Field(String nameFiled, Object valueField) {
        this.nameFiled = nameFiled;
        this.valueField = valueField;
    }

    String getNameField() {
        return nameFiled;
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
