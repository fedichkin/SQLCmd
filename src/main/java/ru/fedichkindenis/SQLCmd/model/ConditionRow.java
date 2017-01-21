package ru.fedichkindenis.SQLCmd.model;

import java.util.*;

/**
 * Класс для хранения строки условий
 */
public class ConditionRow extends Row {

    public void add(String nameField, Object value, String condition) {

        ConditionField conditionField = new ConditionField(nameField, value, condition);
        add(conditionField);
    }

    public Collection<String> getListConditionField() {

        List<String> conditionList = new LinkedList<>();

        Iterator<Field> it = getIteratorField();
        while (it.hasNext()) {
            ConditionField field = (ConditionField) it.next();

            conditionList.add(field.getCondition());
        }

        return conditionList;
    }
}
