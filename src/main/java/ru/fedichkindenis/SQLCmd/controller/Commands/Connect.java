package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

/**
 * Created by Денис on 13.07.2016.
 *
 * Команда для установки соединения, например с базой данных
 * Формат команды: connect|host|port|dbName|userName|password
 * Пример команды: connect|localhost|5433|test|root|1234
 */
public class Connect implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    private static int COUNT_ARGUMENT = 6;

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

        return !StringUtil.isEmpty(textCommand)
                && textCommand.startsWith("connect|")
                && textCommand.split("\\|").length == COUNT_ARGUMENT;

    }
}
