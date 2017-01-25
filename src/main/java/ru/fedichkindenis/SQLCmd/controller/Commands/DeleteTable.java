package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

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

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] parameters = textCommand.split("\\|");
        String nameTable = parameters[1];

        dbManager.deleteTable(nameTable);

        view.write("Таблица " + nameTable + " была удалена!");
    }

    private boolean validateCommand() {

        int countArguments = 2;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("delete-table|")
                && textCommand.split("\\|").length == countArguments;

    }
}
