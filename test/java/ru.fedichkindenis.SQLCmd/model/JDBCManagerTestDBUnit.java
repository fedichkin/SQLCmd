package ru.fedichkindenis.SQLCmd.model;

import config.DBUnitConfig;
import config.JDBCProperties;
import org.dbunit.Assertion;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Денис on 03.08.2016.
 *
 * Тестирование работы с базой данных с использованием DBUnit
 */
public class JDBCManagerTestDBUnit extends DBUnitConfig {

    private JDBCManager jdbcManager;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("init-data.xml"));

        tester.setDataSet(beforeData);
        tester.onSetup();

        JDBCProperties jdbcProperties = new JDBCProperties("postgesql.config.properties");
        Properties properties = jdbcProperties.getProperties();
        jdbcManager = new JDBCManager();
        jdbcManager.connect(properties.getProperty("db.host"),
                properties.getProperty("db.port"),
                properties.getProperty("db.dbName"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }

    public JDBCManagerTestDBUnit(String name) {
        super(name);
    }

    public void testListTable() throws Exception {

        List<String> actual = jdbcManager.listTable();

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("init-data.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        Assertion.assertEquals(expectedData, actualData);
        Assert.assertEquals(Arrays.asList(expectedData.getTableNames()), actual);
    }

    public void testDataTable()  throws Exception {

        List<DataRow> actual = jdbcManager.dataTable("usr");

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("init-data.xml"));

        List<DataRow> expected = getDataMapList(expectedData, "usr");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    public void testInsert() throws Exception {

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("insert-data.xml"));

        List<DataRow> expected = getDataMapList(expectedData, "usr");

        DataRow newRow = new DataRow();
        newRow.add("id", 3);
        newRow.add("login", "super_user");
        newRow.add("password", "1q2w3e4r");
        jdbcManager.insert("usr", newRow);

        List<DataRow> actual = jdbcManager.dataTable("usr");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    public void testUpdate() throws Exception {

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("update-data.xml"));

        List<DataRow> expected = getDataMapList(expectedData, "usr");

        DataRow modifyRow = new DataRow();
        modifyRow.add("password", "QWERTY");

        ConditionRow conditionRow = new ConditionRow();
        conditionRow.add("id", 2, "=");

        jdbcManager.update("usr", modifyRow, conditionRow);

        List<DataRow> actual = jdbcManager.dataTable("usr");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    public void testDelete() throws Exception {

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("delete-data.xml"));

        List<DataRow> expected = getDataMapList(expectedData, "user_info");

        jdbcManager.delete("user_info", 1);

        List<DataRow> actual = jdbcManager.dataTable("user_info");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    public void testClearTable() throws Exception {

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("clear_table-data.xml"));

        List<DataRow> expected = getDataMapList(expectedData, "user_info");

        jdbcManager.clearTable("user_info");

        List<DataRow> actual = jdbcManager.dataTable("user_info");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    //TODO: Придумать что сделать синициализацие таблиц, они не создаются после удаления
    /*public void testDeleteTable() throws Exception {

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("delete_table-data.xml"));

        jdbcManager.deleteTable("user_info");

        List<String> actual = jdbcManager.listTable();

        Assert.assertEquals(Arrays.asList(expectedData.getTableNames()), actual);
    }*/

    private List<DataRow> getDataMapList(IDataSet iDataSet, String tableName) throws Exception {

        List<DataRow> dataRowList = new LinkedList<>();

        ITable usrTable = iDataSet.getTable(tableName);
        Column [] columns = usrTable.getTableMetaData().getColumns();

        for (int indexRow = 0; indexRow < usrTable.getRowCount(); indexRow++) {
            DataRow row = new DataRow();
            for (Column column : columns) {

                String nameColumn = column.getColumnName();
                Object value = usrTable.getValue(indexRow, nameColumn);
                row.add(nameColumn, value);
            }
            dataRowList.add(row);
        }

        return dataRowList;
    }
}
