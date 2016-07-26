package ru.fedichkindenis.SQLCmd.controller.commands;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Денис on 26.07.2016.
 */
public interface CommandTest {
    @Before
    void setup();

    @Test
    void testIncorrectCommandFormat();

    @Test
    void testCorrectCommandFormat();
}
