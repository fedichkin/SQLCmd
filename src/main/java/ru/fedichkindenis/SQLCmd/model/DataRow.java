package ru.fedichkindenis.SQLCmd.model;

import java.util.*;

/**
 * Класс для хранения строки таблицы
 */
public class DataRow {

    private Map<String, Object> dataMap = new LinkedHashMap<>();

    public void add(String nameField, Object field) {

        dataMap.put(nameField, field);
    }

    public Collection<String> getListNameField() {

        return dataMap.keySet();
    }

    public Collection<Object> getListValueField() {

        return dataMap.values();
    }

    public int getCountField() {

        return dataMap.size();
    }

    public Object getValueByName(String nameField) {

        return dataMap.get(nameField);
    }

    @Override
    public String toString() {

        return dataMap.toString();
    }
}
