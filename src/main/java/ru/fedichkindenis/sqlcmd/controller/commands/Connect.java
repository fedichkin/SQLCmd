package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

/**
 * Команда для установки соединения с базой данных
 * Формат команды: connect|host|port|dbName|userName|password
 * Пример команды: connect|localhost|5433|test|root|1234
 */
public class Connect implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public Connect(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] arguments = textCommand.split("\\|");
        String host = arguments[1];
        String port = arguments[2];
        String dbName = arguments[3];
        String userName = arguments[4];
        String password = arguments[5];

        dbManager.connect(host, port, dbName, userName, password);
        view.write("Соединение установлено!");
    }

    private boolean validateCommand() {

        int countArgument = 6;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("connect|")
                && textCommand.split("\\|").length == countArgument;

    }
}
