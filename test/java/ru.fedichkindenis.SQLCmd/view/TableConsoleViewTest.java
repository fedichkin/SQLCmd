package ru.fedichkindenis.SQLCmd.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.fedichkindenis.SQLCmd.model.DataRow;

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

        String expected = "Hello World!!!";
        when(view.read()).thenReturn(expected);

        viewDecorator = new TableConsoleView(view);
        String actual = viewDecorator.read();

        assertEquals(expected, actual);
    }

    @Test
    public void writeTest() {

        String expected = "Hello World!!!";

        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(expected);

        shouldPrintView(Collections.singletonList(expected));
    }

    @Test
    public void closeTest() {

        viewDecorator = new TableConsoleView(view);
        viewDecorator.close();

        verify(view).close();
    }

    @Test
    public void writeListIfListNullTest() {

        try {
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(null, AlignWrite.VERTICAL);

            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void writeListIfListEmptyTest() {

        try {
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(new LinkedList<>(), AlignWrite.VERTICAL);

            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void writeListHorizontalTest() {

        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(Arrays.asList("table1", "table22", "table333"), AlignWrite.HORIZONTAL);

        List<String> expected = Arrays.asList(
                "╔════════╦════════╦════════╗",
                "║table1  ║table22 ║table333║",
                "╚════════╩════════╩════════╝");
        shouldPrintView(expected);
    }

    @Test
    public void writeListVerticalTest() {

        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(Arrays.asList("table1", "table22", "table333"), AlignWrite.VERTICAL);

        List<String> expected = Arrays.asList(
                "╔════════╗",
                "║table1  ║",
                "╠════════╣",
                "║table22 ║",
                "╠════════╣",
                "║table333║",
                "╚════════╝");
        shouldPrintView(expected);
    }

    @Test
    public void writeDataRowTest() {

        viewDecorator = new TableConsoleView(view);
        DataRow dataRow = new DataRow();
        dataRow.add("id", 1);
        dataRow.add("name", "user");

        viewDecorator.write(dataRow);

        List<String> expected = Arrays.asList(
                        "╔════╦════╗",
                        "║id  ║name║",
                        "╠════╬════╣",
                        "║1   ║user║",
                        "╚════╩════╝");

        shouldPrintView(expected);
    }

    @Test
    public void writeListDataRow() {

        viewDecorator = new TableConsoleView(view);
        DataRow dataRow1 = new DataRow();
        dataRow1.add("id", 1);
        dataRow1.add("name", "user");
        DataRow dataRow2 = new DataRow();
        dataRow2.add("id", 2);
        dataRow2.add("name", "admin");

        viewDecorator.write(Arrays.asList(dataRow1, dataRow2));

        List<String> expected = Arrays.asList(
                "╔═════╦═════╗",
                "║id   ║name ║",
                "╠═════╬═════╣",
                "║1    ║user ║",
                "║2    ║admin║",
                "╚═════╩═════╝");

        shouldPrintView(expected);
    }

    private void shouldPrintView(List<String> expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues());
    }
}
