package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

/**
 * Команда для выхода из приложения
 * Формат команды: exit
 * Пример команды: exit
 */
public class Exit implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public Exit(DBManager dbManager, ViewDecorator view, String textCommand) {
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
        view.close();
        view.write("До свидания!");

        throw new ExitException();
    }

    private boolean validateCommand() {

        return !StringUtil.isEmpty(textCommand) && textCommand.equals("exit");

    }
}
