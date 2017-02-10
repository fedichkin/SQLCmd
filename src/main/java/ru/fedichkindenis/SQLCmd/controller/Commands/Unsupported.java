package ru.fedichkindenis.SQLCmd.controller.Commands;

/**
 * Команда обрабатывает не поддерживающиеся команды
 */
public class Unsupported implements Command {

    @Override
    public void execute() {

        throw new IllegalArgumentException("Указан не верный формат команды");
    }
}
