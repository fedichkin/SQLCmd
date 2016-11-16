package ru.fedichkindenis.SQLCmd.model;

import java.sql.Types;
import java.util.*;

/**
 * Класс для хранения строки таблицы
 */
public class DataRow {

    private List<DataField> dataRow = new LinkedList<>();

    public void add(String nameField, Object field, Types types) {

        DataField dataField = new DataField(nameField, field, types);
        dataRow.add(dataField);
    }

    public Collection<String> getListNameField() {

        List<String> nameList = new ArrayList<>();

        for(DataField dataField : dataRow) {

            nameList.add(dataField.getName());
        }

        return nameList;
    }

    public Collection<Object> getListValueField() {

        List<Object> valueList = new ArrayList<>();

        for(DataField dataField : dataRow) {

            valueList.add(dataField.getValue());
        }

        return valueList;
    }

    public int getCountField() {

        return dataRow.size();
    }

    public Object getValueByName(String nameField) {

        for(DataField dataField : dataRow) {

            if(dataField.getName().equals(nameField)) {

                return dataField.getValue();
            }
        }

        return null;
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
