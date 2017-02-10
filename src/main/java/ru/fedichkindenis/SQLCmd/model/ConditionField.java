package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения условия
 */
class ConditionField extends Field {

    private Object valueField;
    private String condition;

    ConditionField(String nameField, Object valueField, String condition) {
        super(nameField);
        this.condition = condition;
        this.valueField = valueField;
    }

    Object getValueField() {
        return valueField;
    }

    String getCondition() {
        return condition;
    }
}
