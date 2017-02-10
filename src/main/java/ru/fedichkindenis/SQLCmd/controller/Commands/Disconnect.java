package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

/**
 * Команда для разрыва соединения с базой данных
 * Формат команды: disconnect
 * Пример команды: disconnect
 */
public class Disconnect implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public Disconnect(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        dbManager.disconnect();
        view.write("Соединение с базой разорванно");
    }

    private boolean validateCommand() {

        return !StringUtil.isEmpty(textCommand) && textCommand.equals("disconnect");

    }
}
