package ru.fedichkindenis.SQLCmd.model;

import java.util.List;

/**
 * Интерфейс управления базами данных
 */
public interface DBManager {

    void connect(String hostname, String port,  String dbName, String username, String password);
    void disconnect();
    List<String> listTable();
    List<DataRow> dataTable(String tableName);
    void clearTable(String tableName);
    void deleteTable(String tableName);
    void insert(String tableName, DataRow dataRow);
    void update(String tableName, DataRow dataRow, ConditionRow conditionRow);
    void delete(String tableName, ConditionRow conditionRow);
    void create(String tableName, CreateRow createRow);
    void userQuery(String textQuery);
}
