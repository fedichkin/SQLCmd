package ru.fedichkindenis.SQLCmd.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Денис on 05.06.2016.
 */
public class PostgreSql extends Connect {

    private Connection connection = null;

    public PostgreSql(String hostname, Integer port, String nameBd, String username, String password) {

        this.hostname = hostname;
        this.port = port;
        this.nameBd = nameBd;
        this.username = username;
        this.password = password;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port + "/" + nameBd,
                    username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnect() {

        return connection;
    }

    public void closeConnect(){

        try {
            if(connection != null && !connection.isClosed()){

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
