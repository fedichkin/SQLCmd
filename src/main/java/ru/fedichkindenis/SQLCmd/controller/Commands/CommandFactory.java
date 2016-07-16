package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.View;

/**
 * Created by Денис on 15.07.2016.
 */
public class CommandFactory {

    private DBManager dbManager;
    private View view;
    private String textCommand;

    public CommandFactory(DBManager dbManager, View view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    public Command createCommand() {

        if(StringUtil.isEmpty(textCommand) || textCommand.split("\\|").length == 0) {

            throw new IllegalArgumentException("Введен не верный формат команды!");
        }

        String wordCommand = textCommand.split("\\|")[0];

        switch (wordCommand) {

            case "connect":
                return new Connect(dbManager, view, textCommand);
            case "exit":
                return new Exit(dbManager, view, textCommand);
            default:
                return new Unsupported();
        }
    }
}
