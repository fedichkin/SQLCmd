package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

/**
 * Команда для выполнения пользовательского запроса
 * Формат команды: user-query|<пользовательский запрос
 * Пример команды: user-query|<insert into usr (id, login, password) values(6, "us", "1234")
 * В текущей версии select запросы не доступны
 */
public class UserQuery implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public UserQuery(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] parameters = textCommand.split("\\|<");
        String textQuery = parameters[1];

        dbManager.userQuery(textQuery);
        view.write("Была выполнен запрос: " + textQuery + "!");
    }

    private boolean validateCommand() {

        int countArguments = 2;
        boolean isValidateCommand;

        isValidateCommand = !StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && textCommand.startsWith("user-query|");
        isValidateCommand = isValidateCommand && textCommand.split("\\|<").length == countArguments;

        return isValidateCommand;
    }
}
