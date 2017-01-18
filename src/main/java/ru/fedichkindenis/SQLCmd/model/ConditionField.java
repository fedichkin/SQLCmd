package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения условия
 */
public class ConditionField {

    private String nameField;
    private String value;
    private String condition;

    public ConditionField(String nameField, String value, String condition) {
        this.nameField = nameField;
        this.value = value;
        this.condition = condition;
    }
}
