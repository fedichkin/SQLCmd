package ru.fedichkindenis.SQLCmd.controller.Commands;

/**
 * Created by Денис on 16.07.2016.
 *
 * Команда обрабатывает не поддерживающиеся команды
 */
public class Unsupported implements Command {

    @Override
    public void execute() {

        throw new IllegalArgumentException("Указан не верный формат команды");
    }
}
