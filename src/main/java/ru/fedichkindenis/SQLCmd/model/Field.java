package ru.fedichkindenis.SQLCmd.model;

/**
 * База для поля таблицы
 */
public abstract class Field {

    private String nameFiled;
    private Object valueField;

    public Field(String nameFiled, Object valueField) {
        this.nameFiled = nameFiled;
        this.valueField = valueField;
    }

    public String getNameField() {
        return nameFiled;
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
