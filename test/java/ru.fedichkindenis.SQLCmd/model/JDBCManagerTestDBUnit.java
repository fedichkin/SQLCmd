package ru.fedichkindenis.SQLCmd.model;

import config.DBUnitConfig;
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
import java.util.concurrent.ExecutionException;

/**
 * Created by Денис on 03.08.2016.
 *
 * Тестирование работы с базой данных с использованием DBUnit
 */
public class JDBCManagerTestDBUnit extends DBUnitConfig {

    @Before
    public void setUp() throws Exception {
        super.setUp();
        beforeData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("init-data.xml"));

        tester.setDataSet(beforeData);
        tester.onSetup();
    }

    public JDBCManagerTestDBUnit(String name) {
        super(name);
    }

    @Test
    public void testListTable() throws Exception {

        JDBCManager jdbcManager = new JDBCManager();
        jdbcManager.connect("localhost", "5432", "testSqlCmd", "postgres", "mac");

        List<String> actual = jdbcManager.listTable();

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("init-data.xml"));

        IDataSet actualData = tester.getConnection().createDataSet();

        Assertion.assertEquals(expectedData, actualData);
        Assert.assertEquals(Arrays.asList(expectedData.getTableNames()), actual);
    }

    @Test
    public void testDataTable()  throws Exception {

        JDBCManager jdbcManager = new JDBCManager();
        jdbcManager.connect("localhost", "5432", "testSqlCmd", "postgres", "mac");

        List<DataMap> actual = jdbcManager.dataTable("usr");

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("init-data.xml"));

        List<DataMap> expected = getDataMapList(expectedData, "usr");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testInsert() throws Exception {

        JDBCManager jdbcManager = new JDBCManager();
        jdbcManager.connect("localhost", "5432", "testSqlCmd", "postgres", "mac");

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("insert-data.xml"));

        List<DataMap> expected = getDataMapList(expectedData, "usr");

        DataMap newRow = new DataMap();
        newRow.add("id", 3);
        newRow.add("login", "super_user");
        newRow.add("password", "1q2w3e4r");
        jdbcManager.insert("usr", newRow);

        List<DataMap> actual = jdbcManager.dataTable("usr");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testUpdate() throws Exception {

        JDBCManager jdbcManager = new JDBCManager();
        jdbcManager.connect("localhost", "5432", "testSqlCmd", "postgres", "mac");

        IDataSet expectedData = new FlatXmlDataSetBuilder().build(
                Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream("update-data.xml"));

        List<DataMap> expected = getDataMapList(expectedData, "usr");

        DataMap modifyRow = new DataMap();
        modifyRow.add("id", 2);
        modifyRow.add("login", "user");
        modifyRow.add("password", "QWERTY");
        jdbcManager.update("usr", 2, modifyRow);

        List<DataMap> actual = jdbcManager.dataTable("usr");

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    private List<DataMap> getDataMapList(IDataSet iDataSet, String tableName) throws Exception {

        List<DataMap> dataMapList = new LinkedList<>();

        ITable usrTable = iDataSet.getTable(tableName);
        Column [] columns = usrTable.getTableMetaData().getColumns();

        for (int indexRow = 0; indexRow < usrTable.getRowCount(); indexRow++) {
            DataMap row = new DataMap();
            for (Column column : columns) {

                String nameColumn = column.getColumnName();
                row.add(nameColumn, usrTable.getValue(indexRow, nameColumn));
            }
            dataMapList.add(row);
        }

        return dataMapList;
    }
}
