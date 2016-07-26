package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.ListTable;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by Денис on 25.07.2016.
 *
 * Класс для тестирования команды list-table
 */
public class ListTableTest implements CommandTest {

    private DBManager dbManager;
    private ViewDecorator viewDecorator;
    private Command command;

    @Captor
    private ArgumentCaptor<List<String>> listTable;

    @Override
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        dbManager = mock(DBManager.class);
        viewDecorator = mock(ViewDecorator.class);
    }

    @Override
    @Test
    public void testIncorrectCommandFormat() {
        try {
            command = new ListTable(dbManager, viewDecorator, "list_table");
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Override
    @Test
    public void testCorrectCommandFormat() {

        List<String> data = new LinkedList<>();
        data.add("user");
        data.add("user_info");

        when(dbManager.listTable()).thenReturn(data);

        command = new ListTable(dbManager, viewDecorator, "list-table");
        command.execute();

        shouldPrintViewDecarator("[[user, user_info]]");
    }

    private void shouldPrintViewDecarator(String expected) {
        verify(viewDecorator, atMost(2)).write(listTable.capture(), anyObject());
        assertEquals(expected, listTable.getAllValues().toString());
    }
}
