package ru.fedichkindenis.SQLCmd.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
    public void testRead() {

        String expected = "Hello World!!!";
        when(view.read()).thenReturn(expected);

        viewDecorator = new TableConsoleView(view);
        String actual = viewDecorator.read();

        assertEquals(expected, actual);
    }

    @Test
    public void testWrite() {

        String expected = "Hello World!!!";

        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(expected);

        shouldPrintView(Collections.singletonList(expected));
    }

    @Test
    public void testClose() {

        viewDecorator = new TableConsoleView(view);
        viewDecorator.close();

        verify(view).close();
    }

    @Test
    public void testWriteListIfListNull() {

        try {
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(null, AlignWrite.VERTICAL);

            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void testWriteListIfListEmpty() {

        try {
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(new LinkedList<>(), AlignWrite.VERTICAL);

            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void testWriteListHorizontal() {

        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(Arrays.asList("table1", "table22", "table333"), AlignWrite.HORIZONTAL);

        List<String> expected = Arrays.asList(
                "╔════════╦════════╦════════╗",
                "║table1  ║table22 ║table333║",
                "╚════════╩════════╩════════╝");
        shouldPrintView(expected);
    }

    @Test
    public void testWriteListVertical() {

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

    private void shouldPrintView(List<String> expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues());
    }
}
