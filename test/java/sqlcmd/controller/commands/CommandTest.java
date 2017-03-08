package sqlcmd.controller.commands;

/**
 * Интерфейс для классов тестирующие команды
 */

interface CommandTest {

    void setup();

    void testIncorrectCommandFormat() throws Exception;

    void testCorrectCommandFormat() throws Exception;
}
