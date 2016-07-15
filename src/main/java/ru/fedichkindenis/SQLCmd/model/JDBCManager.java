package ru.fedichkindenis.SQLCmd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Денис on 15.07.2016.
 */
public class JDBCManager implements DBManager {

    private Connection connection;

    @Override
    public void connect(String hostname, String port, String dbName, String username, String password) {

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":" + port + "/" + dbName,
                    username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void disconnect() {

    }

    @Override
    public String listTable() {
        return null;
    }

    @Override
    public List<DataList> dataTable(String tableName) {
        return null;
    }

    @Override
    public void clearTable(String tableName) {

    }

    @Override
    public void insert(String tableName, DataList dataList) {

    }

    @Override
    public void update(String tableName, Integer id, DataList dataList) {

    }

    @Override
    public void delete(String tableName, Integer id) {

    }

    private boolean isConnect() {

        return connection != null;
    }
}
