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

        final int indexHost = 1;
        final int indexPort = 2;
        final int indexDbName = 3;
        final int indexUserName = 4;
        final int indexPassword = 5;

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] arguments = textCommand.split(SEPARATE);
        String host = arguments[indexHost];
        String port = arguments[indexPort];
        String dbName = arguments[indexDbName];
        String userName = arguments[indexUserName];
        String password = arguments[indexPassword];

        dbManager.connect(host, port, dbName, userName, password);
        view.write("Соединение установлено!");
    }

    private boolean validateCommand() {

        int countArgument = 6;

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("connect|")
                && textCommand.split(SEPARATE).length == countArgument;

    }
}
