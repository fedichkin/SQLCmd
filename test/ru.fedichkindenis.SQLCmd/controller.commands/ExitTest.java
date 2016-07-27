package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.Exit;
import ru.fedichkindenis.SQLCmd.controller.Commands.ExitException;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Денис on 26.07.2016.
 *
 * Тест для команды exit
 */
public class ExitTest implements CommandTest {

    private DBManager dbManager;
    private ViewDecorator viewDecorator;
    private Command command;

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
            command = new Exit(dbManager, viewDecorator, "exxit");
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Override
    @Test
    public void testCorrectCommandFormat() {

        try {
            command = new Exit(dbManager, viewDecorator, "exit");
            command.execute();

            shouldPrintView("[До свидания!]");

            fail("Expected ExitException");

        } catch (ExitException e) {
            //do nothing
        }
    }

    private void shouldPrintView(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(viewDecorator, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
