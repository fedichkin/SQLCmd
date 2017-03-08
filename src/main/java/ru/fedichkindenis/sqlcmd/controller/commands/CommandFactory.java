package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

/**
 * Класс-фабрика для создания команд
 */
public class CommandFactory {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public CommandFactory(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    public Command createCommand() {

        if(StringUtil.isEmpty(textCommand) || textCommand.split("\\|").length == 0) {

            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String wordCommand = textCommand.split("\\|")[0];

        switch (wordCommand) {

            case "connect":
                return new Connect(dbManager, view, textCommand);
            case "exit":
                return new Exit(dbManager, view, textCommand);
            case "list-table":
                return new ListTable(dbManager, view, textCommand);
            case "data-table":
                return new DataTable(dbManager, view, textCommand);
            case "clear-table":
                return new ClearTable(dbManager, view, textCommand);
            case "delete-table":
                return new DeleteTable(dbManager, view, textCommand);
            case "insert-row":
                return new InsertRow(dbManager, view, textCommand);
            case "update-row":
                return new UpdateRow(dbManager, view, textCommand);
            case "delete-row":
                return new DeleteRow(dbManager, view, textCommand);
            case "create-table":
                return new CreateTable(dbManager, view, textCommand);
            case "user-query":
                return new UserQuery(dbManager, view, textCommand);
            case "disconnect":
                return new Disconnect(dbManager, view, textCommand);
            case "help":
                return new Help(view, textCommand);
            default:
                return new Unsupported();
        }
    }
}
