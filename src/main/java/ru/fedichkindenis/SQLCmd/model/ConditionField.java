package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения условия
 */
public class ConditionField extends Field {

    private String condition;

    ConditionField(String nameField, Object valueField, String condition) {
        super(nameField, valueField);
        this.condition = condition;
    }
}
