package ru.fedichkindenis.sqlcmd.model;

import java.util.*;

/**
 * База для строки таблицы
 */
public abstract class Row {

    private List<Field> row = new LinkedList<>();

    void add(Field field) {

        row.add(field);
    }

    public Collection<String> getListNameField() {

        List<String> nameList = new LinkedList<>();

        for(Field field : row) {

            nameList.add(field.getNameField());
        }

        return nameList;
    }

    int getCountField() {

        return row.size();
    }

    Iterator<Field> getIteratorField() {

        return row.iterator();
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
