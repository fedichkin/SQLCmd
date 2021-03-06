package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.model.DataRow;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

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

    public DataTable(DBManager dbManager, ViewDecorator view, String textCommand) {
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

        String nameTable = textCommand.split(SEPARATE)[indexNameTable];

        List<DataRow> dataRowList = dbManager.dataTable(nameTable);

        if(dataRowList.size() == 1
                && dataRowList.get(0).getListValueField().size() == 0) {

            view.write("Таблица " + nameTable + " не содержит данных и полей!");
        }
        else {
            view.write(dataRowList);
        }
    }

    private boolean validateCommand() {

        int countArgument = 2;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("data-table|")
                && textCommand.split(SEPARATE).length == countArgument;
    }
}
