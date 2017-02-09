package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.Help;
import ru.fedichkindenis.SQLCmd.view.ViewDecorator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Класс для тестирования команды help
 */
public class HelpTest implements CommandTest {

    private ViewDecorator viewDecorator;
    private Command command;

    @Override
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        viewDecorator = mock(ViewDecorator.class);
    }

    @Override
    @Test
    public void testIncorrectCommandFormat() {
        try {
            command = new Help(viewDecorator, "helpt");
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }

    @Override
    @Test
    public void testCorrectCommandFormat() {

        command = new Help(viewDecorator, "help");
        command.execute();

        shouldPrintView("[Команда: disconnect, " +
                "\tОписание команды: Команда для разрыва соединения с базой данных, " +
                "\tФормат команды: disconnect, " +
                "\tПример команды: disconnect, " +
                "Команда: insert-row, " +
                "\tОписание команды: Команда для вставки строк в таблицу, " +
                "\tФормат команды: insert-row|наименование таблицы|наименование поля1|значение1..., " +
                "\tПример команды: insert-row|usr|id|1|login|user|password|qwerty, " +
                "Команда: user-query, " +
                "\tОписание команды: Команда для выполнения пользовательского запроса, " +
                "\tФормат команды: user-query|<пользовательский запрос, " +
                "\tПример команды: user-query|<insert into usr (id, login, password) values(6, \"us\", \"1234\"), " +
                "\tДополнительная информация: В текущей версии select запросы не доступны, " +
                "Команда: delete-row, " +
                "\tОписание команды: Команда для удаления строк отобраных по условию, " +
                "\tФормат команды: delete-row|наименование таблицы|!IF|поле условия1|оператор условия1|значение условия1|..., " +
                "\tПример команды: delete-row|usr|!IF|id|<|5, " +
                "\tДополнительная информация: В данной версии программы можно использовать только такие операторы для сравнения: =, <>, >, <, >=, <=. Условие обязательно или воспользуйтесь командой clear-table, " +
                "Команда: clear-table, " +
                "\tОписание команды: Команда для очистки таблицы, " +
                "\tФормат команды: clear-table|наименование таблицы, " +
                "\tПример команды: clear-table|usr, " +
                "Команда: exit, " +
                "\tОписание команды: Команда для выхода из приложения, " +
                "\tФормат команды: exit, " +
                "\tПример команды: exit, " +
                "Команда: help, " +
                "\tОписание команды: Команда для вызова справки, " +
                "\tФормат команды: help, " +
                "\tПример команды: help, " +
                "Команда: data-table, " +
                "\tОписание команды: Команда для вывода данных таблицы, " +
                "\tФормат команды: data-table|наименование таблицы, " +
                "\tПример команды: data-table|usr, " +
                "Команда: create-table, " +
                "\tОписание команды: Команда для создания таблицы, " +
                "\tФормат команды: create-table|наименование таблицы|поле 1| тип поля 1| признак обязательного заполнения поля 1|..., " +
                "\tПример команды: create-table|my_table|id|bigint|true|name|varchar|false, " +
                "\tДополнительная информация: В данной версии программы у поля можно указать только тип и признак обязательного заполнения. Блок с полями не обязателен., " +
                "Команда: update-row, " +
                "\tОписание команды: Команда для обновления данных в строках отобраных по условию, " +
                "\tФормат команды: update-row|наименование таблицы|наименование поля1|значение поля 1|...|!IF|поле условия1|оператор условия1|значение условия1|..., " +
                "\tПример команды: update-row|usr|password|1111|!IF|id|=|3, " +
                "\tДополнительная информация: В данной версии программы можно использовать только такие операторы для сравнения: =, <>, >, <, >=, <=. Блок с уловием может отсутствовать, " +
                "Команда: connect, " +
                "\tОписание команды: Команда для установки соединения с базой данных, " +
                "\tФормат команды: connect|хост|порт|имя базы|имя пользователя|пароль, " +
                "\tПример команды: connect|localhost|5433|test|root|1234, " +
                "Команда: list-table, " +
                "\tОписание команды: Команда для вывода списка таблиц, " +
                "\tФормат команды: list-table, " +
                "\tПример команды: list-table, Команда: delete-table, " +
                "\tОписание команды: Команда для удаления таблицы, " +
                "\tФормат команды: delete-table|имя таблицы, " +
                "\tПример команды: delete-table|usr]");
    }

    private void shouldPrintView(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(viewDecorator, atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
