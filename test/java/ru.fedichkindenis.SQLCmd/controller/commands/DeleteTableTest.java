package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.DeleteTable;
import ru.fedichkindenis.SQLCmd.controller.Commands.ListTable;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Класс для тестирования команды delete-table
 */
public class DeleteTableTest implements CommandTest {

    private DBManager dbManager;
    private ViewDecorator viewDecorator;
    private Command command;

    @Override
    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

        dbManager = mock(DBManager.class);
        viewDecorator = mock(ViewDecorator.class);
    }

    @Override
    @Test
    public void testIncorrectCommandFormat() throws Exception {

        try {
            //given
            command = new DeleteTable(dbManager, viewDecorator, "delete-table|");
            //when
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Override
    @Test
    public void testCorrectCommandFormat() throws Exception {

        //given
        command = new DeleteTable(dbManager, viewDecorator, "delete-table|user-info");
        //when
        command.execute();
        //then
        shouldPrintView("[Таблица user-info была удалена!]");
    }

    private void shouldPrintView(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(viewDecorator, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
