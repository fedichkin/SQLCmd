package ru.fedichkindenis.sqlcmd.controller.commands;

public class ExitException extends RuntimeException {

    ExitException(String message) {
        super(message);
    }
}
