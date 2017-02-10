package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

/**
 * Команда для очистки таблицы
 * Формат команды: clear-table|наименование таблицы
 * Пример команды: clear-table|usr
 */
public class ClearTable implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public ClearTable(DBManager dbManager, ViewDecorator view, String textCommand) {
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

        dbManager.clearTable(nameTable);
        view.write("Таблица " + nameTable + " очищена!");
    }

    private boolean validateCommand() {

        int countArgument = 2;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("clear-table|")
                && textCommand.split("\\|").length == countArgument;
    }
}
