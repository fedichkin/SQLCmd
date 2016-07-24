package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.Connect;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by Денис on 23.07.2016.
 *
 * Тесты для команды connect
 */

public class ConnectTest {

    DBManager manager;
    View view;
    Command command;

    @Before
    public void setup() {
        manager = mock(DBManager.class);
        view = mock(View.class);
    }

    @Test
    public void testIncorrectCommandFormat() {
        try {
            command = new Connect(manager, view, "connect|d|12|fg|ere");
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Test
    public void testCorrectCommandFormat() {

        command = new Connect(manager, view, "connect|d|12|fg|ere|12");
        command.execute();
    }

    @Test
    public void testConnect() {

        command = new Connect(manager, view, "connect|localhost|5433|cmd|postgres|mac");
        command.execute();

        shouldPrint("[Соединение установлено!]");
    }

    private void shouldPrint(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
