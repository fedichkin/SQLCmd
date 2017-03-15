package ru.fedichkindenis.sqlcmd.controller.commands;

public interface Command {

    String SEPARATE = "\\|";
    String IF_SEPARATE = "\\|!IF\\|";
    String QUERY_SEPARATE = "\\|<";

    void execute() throws Exception;
}
