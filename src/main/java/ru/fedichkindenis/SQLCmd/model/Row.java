package ru.fedichkindenis.SQLCmd.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * База для строки таблицы
 */
public abstract class Row {

    private List<Field> row = new LinkedList<>();

    void add(Field field) {

        row.add(field);
    }

    public Collection<String> getListNameField() {

        List<String> nameList = new ArrayList<>();

        for(Field field : row) {

            nameList.add(field.getNameField());
        }

        return nameList;
    }

    public Collection<Object> getListValueField() {

        List<Object> valueList = new ArrayList<>();

        for(Field field : row) {

            valueList.add(field.getValueField());
        }

        return valueList;
    }

    int getCountField() {

        return row.size();
    }

    @Override
    public String toString() {

        String result = "{";

        for(Field field : row) {

            result += field.toString();
        }

        return result + "}";
    }
}
