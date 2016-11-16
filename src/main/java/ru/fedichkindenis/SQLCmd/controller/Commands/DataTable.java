package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.model.DataRow;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import java.util.List;

/**
 * Команда для вывода данных таблицы
 * Формат команды: data-table|наименование таблицы
 * Пример команды: data-table|usr
 */
public class DataTable implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    private static int COUNT_ARGUMENT = 2;

    public DataTable(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String nameTable = textCommand.split("\\|")[1];

        List<DataRow> dataRowList = dbManager.dataTable(nameTable);
        view.write(dataRowList);
    }

    private boolean validateCommand() {

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("data-table|")
                && textCommand.split("\\|").length == COUNT_ARGUMENT;
    }
}
