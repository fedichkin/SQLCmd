package ru.fedichkindenis.SQLCmd.model;

import java.util.List;

/**
 * Created by Денис on 11.07.2016.
 */
public interface DBManager {

    void connect(String hostname, String port,  String dbName, String username, String password);
    void disconnect();
    List<String> listTable();
    List<DataMap> dataTable(String tableName);
    void clearTable(String tableName);
    void insert(String tableName, DataMap dataMap);
    void update(String tableName, Integer id, DataMap dataMap);
    void delete(String tableName, Integer id);
}
