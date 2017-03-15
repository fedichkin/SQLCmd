package ru.fedichkindenis.sqlcmd.controller.commands;

import ru.fedichkindenis.sqlcmd.controller.row.RowFactory;
import ru.fedichkindenis.sqlcmd.model.ConditionRow;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.util.StringUtil;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

/**
 * Команда для удаления строк отобранных по условию
 * Формат команды: delete-row|наименование таблицы|!IF|
 * поле условия1|оператор условия1|значение условия1| ...
 * Пример команды: delete-row|usr|!IF|id|<|5
 * В данной версии программы можно использовать только такие
 * операторы для сравнения: =, <>, >, <, >=, <=
 * Блок условия не обязателен
 */
public class DeleteRow implements Command {

    private final int COUNT_BLOCKS_IN_COMMAND = 2;

    private DBManager dbManager;
    private ViewDecorator view;
    private String textCommand;

    public DeleteRow(DBManager dbManager, ViewDecorator view, String textCommand) {
        this.dbManager = dbManager;
        this.view = view;
        this.textCommand = textCommand;
    }

    @Override
    public void execute() {

        final int indexNameTable = 1;

        if(!validateCommand()) {
            throw new IllegalArgumentException("Указан не верный формат команды");
        }

        String [] blocksCommand = textCommand.split(IF_SEPARATE);
        String [] parameters = blocksCommand[0].split(SEPARATE);
        String nameTable = parameters[indexNameTable];

        ConditionRow conditionRow = new ConditionRow();

        if(blocksCommand.length == COUNT_BLOCKS_IN_COMMAND) {

            String [] conditions = blocksCommand[1].split(SEPARATE);
            RowFactory rowFactory = new RowFactory(conditions);
            conditionRow = rowFactory.createConditionRow();
        }

        dbManager.delete(nameTable, conditionRow);

        view.write("В таблице " + nameTable + " были удалены строки!");
    }

    private boolean validateCommand() {

        final int minCountArguments = 2;
        final int countParametersInFieldFirstBlock = 2;
        final int countParametersInFieldSecondBlock = 3;
        boolean isValidateCommand;

        isValidateCommand = !StringUtil.isEmpty(textCommand);
        isValidateCommand = isValidateCommand && textCommand.startsWith("delete-row|");
        isValidateCommand = isValidateCommand && textCommand.split(SEPARATE).length >= minCountArguments;

        String [] blocksCommand = textCommand.split(IF_SEPARATE);

        isValidateCommand = isValidateCommand && (blocksCommand.length == 1 || blocksCommand.length == 2);

        String [] argumentsFirstBlock = blocksCommand[0].split(SEPARATE);
        String [] argumentsSecondBlock = blocksCommand.length == COUNT_BLOCKS_IN_COMMAND
                ? blocksCommand[1].split(SEPARATE) : new String[0];

        isValidateCommand = isValidateCommand &&
                argumentsFirstBlock.length % countParametersInFieldFirstBlock == 0;
        isValidateCommand = isValidateCommand &&
                argumentsSecondBlock.length % countParametersInFieldSecondBlock == 0;

        return isValidateCommand;
    }
}
