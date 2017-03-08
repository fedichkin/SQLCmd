package ru.fedichkindenis.sqlcmd.controller;

import ru.fedichkindenis.sqlcmd.controller.commands.Command;
import ru.fedichkindenis.sqlcmd.controller.commands.CommandFactory;
import ru.fedichkindenis.sqlcmd.controller.commands.ExitException;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

/**
 * Класс контролер для управления основной логикой программы
 */
public class Controller {

    private ViewDecorator view;
    private DBManager dbManager;

    public Controller(ViewDecorator view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    public void run() {

        view.write("Приветствую тебя пользователь!");
        view.write("Для начала работы с системой установи соединение с базой данных с помощью команды: ");
        view.write("connect|host|port|dbName|userName|password");

        while (true) {

            try {

                view.write("");
                view.write("Введите команду (help для справки): ");
                String textCommand = view.read();

                CommandFactory commandFactory = new CommandFactory(dbManager, view, textCommand);

                Command command = commandFactory.createCommand();
                command.execute();
            } catch (ExitException e) {
                break;
            } catch (Exception e) {

                view.write("Произошла ошибка: " + e.getMessage());
            }
        }
    }
}
