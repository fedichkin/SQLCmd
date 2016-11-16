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
    void insert(String tableName, DataRow dataRow);
    void update(String tableName, Integer id, DataRow dataRow);
    void delete(String tableName, Integer id);
}
