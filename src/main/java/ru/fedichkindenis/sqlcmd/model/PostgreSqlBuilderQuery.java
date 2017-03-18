package ru.fedichkindenis.sqlcmd.model;

import java.util.Iterator;

public class PostgreSqlBuilderQuery implements BuilderQuery {

    @Override
    public String getClassDriver() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getUrlConnection(String hostname, String port, String dbName) {
        return "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName;
    }

    @Override
    public String getQueryListTable() {
        return "select table_name as nameTable " +
                "from information_schema.tables " +
                "where table_schema = 'public' " +
                "order by table_name";
    }

    @Override
    public String getQueryDataTable(String tableName) {
        return "select * from " + tableName;
    }

    @Override
    public String getQueryClearTable(String tableName) {
        return "truncate table " + tableName;
    }

    @Override
    public String getQueryDeleteTable(String tableName) {
        return "drop table " + tableName;
    }

    @Override
    public String getQueryInsertRow(String tableName, DataRow dataRow) {

        StringBuilder textQuery = new StringBuilder();

        textQuery.append("insert into ");
        textQuery.append(tableName);
        textQuery.append("(");

        for(String nameFiled : dataRow.getListNameField()) {

            textQuery.append(nameFiled);
            textQuery.append(",");
        }

        textQuery.deleteCharAt(textQuery.lastIndexOf(","));
        textQuery.append(")");
        textQuery.append(" values (");

        for(int i = 0; i < dataRow.getCountField(); i++) {

            textQuery.append("?,");
        }

        textQuery.deleteCharAt(textQuery.lastIndexOf(","));
        textQuery.append(")");

        return textQuery.toString();
    }

    @Override
    public String getQueryUpdateRow(String tableName, DataRow dataRow, ConditionRow conditionRow) {

        StringBuilder textQuery = new StringBuilder();

        textQuery.append("update ");
        textQuery.append(tableName);
        textQuery.append(" set ");

        for(String nameField : dataRow.getListNameField()) {

            textQuery.append(nameField);
            textQuery.append(" = ?,");
        }

        textQuery.deleteCharAt(textQuery.lastIndexOf(","));
        textQuery.append(" where 1 = 1 ");

        fillConditionBlock(conditionRow, textQuery);

        return textQuery.toString();
    }

    @Override
    public String getQueryDeleteRow(String tableName, ConditionRow conditionRow) {

        StringBuilder textQuery = new StringBuilder();

        textQuery.append("delete from ");
        textQuery.append(tableName);
        textQuery.append(" where 1 = 1");

        fillConditionBlock(conditionRow, textQuery);

        return textQuery.toString();
    }

    @Override
    public String getQueryCreateTable(String tableName, CreateRow createRow) {

        StringBuilder textQuery = new StringBuilder();

        textQuery.append("create table ");
        textQuery.append(tableName);
        textQuery.append(" (");

        Iterator<Field> fieldIterator = createRow.getIteratorField();
        while (fieldIterator.hasNext()) {

            CreateField field = (CreateField)fieldIterator.next();

            textQuery.append(field.getNameField());
            textQuery.append(" ");
            textQuery.append(field.getTypeField());
            textQuery.append(" ");
            textQuery.append(field.isNotNull() ? " NOT NULL" : "");
            textQuery.append(",");
        }

        if(textQuery.lastIndexOf(",") != -1) {
            textQuery.deleteCharAt(textQuery.lastIndexOf(","));
        }
        textQuery.append(")");

        return textQuery.toString();
    }

    private void fillConditionBlock(ConditionRow conditionRow, StringBuilder textQuery) {

        Iterator<String> conditionIterator = conditionRow.getListConditionField().iterator();
        for(String nameField : conditionRow.getListNameField()) {

            textQuery.append(" and ");
            textQuery.append(nameField);
            textQuery.append(conditionIterator.next());
            textQuery.append("?");
        }
    }
}
