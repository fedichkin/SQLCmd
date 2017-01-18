package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.ConditionRow;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.model.DataRow;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import java.math.BigDecimal;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Команда для обновления данных в строках отобраных по условию
 * Формат команды: update-row|наименование таблицы|наименование поля1|значение поля 1| ...
 * |!IF|поле условия1|оператор условия1|значение условия1| ...
 * Пример команды: update-row|usr|password|1111|!IF|id|=|3
 *
 * В данной версии программы можно использовать только такие
 * операторы для сравнения: =, <>, >, <, >=, <=
 * Блок с уловием может отсутствовать
 */
public class UpdateRow implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    private static int MIN_COUNT_ARGUMENT = 4;

    public UpdateRow(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] blocksCommand = textCommand.split("\\|!IF\\|");
        String [] parameters = blocksCommand[0].split("\\|");
        String nameTable = parameters[1];
        int countField = (parameters.length / 2) - 1;
        int firstFieldIndex = 2;
        int firstValueIndex = 3;

        DataRow dataRow = new DataRow();
        for(int index = 0; index < countField; index ++) {

            String field = parameters[firstFieldIndex + index * 2];
            Object value = getValue(parameters[firstValueIndex + index * 2]);
            int type = getTypeValue(value);

            dataRow.add(field, value, type);
        }

        ConditionRow conditionRow = new ConditionRow();

        if(blocksCommand.length == 2) {

            String [] conditions = blocksCommand[1].split("\\|");
            int countCondition = conditions.length / 3;
            int firstIndexNameField = 0;
            int firstIndexCondition = 1;
            int firstIndexValueField = 2;

            for(int index = 0; index < countCondition; index++) {

                String nameFiled = conditions[firstIndexNameField + index * 3];
                String condition = conditions[firstIndexCondition + index * 3];
                String valueField = conditions[firstIndexValueField + index * 3];

                conditionRow.add(nameFiled, valueField, condition);
            }
        }

        dbManager.update(nameTable, dataRow, conditionRow);
        view.write("В таблице " + nameTable + " была обновлена строка!");
    }

    private boolean validateCommand() {

        boolean isValidateCommand;

        isValidateCommand = StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && !textCommand.startsWith("update-row|");
        isValidateCommand = isValidateCommand && textCommand.split("\\|").length < MIN_COUNT_ARGUMENT;

        String [] blocksCommand = textCommand.split("\\|!IF\\|");

        isValidateCommand = isValidateCommand && blocksCommand.length < 1 || blocksCommand.length > 2;

        String [] argumentsFirstBlock = blocksCommand[0].split("\\|");
        String [] argumentsSecondBlock = blocksCommand.length == 2
                ? blocksCommand[1].split("\\|") : new String [0];

        isValidateCommand = isValidateCommand && argumentsFirstBlock.length % 2 != 0;
        isValidateCommand = isValidateCommand && argumentsSecondBlock.length % 3 != 0;

        return isValidateCommand;
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

    private int getTypeValue(Object value) {

        if(value instanceof Date) return Types.DATE;
        if(value instanceof BigDecimal) return Types.DECIMAL;

        return Types.VARCHAR;
    }
}
