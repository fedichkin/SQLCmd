package ru.fedichkindenis.sqlcmd.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.fedichkindenis.sqlcmd.model.DataRow;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Created by Денис on 29.07.2016.
 *
 * Клас для тестирования табличного вывода в консоль
 */
public class TableConsoleViewTest {

    private View view;
    private ViewDecorator viewDecorator;

    @Before
    public void setup() {
        view = mock(View.class);
    }

    @Test
    public void readTes() {
        //given
        String expected = "Hello World!!!";
        when(view.read()).thenReturn(expected);
        //when
        viewDecorator = new TableConsoleView(view);
        String actual = viewDecorator.read();
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void writeTest() {
        //given
        String expected = "Hello World!!!";
        //when
        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(expected);
        //then
        shouldPrintView(Collections.singletonList(expected));
    }

    @Test
    public void closeTest() {
        //when
        viewDecorator = new TableConsoleView(view);
        viewDecorator.close();
        //then
        verify(view).close();
    }

    @Test
    public void writeListIfListNullTest() {

        try {
            //when
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(null, AlignWrite.VERTICAL);
            //then
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void writeListIfListEmptyTest() {

        try {
            //when
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(new LinkedList<>(), AlignWrite.VERTICAL);
            //then
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void writeListHorizontalTest() {
        //given
        List<String> expected = Arrays.asList(
                "╔════════╦════════╦════════╗",
                "║table1  ║table22 ║table333║",
                "╚════════╩════════╩════════╝");
        //when
        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(Arrays.asList("table1", "table22", "table333"), AlignWrite.HORIZONTAL);
        //than
        shouldPrintView(expected);
    }

    @Test
    public void writeListVerticalTest() {

        //given
        List<String> expected = Arrays.asList(
                "╔════════╗",
                "║table1  ║",
                "╠════════╣",
                "║table22 ║",
                "╠════════╣",
                "║table333║",
                "╚════════╝");
        //when
        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(Arrays.asList("table1", "table22", "table333"), AlignWrite.VERTICAL);
        //than
        shouldPrintView(expected);
    }

    @Test
    public void writeDataRowTest() {

        //given
        List<String> expected = Arrays.asList(
                "╔════╦════╗",
                "║id  ║name║",
                "╠════╬════╣",
                "║1   ║user║",
                "╚════╩════╝");

        viewDecorator = new TableConsoleView(view);
        DataRow dataRow = new DataRow();
        dataRow.add("id", 1);
        dataRow.add("name", "user");
        //when
        viewDecorator.write(dataRow);
        //then
        shouldPrintView(expected);
    }

    @Test
    public void writeListDataRow() {

        //given
        List<String> expected = Arrays.asList(
                "╔═════╦═════╗",
                "║id   ║name ║",
                "╠═════╬═════╣",
                "║1    ║user ║",
                "║2    ║admin║",
                "╚═════╩═════╝");

        viewDecorator = new TableConsoleView(view);
        DataRow dataRow1 = new DataRow();
        dataRow1.add("id", 1);
        dataRow1.add("name", "user");
        DataRow dataRow2 = new DataRow();
        dataRow2.add("id", 2);
        dataRow2.add("name", "admin");
        //when
        viewDecorator.write(Arrays.asList(dataRow1, dataRow2));
        //then
        shouldPrintView(expected);
    }

    private void shouldPrintView(List<String> expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues());
    }
}
