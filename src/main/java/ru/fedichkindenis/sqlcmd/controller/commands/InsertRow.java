package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.controller.row.RowFactory;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.model.DataRow;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

import java.util.Arrays;

/**
 * Команда для вставки строк в таблицу
 * Формат команды: insert-row|наименование таблицы|наименование поля1|значение1...
 * Пример команды: insert-row|usr|id|1|login|user|password|qwerty
 */
public class InsertRow implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public InsertRow(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        final int indexNameTable = 1;
        final int startIndexField = 2;

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] parameters = textCommand.split(SEPARATE);
        String nameTable = parameters[indexNameTable];
        String [] parametersDataRow = Arrays.copyOfRange(parameters, startIndexField, parameters.length);

        RowFactory rowFactory = new RowFactory(parametersDataRow);
        DataRow dataRow = rowFactory.createDataRow();

        dbManager.insert(nameTable, dataRow);
        view.write("В таблицу " + nameTable + " была добавлена строка!");
    }

    private boolean validateCommand() {

        int minCountArguments = 4;
        int countParametersInField = 2;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("insert-row|")
                && textCommand.split(SEPARATE).length >= minCountArguments
                && textCommand.split(SEPARATE).length % countParametersInField == 0;
    }
}
