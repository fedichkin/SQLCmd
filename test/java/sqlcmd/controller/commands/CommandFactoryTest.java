package sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import ru.fedichkindenis.sqlcmd.controller.commands.*;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
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
