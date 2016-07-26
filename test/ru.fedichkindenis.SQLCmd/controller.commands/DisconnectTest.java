package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.Connect;
import ru.fedichkindenis.SQLCmd.controller.Commands.Disconnect;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Денис on 26.07.2016.
 */
public class DisconnectTest implements CommandTest {

    private DBManager dbManager;
    private View view;
    private Command command;

    @Override
    @Before
    public void setup() {

        dbManager = mock(DBManager.class);
        view = mock(View.class);
    }

    @Override
    @Test
    public void testIncorrectCommandFormat() {
        try {
            command = new Disconnect(dbManager, view, "dissconnect");
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Override
    @Test
    public void testCorrectCommandFormat() {

        command = new Disconnect(dbManager, view, "disconnect");
        command.execute();

        shouldPrintView("[Соединение с базой разорванно]");
    }

    private void shouldPrintView(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
