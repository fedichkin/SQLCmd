package ru.fedichkindenis.SQLCmd.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс для хранения строки условий
 */
public class ConditionRow {

    private List<ConditionField> conditionRow = new LinkedList<>();

    public void add(String nameField, Object value, String condition) {

        ConditionField conditionField = new ConditionField(nameField, value, condition);
        conditionRow.add(conditionField);
    }
}
