package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.AlignWrite;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

import java.util.List;

/**
 * Команда для вывода списка таблиц
 * Формат команды: list-table
 * Пример команды: list-table
 */
public class ListTable implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public ListTable(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        List<String> listTable = dbManager.listTable();
        view.write(listTable, AlignWrite.VERTICAL);
    }

    private boolean validateCommand() {

        return !StringUtil.isEmpty(textCommand) && textCommand.equals("list-table");

    }
}
