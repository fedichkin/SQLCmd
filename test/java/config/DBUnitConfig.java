package config;

import config.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.*;

import java.io.IOException;
import java.util.Properties;

/**
 * Конфигурационный файл для dbunit тестов
 */
@Ignore
public class DBUnitConfig extends DBTestCase {

    protected IDatabaseTester tester;
    private Properties properties;
    protected IDataSet beforeData;
    private static TestBD testBD;

    @BeforeClass
    public static void initDb() throws Exception {

        testBD = new TestBD();
        testBD.generate();
    }

    @AfterClass
    public static void deleteDB() throws Exception {

        testBD.deleteDB();
    }

    @Before
    public void setUp() throws Exception {

        String url = properties.getProperty("db.javaDdConnect") +
                ":" + properties.getProperty("db.base") +
                "://" + properties.getProperty("db.host") +
                ":" + properties.getProperty("db.port") +
                "/" + properties.getProperty("db.dbName");

        tester = new JdbcDatabaseTester(properties.getProperty("db.driver"),
                url,
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }

    public DBUnitConfig() {
        super();
        try {
            JDBCProperties jdbcProperties = new JDBCProperties("postgesql.config.properties");
            properties = jdbcProperties.getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("db.javaDdConnect") +
                ":" + properties.getProperty("db.base") +
                "://" + properties.getProperty("db.host") +
                ":" + properties.getProperty("db.port") +
                "/" + properties.getProperty("db.dbName");

        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, properties.getProperty("db.driver"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, url);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, properties.getProperty("db.username"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, properties.getProperty("db.password"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return beforeData;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.CLOSE_CONNECTION(DatabaseOperation.NONE);
    }
}
