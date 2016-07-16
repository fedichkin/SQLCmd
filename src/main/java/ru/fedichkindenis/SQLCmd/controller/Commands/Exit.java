package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.Console;
import ru.fedichkindenis.SQLCmd.view.View;

/**
 * Created by Денис on 16.07.2016.
 *
 * Команда для выхода из приложения
 * Формат команды: exit
 * Пример команды: exit
 */
public class Exit implements Command {

    private DBManager dbManager;
    private View view;
    private String textCommand;

    public Exit(DBManager dbManager, View view, String textCommand) {
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

        throw new ExitException();
    }

    private boolean validateCommand() {

        if(StringUtil.isEmpty(textCommand)) return false;
        if(!textCommand.equals("exit")) return false;

        return true;
    }
}
