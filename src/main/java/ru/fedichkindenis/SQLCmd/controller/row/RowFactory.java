package ru.fedichkindenis.SQLCmd.controller.row;

import ru.fedichkindenis.SQLCmd.model.ConditionRow;
import ru.fedichkindenis.SQLCmd.model.CreateRow;
import ru.fedichkindenis.SQLCmd.model.DataRow;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Фабрика для создания разного вида строк
 */
public class RowFactory {

    private String [] parameters;
    private final String DATA_ROW = "data-row";
    private final String CONDITION_ROW = "condition-row";
    private final String CREATE_ROW = "create-row";

    public RowFactory(String[] parameters) {
        this.parameters = parameters;
    }

    public DataRow createDataRow() {

        validate(DATA_ROW);

        int countField = (parameters.length / 2);
        int firstFieldIndex = 0;
        int firstValueIndex = 1;

        DataRow dataRow = new DataRow();
        for(int index = 0; index < countField; index ++) {

            String field = parameters[firstFieldIndex + index * 2];
            Object value = getValue(parameters[firstValueIndex + index * 2]);

            dataRow.add(field, value);
        }

        return dataRow;
    }

    public ConditionRow createConditionRow() {

        validate(CONDITION_ROW);

        int countCondition = parameters.length / 3;
        int firstIndexNameField = 0;
        int firstIndexCondition = 1;
        int firstIndexValueField = 2;

        ConditionRow conditionRow = new ConditionRow();
        for(int index = 0; index < countCondition; index++) {

            String nameFiled = parameters[firstIndexNameField + index * 3];
            String condition = parameters[firstIndexCondition + index * 3];
            Object valueField = getValue(parameters[firstIndexValueField + index * 3]);

            conditionRow.add(nameFiled, valueField, condition);
        }

        return conditionRow;
    }

    public CreateRow createCreateRow() {

        validate(CONDITION_ROW);

        int countCreateField = parameters.length / 3;
        int firstIndexNameField = 0;
        int firstIndexTypeField = 1;
        int firstIndexIsNotNullField = 2;

        CreateRow createRow = new CreateRow();
        for(int index = 0; index < countCreateField; index++) {

            String nameField = parameters[firstIndexNameField + index * 3];
            String typeField = parameters[firstIndexTypeField + index * 3];
            String isNotNullField = parameters[firstIndexIsNotNullField + index * 3];

            createRow.add(nameField, typeField, isNotNullField.equals("true"));
        }

        return createRow;
    }

    private void validate(String typeRow) {

        boolean valid;

        valid = parameters != null;
        valid = valid && parameters.length > 0;

        switch (typeRow) {

            case DATA_ROW:
                valid = valid && parameters.length % 2 == 0;
                break;
            case CONDITION_ROW:
            case CREATE_ROW:
                valid = valid && parameters.length % 3 == 0;
                break;
            default:
                valid = false;
        }

        if (!valid) {

            throw new IllegalArgumentException("Указан не верные параметры строки");
        }
    }

    private Object getValue(String parameter) {

        if(parameter.matches("\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d")) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            try {
                return dateFormat.parse(parameter);
            } catch (ParseException e) {
                return parameter;
            }
        }
        else {
            try {

                return new BigDecimal(parameter);
            } catch (NumberFormatException e) {

                return parameter;
            }
        }
    }
}
