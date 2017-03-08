package ru.fedichkindenis.sqlcmd;

import ru.fedichkindenis.sqlcmd.controller.Controller;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.model.JDBCManager;
import ru.fedichkindenis.sqlcmd.view.Console;
import ru.fedichkindenis.sqlcmd.view.TableConsoleView;
import ru.fedichkindenis.sqlcmd.view.View;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

/**
 * Класс для запуска приложения
 */
public class Main {

    public static void main(String[] args) {

        View view = new Console();
        ViewDecorator viewDecorator = new TableConsoleView(view);
        DBManager dbManager = new JDBCManager();

        Controller controller = new Controller(viewDecorator, dbManager);
        controller.run();
    }
}
