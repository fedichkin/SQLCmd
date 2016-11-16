package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.model.DataRow;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

/**
 * Команда для вставки строк в таблицу
 * Формат команды: insert-row|наименование таблицы|наименование поля1|значение1.....
 * Пример команды: insert-row|usr|id|1|login|user|password|qwerty
 */
public class InsertRow implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    private static int MIN_COUNT_ARGUMENT = 4;

    public InsertRow(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] parameters = textCommand.split("\\|");
        String nameTable = parameters[1];
        int countField = (parameters.length / 2) - 1;
        int firstFieldIndex = 2;
        int firstValueIndex = 3;

        DataRow dataRow = new DataRow();
        for(int index = 0; index < countField; index ++) {

            String field = parameters[firstFieldIndex + index * 2];
            String value = parameters[firstValueIndex + index * 2];

            dataRow.add(field, value);
        }

        dbManager.insert(nameTable, dataRow);
        view.write("В таблицу " + nameTable + " была добавлена строка!");
    }

    private boolean validateCommand() {

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("insert-row|")
                && textCommand.split("\\|").length >= MIN_COUNT_ARGUMENT
                && textCommand.split("\\|").length % 2 == 0;
    }
}
