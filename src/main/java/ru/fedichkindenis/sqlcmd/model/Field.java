package ru.fedichkindenis.sqlcmd.model;

/**
 * База для поля таблицы
 */
abstract class Field {

    private String nameFiled;

    Field(String nameFiled) {
        this.nameFiled = nameFiled;
    }

    String getNameField() {
        return nameFiled;
    }

}
