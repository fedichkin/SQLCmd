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

    public Object getValueField() {
        return valueField;
    }

    public String getCondition() {
        return condition;
    }
}
