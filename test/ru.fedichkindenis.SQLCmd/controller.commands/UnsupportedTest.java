package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Test;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.controller.Commands.Unsupported;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.View;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Денис on 26.07.2016.
 */
public class UnsupportedTest implements CommandTest {

    private Command command;

    @Override
    public void setup() {

    }

    @Override
    public void testIncorrectCommandFormat() {

    }

    @Override
    @Test
    public void testCorrectCommandFormat() {

        try {
            command = new Unsupported();
            command.execute();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Указан не верный формат команды", e.getMessage());
        }
    }
}
