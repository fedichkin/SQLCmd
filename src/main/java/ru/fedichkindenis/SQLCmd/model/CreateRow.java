package ru.fedichkindenis.SQLCmd.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс для хранения набора параметв создания полей
 */
public class CreateRow extends Row {

    public void add(String nameField, Object value, String tpeField) {

        CreateField createField = new CreateField(nameField, value, tpeField);
        add(createField);
    }

    public Collection<String> getListTypeField() {

        List<String> typeFieldList = new LinkedList<>();

        Iterator<Field> it = getIteratorField();
        while (it.hasNext()) {
            CreateField field = (CreateField) it.next();

            typeFieldList.add(field.getTypeField());
        }

        return typeFieldList;
    }
}
