package ru.fedichkindenis.sqlcmd.view;

public interface View {

    void write(String message);
    String read();
    void close();
}
