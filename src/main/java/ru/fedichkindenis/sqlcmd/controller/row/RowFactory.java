package ru.fedichkindenis.sqlcmd.controller.row;

import ru.fedichkindenis.sqlcmd.model.ConditionRow;
import ru.fedichkindenis.sqlcmd.model.CreateRow;
import ru.fedichkindenis.sqlcmd.model.DataRow;

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

        final int countParametersInField = 2;
        final int countField = (parameters.length / countParametersInField);
        final int firstFieldIndex = 0;
        final int firstValueIndex = 1;

        DataRow dataRow = new DataRow();
        for(int index = 0; index < countField; index ++) {

            String field = parameters[firstFieldIndex + index * countParametersInField];
            Object value = getValue(parameters[firstValueIndex + index * countParametersInField]);

            dataRow.add(field, value);
        }

        return dataRow;
    }

    public ConditionRow createConditionRow() {

        validate(CONDITION_ROW);

        final int countParametersInField = 3;
        final int countCondition = parameters.length / countParametersInField;
        final int firstIndexNameField = 0;
        final int firstIndexCondition = 1;
        final int firstIndexValueField = 2;

        ConditionRow conditionRow = new ConditionRow();
        for(int index = 0; index < countCondition; index++) {

            String nameFiled = parameters[firstIndexNameField + index * countParametersInField];
            String condition = parameters[firstIndexCondition + index * countParametersInField];
            Object valueField = getValue(parameters[firstIndexValueField + index * countParametersInField]);

            conditionRow.add(nameFiled, valueField, condition);
        }

        return conditionRow;
    }

    public CreateRow createCreateRow() {

        validate(CONDITION_ROW);

        final int countParametersInField = 3;
        final int countCreateField = parameters.length / countParametersInField;
        final int firstIndexNameField = 0;
        final int firstIndexTypeField = 1;
        final int firstIndexIsNotNullField = 2;

        CreateRow createRow = new CreateRow();
        for(int index = 0; index < countCreateField; index++) {

            String nameField = parameters[firstIndexNameField + index * countParametersInField];
            String typeField = parameters[firstIndexTypeField + index * countParametersInField];
            String isNotNullField = parameters[firstIndexIsNotNullField + index * countParametersInField];

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

        final String regexDateFormat = "\\d\\d\\.\\d\\d\\.\\d\\d\\d\\d";
        final String textDateFormat = "dd.MM.yyyy";

        if(parameter.matches(regexDateFormat)) {

            SimpleDateFormat dateFormat = new SimpleDateFormat(textDateFormat);

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
