package ru.fedichkindenis.SQLCmd.model;

import java.util.*;

/**
 * Created by Денис on 11.07.2016.
 *
 * Класс для хранения данных таблицы
 */
public class DataMap {

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
