package ru.fedichkindenis.SQLCmd;

import ru.fedichkindenis.SQLCmd.bd.BDManager;
import ru.fedichkindenis.SQLCmd.bd.PostgreSql;
import ru.fedichkindenis.SQLCmd.controller.Controller;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.model.JDBCManager;
import ru.fedichkindenis.SQLCmd.view.Console;
import ru.fedichkindenis.SQLCmd.view.View;

import java.util.List;

/**
 * Created by Денис on 05.06.2016.
 */
public class Main {

    public static void main(String[] args) {

        View view = new Console();
        DBManager dbManager = new JDBCManager();

        Controller controller = new Controller(view, dbManager);
        controller.run();
    }
}
