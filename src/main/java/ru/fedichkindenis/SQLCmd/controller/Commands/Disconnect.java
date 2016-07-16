package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.View;

/**
 * Created by Денис on 16.07.2016.
 *
 * Команда для разрыва соединения с базой данных
 * Формат команды: disconnect
 * Пример команды: disconnect
 */
public class Disconnect implements Command {

    private DBManager dbManager;
    private View view;
    private String textCommand;

    public Disconnect(DBManager dbManager, View view, String textCommand) {
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

        if(StringUtil.isEmpty(textCommand)) return false;
        if(!textCommand.equals("disconnect")) return false;

        return true;
    }
}
