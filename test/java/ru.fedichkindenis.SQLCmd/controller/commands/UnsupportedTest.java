package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Test;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.Unsupported;

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
            Command command = new Unsupported();
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }
}
