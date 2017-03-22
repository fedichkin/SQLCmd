package sqlcmd.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import ru.fedichkindenis.sqlcmd.model.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Класс для тестирования сборщика запросов для PostgreSql
 */
public class PostgreSqlBuilderQueryTest {

    private PostgreSqlBuilderQuery builderQuery;
    private DataRow dataRow;
    private ConditionRow conditionRow;
    private CreateRow createRow;
    private CreateField field;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        dataRow = mock(DataRow.class);
        conditionRow = mock(ConditionRow.class);
        createRow = mock(CreateRow.class);
        field = mock(CreateField.class);

        builderQuery = new PostgreSqlBuilderQuery();
    }

    @Test
    public void getClassDriverTest() {

        //given
        String expected = "org.postgresql.Driver";

        //when
        String actual = builderQuery.getClassDriver();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getUrlConnectionTest() {

        //given
        String hostname = "localhost";
        String port = "1515";
        String dbName = "dbTest";
        String expected = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName;

        //when
        String actual = builderQuery.getUrlConnection(hostname, port, dbName);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryListTableTest() {

        //given
        String expected = "select table_name as nameTable " +
                "from information_schema.tables " +
                "where table_schema = 'public' " +
                "order by table_name";

        //when
        String actual = builderQuery.getQueryListTable();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryDataTableTest() {

        //given
        String tableName = "myTable";
        String expected = "select * from " + tableName;

        //when
        String actual = builderQuery.getQueryDataTable(tableName);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryClearTableTest() {

        //given
        String tableName = "myTable";
        String expected = "truncate table " + tableName;

        //when
        String actual = builderQuery.getQueryClearTable(tableName);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryDeleteTableTest() {

        //given
        String tableName = "myTable";
        String expected = "drop table " + tableName;

        //when
        String actual = builderQuery.getQueryDeleteTable(tableName);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryInsertRowTest() {

        //given
        String tableName = "myTable";
        String expected = "insert into myTable(id,name) values (?,?)";

        Collection<String> listNameField = new LinkedList<>();
        listNameField.add("id");
        listNameField.add("name");
        int countField = 2;

        when(dataRow.getListNameField()).thenReturn(listNameField);
        when(dataRow.getCountField()).thenReturn(countField);

        //when
        String actual = builderQuery.getQueryInsertRow(tableName, dataRow);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryUpdateRowTest() {

        //given
        String tableName = "myTable";
        String expected = "update myTable set id = ?,name = ? where 1 = 1  and id=?";

        Collection<String> listNameField = new LinkedList<>();
        listNameField.add("id");
        listNameField.add("name");

        Collection<String> listConditionNameField = new LinkedList<>();
        listConditionNameField.add("id");

        Collection<String> listConditionField = new LinkedList<>();
        listConditionField.add("=");

        when(dataRow.getListNameField()).thenReturn(listNameField);
        when(conditionRow.getListConditionField()).thenReturn(listConditionField);
        when(conditionRow.getListNameField()).thenReturn(listConditionNameField);

        //when
        String actual = builderQuery.getQueryUpdateRow(tableName, dataRow, conditionRow);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryDeleteRowTest() {

        //given
        String tableName = "myTable";
        String expected = "delete from myTable where 1 = 1 and id=?";

        Collection<String> listConditionNameField = new LinkedList<>();
        listConditionNameField.add("id");

        Collection<String> listConditionField = new LinkedList<>();
        listConditionField.add("=");

        when(conditionRow.getListConditionField()).thenReturn(listConditionField);
        when(conditionRow.getListNameField()).thenReturn(listConditionNameField);

        //when
        String actual = builderQuery.getQueryDeleteRow(tableName, conditionRow);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void getQueryCreateTableTest() {

        //given
        String tableName = "myTable";
        String expected = "create table myTable (id bigint  NOT NULL,name varchar )";

        List<Field> fieldList = new LinkedList<>();
        fieldList.add(field);
        fieldList.add(field);

        String nameField1 = "id";
        String nameField2 = "name";
        String typeField1 = "bigint";
        String typeField2 = "varchar";


        when(createRow.getIteratorField()).thenReturn(fieldList.iterator());
        when(field.getNameField()).thenReturn(nameField1).thenReturn(nameField2);
        when(field.getTypeField()).thenReturn(typeField1).thenReturn(typeField2);
        when(field.isNotNull()).thenReturn(true).thenReturn(false);

        //when
        String actual = builderQuery.getQueryCreateTable(tableName, createRow);

        //then
        assertEquals(expected, actual);
    }
}
