package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.controller.row.RowFactory;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.model.DataRow;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import java.util.Arrays;

/**
 * Команда для вставки строк в таблицу
 * Формат команды: insert-row|наименование таблицы|наименование поля1|значение1.....
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

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] parameters = textCommand.split("\\|");
        String nameTable = parameters[1];
        String [] parametersDataRow = Arrays.copyOfRange(parameters, 2, parameters.length);

        RowFactory rowFactory = new RowFactory(parametersDataRow);
        DataRow dataRow = rowFactory.createDataRow();

        dbManager.insert(nameTable, dataRow);
        view.write("В таблицу " + nameTable + " была добавлена строка!");
    }

    private boolean validateCommand() {

        int minCountArguments = 4;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("insert-row|")
                && textCommand.split("\\|").length >= minCountArguments
                && textCommand.split("\\|").length % 2 == 0;
    }
}
