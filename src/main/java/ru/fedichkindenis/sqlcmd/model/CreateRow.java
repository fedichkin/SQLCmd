package ru.fedichkindenis.sqlcmd.model;

/**
 * Класс для хранения набора параметв создания полей
 */
public class CreateRow extends Row {

    public void add(String nameField, String typeField, boolean isNotNull) {

        CreateField createField = new CreateField(nameField, typeField, isNotNull);
        add(createField);
    }
}
