package ru.fedichkindenis.SQLCmd.bd;

import java.sql.Connection;

/**
 * Created by Денис on 05.06.2016.
 */
public abstract class DataBase {

    String hostname;
    Integer port;
    String nameBd;
    String username;
    String password;

    public abstract Connection getConnect();
    public abstract void closeConnect();
}
