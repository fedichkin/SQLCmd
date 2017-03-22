package ru.fedichkindenis.sqlcmd.model;

/**
 * База для поля таблицы
 */
public abstract class Field {

    private String nameFiled;

    Field(String nameFiled) {
        this.nameFiled = nameFiled;
    }

    public String getNameField() {
        return nameFiled;
    }

}
