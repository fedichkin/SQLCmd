package ru.fedichkindenis.sqlcmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.model.DataRow;
import ru.fedichkindenis.sqlcmd.view.ViewDecorator;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Класс для тестирования команды data-table
 */
public class DataTableTest implements CommandTest {

    private DBManager dbManager;
    private ViewDecorator viewDecorator;
    private Command command;

    @Captor
    private ArgumentCaptor<List<DataRow>> listDataRow;

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
            command = new DataTable(dbManager, viewDecorator,
                    "data-table|");
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
        List<DataRow> dataRowList = new LinkedList<>();
        DataRow firstRow = new DataRow();
        firstRow.add("id", 1);
        firstRow.add("name", "admin");
        DataRow secondRow = new DataRow();
        secondRow.add("id", 2);
        secondRow.add("name", "user");
        dataRowList.add(firstRow);
        dataRowList.add(secondRow);

        when(dbManager.dataTable("usr")).thenReturn(dataRowList);

        command = new DataTable(dbManager, viewDecorator,
                "data-table|usr");
        //when
        command.execute();
        //then
        shouldPrintViewDecarator("[[{{nameField: id, value: 1}{nameField: name, value: admin}}, " +
                "{{nameField: id, value: 2}{nameField: name, value: user}}]]");
    }

    @Test
    public void testNullTable() throws Exception {

        //given
        List<DataRow> dataRowList = new LinkedList<>();
        DataRow firstRow = new DataRow();
        dataRowList.add(firstRow);

        when(dbManager.dataTable("usr")).thenReturn(dataRowList);

        command = new DataTable(dbManager, viewDecorator,
                "data-table|usr");
        //when
        command.execute();
        //then
        shouldPrintViewDecarator("[]");
    }

    private void shouldPrintViewDecarator(String expected) {
        verify(viewDecorator, atMost(2)).write(listDataRow.capture());
        assertEquals(expected, listDataRow.getAllValues().toString());
    }
}
