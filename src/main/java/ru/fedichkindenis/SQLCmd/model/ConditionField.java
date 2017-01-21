package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения условия
 */
class ConditionField extends Field {

    private String condition;

    ConditionField(String nameField, Object valueField, String condition) {
        super(nameField, valueField);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }
}
