package ru.fedichkindenis.sqlcmd.controller.commands;

/**
 * Команда обрабатывает не поддерживающиеся команды
 */
public class Unsupported implements Command {

    @Override
    public void execute() {

        throw new IllegalArgumentException("Указан не верный формат команды");
    }
}
