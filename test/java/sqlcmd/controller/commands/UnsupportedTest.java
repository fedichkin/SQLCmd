package ru.fedichkindenis.sqlcmd.controller.commands;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Денис on 26.07.2016.
 *
 * Класс для тестирования команды unsupported
 */
public class UnsupportedTest implements CommandTest {

    @Override
    public void setup() {

    }

    @Override
    public void testIncorrectCommandFormat() {

    }

    @Override
    @Test
    public void testCorrectCommandFormat() throws Exception {

        try {
            //given
            Command command = new Unsupported();
            //when
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }
}
