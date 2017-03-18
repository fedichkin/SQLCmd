package ru.fedichkindenis.sqlcmd.model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Менеджер базы данных JDBC
 */
public class JDBCManager implements DBManager {

    private Connection connection;
    private BuilderQuery builderQuery;

    public JDBCManager(BuilderQuery builderQuery) {
        this.builderQuery = builderQuery;
    }

    @Override
    public void connect(String hostname, String port, String dbName, String username, String password) {

        try {
            Class.forName(builderQuery.getClassDriver());
            connection = DriverManager.getConnection(
                    builderQuery.getUrlConnection(hostname, port, dbName), username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void disconnect() {

        if(connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<String> listTable() {
        exceptionConnect();

        List<String> listTable = new LinkedList<>();

        String queryStr = builderQuery.getQueryListTable();

        try (PreparedStatement statement = connection.prepareStatement(queryStr);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                listTable.add(resultSet.getString("nameTable"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        return listTable;
    }

    @Override
    public List<DataRow> dataTable(String tableName) {
        exceptionConnect();

        List<DataRow> dataRows = new LinkedList<>();

        String queryStr = builderQuery.getQueryDataTable(tableName);

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
            throw new RuntimeException(e.getMessage(), e);
        }

        return dataRows;
    }

    @Override
    public void clearTable(String tableName) {
        exceptionConnect();

        String queryStr = builderQuery.getQueryClearTable(tableName);

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteTable(String tableName) {
        exceptionConnect();

        String queryStr = builderQuery.getQueryDeleteTable(tableName);

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void insert(String tableName, DataRow dataRow) {
        exceptionConnect();

        String queryStr = builderQuery.getQueryInsertRow(tableName, dataRow);

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            int index = 1;
            for(Object valueField : dataRow.getListValueField()) {

                statement.setObject(index++, valueField);
            }

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void update(String tableName, DataRow dataRow, ConditionRow conditionRow) {
        exceptionConnect();

        String queryStr = builderQuery.getQueryUpdateRow(tableName, dataRow, conditionRow);

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            setParameters(conditionRow, dataRow, statement);
            statement.execute();

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String tableName, ConditionRow conditionRow) {
        exceptionConnect();

        String queryStr = builderQuery.getQueryDeleteRow(tableName, conditionRow);

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            setParameters(conditionRow, new DataRow(), statement);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void createTable(String tableName, CreateRow createRow) {
        exceptionConnect();

        String queryStr = builderQuery.getQueryCreateTable(tableName, createRow);

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void userQuery(String textQuery) {
        exceptionConnect();

        try (PreparedStatement statement = connection.prepareStatement(textQuery)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void setParameters(ConditionRow conditionRow, DataRow dataRow,
                               PreparedStatement statement) throws SQLException {

        int index = 1;
        for(Object valueField : dataRow.getListValueField()) {

            statement.setObject(index++, valueField);
        }

        for(Object valueField : conditionRow.getListValueField()) {

            statement.setObject(index++, valueField);
        }
    }

    public boolean isConnect() {

        return connection != null;
    }

    private void exceptionConnect() {

        if(!isConnect()) {

            throw new RuntimeException("Соединение не установлено! " +
                    "Установите соединение!");
        }
    }
}
