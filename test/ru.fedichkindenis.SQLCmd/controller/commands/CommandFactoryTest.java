package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import ru.fedichkindenis.SQLCmd.controller.Commands.CommandFactory;
import ru.fedichkindenis.SQLCmd.controller.Commands.Connect;
import ru.fedichkindenis.SQLCmd.controller.Commands.Exit;
import ru.fedichkindenis.SQLCmd.controller.Commands.ListTable;
import ru.fedichkindenis.SQLCmd.controller.Commands.Unsupported;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
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
            commandFactory = new CommandFactory(dbManager, viewDecorator, null);
            commandFactory.createCommand();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Override
    @Test
    public void testCorrectCommandFormat() {

        String [] textCommands = {"connect|1|1|1|1|1", "exit", "list-table", "bla"};
        Class [] commands = {Connect.class, Exit.class, ListTable.class, Unsupported.class};

        for(int index = 0; index < textCommands.length; index++) {

            commandFactory = new CommandFactory(dbManager, viewDecorator, textCommands[index]);
            Command command = commandFactory.createCommand();

            assertEquals(command.getClass(), commands[index]);
        }
    }
}
