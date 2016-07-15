package ru.fedichkindenis.SQLCmd.controller;

import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.CommandFactory;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.View;

/**
 * Created by Денис on 11.07.2016.
 */
public class Controller {

    private View view;
    private DBManager dbManager;

    public Controller(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    public void run() {

        view.write("Приветствую тебя пользователь!");
        view.write("Для начала работы с ситемой установи соединение с базой данных с помощью команды: ");
        view.write("connect|host|port|dbName|userName|password");

        String textCommand = view.read();

        CommandFactory commandFactory = new CommandFactory(dbManager, view, textCommand);

        Command command = commandFactory.createCommand();
        command.execute();
    }
}
