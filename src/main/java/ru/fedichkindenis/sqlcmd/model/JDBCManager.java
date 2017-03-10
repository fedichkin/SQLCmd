package ru.fedichkindenis.sqlcmd.model;

import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
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
        exceptionConnect();

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
        exceptionConnect();

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
        exceptionConnect();

        String queryStr = "truncate table " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteTable(String tableName) {
        exceptionConnect();

        String queryStr = "drop table " + tableName;

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void insert(String tableName, DataRow dataRow) {
        exceptionConnect();

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
        exceptionConnect();

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

            setParameters(conditionRow, dataRow, statement);
            statement.execute();

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void delete(String tableName, ConditionRow conditionRow) {
        exceptionConnect();

        String queryStr = "delete from " + tableName + " where 1 = 1";

        Iterator<String> conditionIterator = conditionRow.getListConditionField().iterator();
        for(String nameField : conditionRow.getListNameField()) {

            queryStr = queryStr + " and " + nameField + conditionIterator.next() + "?";
        }

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            setParameters(conditionRow, new DataRow(), statement);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createTable(String tableName, CreateRow createRow) {
        exceptionConnect();

        String queryStr = "create table " + tableName + " (";

        Iterator<Field> fieldIterator = createRow.getIteratorField();

        while (fieldIterator.hasNext()) {

            CreateField field = (CreateField)fieldIterator.next();

            queryStr = queryStr + field.getNameField() + " ";
            queryStr = queryStr + field.getTypeField() + " ";
            queryStr = queryStr + (field.isNotNull() ? " NOT NULL" : "");
            queryStr = queryStr + ",";
        }

        if(queryStr.charAt(queryStr.length() - 1) == ',') {

            queryStr = queryStr.substring(0, queryStr.length() - 1);
        }

        queryStr = queryStr + ")";

        try (PreparedStatement statement = connection.prepareStatement(queryStr)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void userQuery(String textQuery) {
        exceptionConnect();

        try (PreparedStatement statement = connection.prepareStatement(textQuery)) {

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void setParameters(ConditionRow conditionRow, DataRow dataRow,
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

            throw new RuntimeException("Соединение не установлено! Установите соединение!");
        }
    }
}
