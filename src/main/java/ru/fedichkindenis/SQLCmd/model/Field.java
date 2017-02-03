package ru.fedichkindenis.SQLCmd.model;

/**
 * База для поля таблицы
 */
public abstract class Field {

    private String nameFiled;

    Field(String nameFiled) {
        this.nameFiled = nameFiled;
    }

    String getNameField() {
        return nameFiled;
    }

    @Override
    public String toString() {

        return "{nameField: " + getNameField() + "}";
    }
}
