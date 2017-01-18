package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения строки таблицы
 */
public class DataRow extends Row {

    public void add(String nameField, Object field) {

        DataField dataField = new DataField(nameField, field);
        add(dataField);
    }
}
