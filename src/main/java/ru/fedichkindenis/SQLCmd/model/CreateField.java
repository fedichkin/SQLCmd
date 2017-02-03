package ru.fedichkindenis.SQLCmd.model;

/**
 * Класс для хранения параметров создания поля
 */
public class CreateField extends Field {

    private String typeField;

    CreateField(String nameFiled, Object valueField, String typeField) {
        super(nameFiled, valueField);
        this.typeField = typeField;
    }

    public String getTypeField() {
        return typeField;
    }
}
