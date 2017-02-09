package ru.fedichkindenis.SQLCmd.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Денис on 27.07.2016.
 *
 * Класс для тестирования контролера
 */
public class ControllerTest {

    private ViewDecorator viewDecorator;
    private DBManager dbManager;
    private Controller controller;

    @Before
    public void setup() {
        dbManager = mock(DBManager.class);
        viewDecorator = mock(ViewDecorator.class);
    }

    @Test
    public void testCommandController() {

        when(viewDecorator.read()).thenReturn("error").thenReturn("exit");

        controller = new Controller(viewDecorator, dbManager);
        controller.run();

        List<String> expectedList = Arrays.asList(
                "Приветствую тебя пользователь!",
                "Для начала работы с ситемой установи соединение с базой данных с помощью команды: ",
                "connect|host|port|dbName|userName|password",
                "",
                "Введите команду (help для справки): ",
                "Произошла ошибка: Указан не верный формат команды",
                "",
                "Введите команду (help для справки): ",
                "До свидания!"
        );
        shouldPrintView(expectedList);
    }

    private void shouldPrintView(List<String> expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(viewDecorator, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues());
    }
}
