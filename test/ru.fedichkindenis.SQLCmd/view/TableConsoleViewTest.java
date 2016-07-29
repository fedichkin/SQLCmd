package ru.fedichkindenis.SQLCmd.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
    public void writeListIfListNull() {

        try {
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(null, AlignWrite.VERTICAL);

            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void writeListIfListEmpty() {

        try {
            viewDecorator = new TableConsoleView(view);
            viewDecorator.write(new LinkedList<>(), AlignWrite.VERTICAL);

            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {

            assertEquals("Не указан список значений", e.getMessage());
        }
    }

    @Test
    public void writeListHorizontal() {

        viewDecorator = new TableConsoleView(view);
        viewDecorator.write(Arrays.asList("table1", "table22", "table333"), AlignWrite.HORIZONTAL);

        List<String> expected = Arrays.asList(
                "╔════════╦════════╦════════╗",
                "║table1  ║table22 ║table333║",
                "╚════════╩════════╩════════╝");
        shouldPrintView(expected);
    }

    @Test
    public void writeListVertical() {

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
