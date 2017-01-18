package ru.fedichkindenis.SQLCmd.model;

import java.util.*;

/**
 * Класс для хранения строки таблицы
 */
public class DataRow {

    private List<DataField> dataRow = new LinkedList<>();

    public void add(String nameField, Object field) {

        DataField dataField = new DataField(nameField, field);
        dataRow.add(dataField);
    }

    public Collection<String> getListNameField() {

        List<String> nameList = new ArrayList<>();

        for(DataField dataField : dataRow) {

            nameList.add(dataField.getNameField());
        }

        return nameList;
    }

    public Collection<Object> getListValueField() {

        List<Object> valueList = new ArrayList<>();

        for(DataField dataField : dataRow) {

            valueList.add(dataField.getValueField());
        }

        return valueList;
    }

    int getCountField() {

        return dataRow.size();
    }

    @Override
    public String toString() {

        String result = "{";

        for(DataField dataField : dataRow) {

            result += dataField.toString();
        }

        return result + "}";
    }
}
