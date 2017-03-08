package ru.fedichkindenis.sqlcmd.model;

/**
 * Класс для хранения параметров создания поля
 */
class CreateField extends Field {

    private String typeField;
    private boolean isNotNull;

    CreateField(String nameFiled, String typeField, boolean isNotNull) {
        super(nameFiled);
        this.typeField = typeField;
        this.isNotNull = isNotNull;
    }

    String getTypeField() {
        return typeField;
    }

    boolean isNotNull() {
        return isNotNull;
    }
}
