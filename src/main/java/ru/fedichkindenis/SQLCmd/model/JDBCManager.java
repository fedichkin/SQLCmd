package ru.fedichkindenis.SQLCmd.model;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Денис on 15.07.2016.
 *
 * Менеджер базы данных JDBC
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

        if(connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    @Override
    public List<String> listTable() {
        List<String> listTable = new LinkedList<>();

        String queryStr = "select table_name as nameTable " +
                "from information_schema.tables " +
                "where table_schema = 'public' " +
                "order by table_name";

        try (PreparedStatement statement = connection.prepareStatement(queryStr);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                listTable.add(resultSet.getString("nameTable"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return listTable;
    }

    @Override
    public List<DataRow> dataTable(String tableName) {

        List<DataRow> dataRows = new LinkedList<>();

        String queryStr = "select * from " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(queryStr);
             ResultSet resultSet = statement.executeQuery()) {

            ResultSetMetaData rsmd = resultSet.getMetaData();

            while (resultSet.next()) {

                DataRow dataRow = new DataRow();

                for (int i = 0; i < rsmd.getColumnCount(); i++) {

                    dataRow.add(rsmd.getColumnName(i + 1), resultSet.getObject(i + 1));
                }

                dataRows.add(dataRow);
            }

            if (dataRows.size() == 0) {

                DataRow dataRow = new DataRow();

                for (int i = 0; i < rsmd.getColumnCount(); i++) {

                    dataRow.add(rsmd.getColumnName(i + 1), "");
                }

                dataRows.add(dataRow);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return dataRows;
    }

    @Override
    public void clearTable(String tableName) {

        String queryStr = "delete from " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void insert(String tableName, DataRow dataRow) {

        String queryStr = "insert into " + tableName + "(";

        for(String nameFiled : dataRow.getListNameField()) {

            queryStr = queryStr + nameFiled + ",";
        }

        queryStr = queryStr.substring(0, queryStr.length() - 1) + ")";
        queryStr = queryStr + " values (";

        for(int i = 0; i < dataRow.getCountField(); i++) {

            queryStr = queryStr + "?,";
        }

        queryStr = queryStr.substring(0, queryStr.length() - 1) + ")";

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            int index = 1;
            for(Object valueField : dataRow.getListValueField()) {

                statement.setObject(index++, valueField);
            }

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(String tableName, DataRow dataRow, ConditionRow conditionRow) {

        String queryStr = "update " + tableName + " set ";

        for(String nameField : dataRow.getListNameField()) {

            queryStr = queryStr + nameField + " = ?,";
        }

        queryStr = queryStr.substring(0, queryStr.length() - 1);
        queryStr = queryStr + " where 1 = 1 ";

        Iterator<String> conditionIterator = conditionRow.getListConditionField().iterator();
        for(String nameField : conditionRow.getListNameField()) {

            queryStr = queryStr + " and " + nameField + conditionIterator.next() + "?";
        }

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            int index = 1;
            for(Object valueField : dataRow.getListValueField()) {

                statement.setObject(index++, valueField);
            }

            for(Object valueField : conditionRow.getListValueField()) {

                statement.setObject(index++, valueField);
            }

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(String tableName, Integer id) {

        String queryStr = "delete from " + tableName + " where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public boolean isConnect() {

        return connection != null;
    }
}
