package ru.fedichkindenis.sqlcmd.controller.commands;

/**
 * Created by Денис on 26.07.2016.
 *
 * Интерфейс для классов тестирующие команды
 */

interface CommandTest {

    void setup();

    void testIncorrectCommandFormat() throws Exception;

    void testCorrectCommandFormat() throws Exception;
}
