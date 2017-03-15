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

    private final int MIN_COUNT_ARGUMENTS = 2;

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

        final int indexNameTable = 1;


        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] parameters = textCommand.split(SEPARATE);
        String nameTable = parameters[indexNameTable];

        CreateRow createRow = new CreateRow();
        if(parameters.length > MIN_COUNT_ARGUMENTS) {
            String [] parametersCreateRow = Arrays.copyOfRange(parameters, MIN_COUNT_ARGUMENTS, parameters.length);
            RowFactory rowFactory = new RowFactory(parametersCreateRow);
            createRow = rowFactory.createCreateRow();
        }

        dbManager.createTable(nameTable, createRow);
        view.write("Была создана таблица " + nameTable + "!");
    }

    private boolean validateCommand() {

        final int countParametersForField = 3;
        boolean isValidateCommand;

        isValidateCommand = !StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && textCommand.startsWith("create-table|");
        isValidateCommand = isValidateCommand && textCommand.split(SEPARATE).length >= MIN_COUNT_ARGUMENTS;
        isValidateCommand = isValidateCommand && (textCommand.split(SEPARATE).length - MIN_COUNT_ARGUMENTS)
                % countParametersForField == 0;

        return isValidateCommand;
    }
}
