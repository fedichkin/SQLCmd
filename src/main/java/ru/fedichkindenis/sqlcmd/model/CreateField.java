package ru.fedichkindenis.sqlcmd.model;

/**
 * Класс для хранения параметров создания поля
 */
public class CreateField extends Field {

    private String typeField;
    private boolean isNotNull;

    CreateField(String nameFiled, String typeField, boolean isNotNull) {
        super(nameFiled);
        this.typeField = typeField;
        this.isNotNull = isNotNull;
    }

    public String getTypeField() {
        return typeField;
    }

    public boolean isNotNull() {
        return isNotNull;
    }
}
