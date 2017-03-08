package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.controller.row.RowFactory;
import ru.fedichkindenis.sqlcmd.model.CreateRow;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

import java.util.Arrays;

/**
 * Команда для создания таблицы
 * Формат команды: create-table|наименование таблицы|
 * поле 1| тип поля 1| признак обязательного заполнения поля 1|...
 * Пример команды: create-table|my_table|id|bigint|true|name|varchar|false
 *
 * В данной версии программы у поля можно указать только тип
 * и признак обязательного заполнения.
 * Блок с полями не обязателен.
 */
public class CreateTable implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public CreateTable(DBManager dbManager, ViewDecorator view, String textCommand) {
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

        CreateRow createRow = new CreateRow();
        if(parameters.length > 2) {
            String [] parametersCreateRow = Arrays.copyOfRange(parameters, 2, parameters.length);
            RowFactory rowFactory = new RowFactory(parametersCreateRow);
            createRow = rowFactory.createCreateRow();
        }

        dbManager.createTable(nameTable, createRow);
        view.write("Была создана таблица " + nameTable + "!");
    }

    private boolean validateCommand() {

        int minCountArguments = 2;
        boolean isValidateCommand;

        isValidateCommand = !StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && textCommand.startsWith("create-table|");
        isValidateCommand = isValidateCommand && textCommand.split("\\|").length >= minCountArguments;
        isValidateCommand = isValidateCommand && (textCommand.split("\\|").length - 2) % 3 == 0;

        return isValidateCommand;
    }
}
