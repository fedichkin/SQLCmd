package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

/**
 * Команда для удаления таблицы
 * Формат команды: delete-table|имя таблицы
 * Пример команды: delete-table|usr
 */
public class DeleteTable implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public DeleteTable(DBManager dbManager, ViewDecorator view, String textCommand) {
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

        dbManager.deleteTable(nameTable);

        view.write("Таблица " + nameTable + " была удалена!");
    }

    private boolean validateCommand() {

        int countArguments = 2;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("delete-table|")
                && textCommand.split(SEPARATE).length == countArguments;

    }
}
