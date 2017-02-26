package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import ru.fedichkindenis.SQLCmd.controller.Commands.CommandFactory;
import ru.fedichkindenis.SQLCmd.controller.Commands.Connect;
import ru.fedichkindenis.SQLCmd.controller.Commands.Exit;
import ru.fedichkindenis.SQLCmd.controller.Commands.ListTable;
import ru.fedichkindenis.SQLCmd.controller.Commands.Unsupported;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.ClearTable;
import ru.fedichkindenis.SQLCmd.controller.Commands.DeleteTable;
import ru.fedichkindenis.SQLCmd.controller.Commands.InsertRow;
import ru.fedichkindenis.SQLCmd.controller.Commands.UpdateRow;
import ru.fedichkindenis.SQLCmd.controller.Commands.DeleteRow;
import ru.fedichkindenis.SQLCmd.controller.Commands.DataTable;
import ru.fedichkindenis.SQLCmd.controller.Commands.CreateTable;
import ru.fedichkindenis.SQLCmd.controller.Commands.UserQuery;
import ru.fedichkindenis.SQLCmd.controller.Commands.Help;
import ru.fedichkindenis.SQLCmd.controller.Commands.Disconnect;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Created by Денис on 26.07.2016.
 *
 * Класс для тестирования фабрики команд
 */
public class CommandFactoryTest implements CommandTest {

    private DBManager dbManager;
    private ViewDecorator viewDecorator;
    private CommandFactory commandFactory;

    @Override
    @Before
    public void setup() {
        dbManager = mock(DBManager.class);
        viewDecorator = mock(ViewDecorator.class);
    }

    @Override
    @Test
    public void testIncorrectCommandFormat() {

        try {
            //given
            commandFactory = new CommandFactory(dbManager, viewDecorator, null);
            //when
            commandFactory.createCommand();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Override
    @Test
    public void testCorrectCommandFormat() {

        //given
        String[] textCommands = {"connect|1|1|1|1|1", "exit", "list-table", "bla",
                "clear-table|usr", "delete-table|usr", "insert-row|usr", "update-row|usr",
                "delete-row|usr", "data-table|usr", "create-table|usr", "user-query|insert",
                "help", "disconnect"};
        Class[] commands = {Connect.class, Exit.class, ListTable.class, Unsupported.class,
                ClearTable.class, DeleteTable.class, InsertRow.class, UpdateRow.class,
                DeleteRow.class, DataTable.class, CreateTable.class, UserQuery.class,
                Help.class, Disconnect.class};

        for(int index = 0; index < textCommands.length; index++) {

            commandFactory = new CommandFactory(dbManager, viewDecorator, textCommands[index]);
            //when
            Command command = commandFactory.createCommand();
            //then
            assertEquals(command.getClass(), commands[index]);
        }
    }
}
