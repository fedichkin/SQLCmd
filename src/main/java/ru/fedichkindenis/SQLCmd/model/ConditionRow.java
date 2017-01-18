package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения строки условий
 */
public class ConditionRow extends Row {

    public void add(String nameField, Object value, String condition) {

        ConditionField conditionField = new ConditionField(nameField, value, condition);
        add(conditionField);
    }
}
