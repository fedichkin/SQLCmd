package ru.fedichkindenis.sqlcmd.model;

public interface BuilderQuery {

    String getClassDriver();
    String getUrlConnection(String hostname, String port, String dbName);
    String getQueryListTable();
    String getQueryDataTable(String tableName);
    String getQueryClearTable(String tableName);
    String getQueryDeleteTable(String tableName);
    String getQueryInsertRow(String tableName, DataRow dataRow);
    String getQueryUpdateRow(String tableName, DataRow dataRow, ConditionRow conditionRow);
    String getQueryDeleteRow(String tableName, ConditionRow conditionRow);
    String getQueryCreateTable(String tableName, CreateRow createRow);
}
