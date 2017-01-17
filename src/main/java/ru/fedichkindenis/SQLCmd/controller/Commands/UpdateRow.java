package ru.fedichkindenis.SQLCmd.controller.Commands;

import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.model.DataRow;
import ru.fedichkindenis.SQLCmd.util.StringUtil;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

/**
 * Команда для обновления данных в строках отобраных по условию
 * Формат команды: update-row|наименование таблицы|наименование поля1|значение поля 1| ...
 * |!IF|поле условия1|оператор условия1|значение условия1| ...
 * Пример команды: update-row|usr|password|1111|!IF|id|=|3
 *
 * В данной версии программы можно использовать только такие
 * операторы для сравнения: =, <>, >, <, >=, <=
 * Блок с уловием может отсутствовать
 */
public class UpdateRow implements Command {

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    private static int MIN_COUNT_ARGUMENT = 4;

    public UpdateRow(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] blocksCommand = textCommand.split("\\|!IF\\|");
        String [] parameters = textCommand.split("\\|");
        String nameTable = parameters[1];

        DataRow dataRow = new DataRow();

    }

    private boolean validateCommand() {

        boolean isValidateCommand;

        isValidateCommand = StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && !textCommand.startsWith("update-row|");
        isValidateCommand = isValidateCommand && textCommand.split("\\|").length < MIN_COUNT_ARGUMENT;

        String [] blocksCommand = textCommand.split("\\|!IF\\|");

        isValidateCommand = isValidateCommand && blocksCommand.length < 1 || blocksCommand.length > 2;

        String [] argumentsFirstBlock = blocksCommand[0].split("\\|");
        String [] argumentsSecondBlock = blocksCommand.length == 2
                ? blocksCommand[1].split("\\|") : new String [0];

        isValidateCommand = isValidateCommand && argumentsFirstBlock.length % 2 != 0;
        isValidateCommand = isValidateCommand && argumentsSecondBlock.length % 3 != 0;

        return isValidateCommand;
    }
}
