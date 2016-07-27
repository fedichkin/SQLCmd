package ru.fedichkindenis.SQLCmd.controller.commands;

/**
 * Created by Денис on 26.07.2016.
 *
 * Интерфейс для классов тестирующие команды
 */

interface CommandTest {

    void setup();

    void testIncorrectCommandFormat();

    void testCorrectCommandFormat();
}
