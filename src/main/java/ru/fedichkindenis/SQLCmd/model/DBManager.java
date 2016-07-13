package ru.fedichkindenis.SQLCmd.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Денис on 11.07.2016.
 */
public interface DBManager {

    void connect(String hostname, String port,  String dbName, String username, String password);
    void disconnect();
    String listTable();
    List<DataList> dataTable(String tableName);
    void clearTable(String tableName);
    void insert(String tableName, DataList dataList);
    void update(String tableName, Integer id, DataList dataList);
    void delete(String tableName, Integer id);
}
