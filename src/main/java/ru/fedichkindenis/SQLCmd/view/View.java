package ru.fedichkindenis.SQLCmd.view;

import ru.fedichkindenis.SQLCmd.model.DataMap;

import java.util.List;

/**
 * Created by Денис on 11.07.2016.
 */
public interface View {

    void write(String message);
    String read();
    void close();
}
