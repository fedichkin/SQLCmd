package ru.fedichkindenis.SQLCmd.model;

import config.JDBCProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Денис on 30.07.2016.
 *
 * Класс для тестирования менеджера базы данных JDBC
 */
public class JDBCManagerTest {

    private JDBCManager jdbcManager;
    private Properties properties;

    @Before
    public void setUp() throws Exception {
        JDBCProperties jdbcProperties = new JDBCProperties("postgesql.config.properties");
        properties = jdbcProperties.getProperties();
        jdbcManager = new JDBCManager();
    }

    private void correctConnect() {

        jdbcManager.connect(properties.getProperty("db.host"),
                properties.getProperty("db.port"),
                properties.getProperty("db.dbName"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }

    @Test
    public void testConnect() {

        correctConnect();
        assertEquals(true, jdbcManager.isConnect());
        jdbcManager.disconnect();
    }

    @Test
    public void testConnectBadHost() {

        try {
            jdbcManager.connect("badHost",
                    properties.getProperty("db.port"),
                    properties.getProperty("db.dbName"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadPort() {

        try {
            jdbcManager.connect(properties.getProperty("db.host"),
                    "6666",
                    properties.getProperty("db.dbName"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadDBName() {

        try {
            jdbcManager.connect(properties.getProperty("db.host"),
                    properties.getProperty("db.port"),
                    "bad",
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password"));

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadUser() {

        try {
            jdbcManager.connect(properties.getProperty("db.host"),
                    properties.getProperty("db.port"),
                    properties.getProperty("db.dbName"),
                    "bad",
                    properties.getProperty("db.password"));

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadPassword() {

        try {
            jdbcManager.connect(properties.getProperty("db.host"),
                    properties.getProperty("db.port"),
                    properties.getProperty("db.dbName"),
                    properties.getProperty("db.username"),
                    "bad");

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testDisconnect() {

        correctConnect();
        jdbcManager.disconnect();
    }
}
