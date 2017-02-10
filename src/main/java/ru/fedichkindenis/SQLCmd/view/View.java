package ru.fedichkindenis.SQLCmd.view;

public interface View {

    void write(String message);
    String read();
    void close();
}
