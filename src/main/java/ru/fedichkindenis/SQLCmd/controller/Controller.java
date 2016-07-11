package ru.fedichkindenis.SQLCmd.controller;

import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.View;

/**
 * Created by Денис on 11.07.2016.
 */
public class Controller {

    private View view;
    private DBManager dbManager;
    private Command command;

    public Controller(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }
}
