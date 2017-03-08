package ru.fedichkindenis.sqlcmd.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для хранения строки таблицы
 */
public class DataRow extends Row {

    public void add(String nameField, Object field) {

        DataField dataField = new DataField(nameField, field);
        add(dataField);
    }

    public Collection<Object> getListValueField() {

        List<Object> valueList = new LinkedList<>();

        Iterator<Field> it = getIteratorField();
        while (it.hasNext()) {
            DataField field = (DataField) it.next();

            valueList.add(field.getValueField());
        }

        return valueList;
    }
}
