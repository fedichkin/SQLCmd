package ru.fedichkindenis.SQLCmd.model;

import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Денис on 30.07.2016.
 *
 * Класс для тестирования менеджера базы данных JDBC
 */
public class JDBCManagerTest {

    @Test
    public void testConnect() {

        JDBCManager jdbcManager = new JDBCManager();
        jdbcManager.connect("localhost", "5432", "testSqlCmd", "postgres", "mac");

        assertEquals(true, jdbcManager.isConnect());
    }

    @Test
    public void testConnectBadHost() {

        try {
            JDBCManager jdbcManager = new JDBCManager();
            jdbcManager.connect("badHost", "5432", "testSqlCmd", "postgres", "mac");

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadPort() {

        try {
            JDBCManager jdbcManager = new JDBCManager();
            jdbcManager.connect("localhost", "6666", "testSqlCmd", "postgres", "mac");

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadDBName() {

        try {
            JDBCManager jdbcManager = new JDBCManager();
            jdbcManager.connect("localhost", "5432", "bad", "postgres", "mac");

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadUser() {

        try {
            JDBCManager jdbcManager = new JDBCManager();
            jdbcManager.connect("localhost", "5432", "testSqlCmd", "bad", "mac");

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testConnectBadPassword() {

        try {
            JDBCManager jdbcManager = new JDBCManager();
            jdbcManager.connect("localhost", "5432", "testSqlCmd", "postgres", "bad");

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }

    @Test
    public void testDisconnect() {

        JDBCManager jdbcManager = new JDBCManager();
        jdbcManager.connect("localhost", "5432", "testSqlCmd", "postgres", "mac");
        jdbcManager.disconnect();
    }

    @Test
    public void testListTableNoConnect() {

        try {
            JDBCManager jdbcManager = new JDBCManager();
            jdbcManager.listTable();

            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            //do nothing
        }
    }
}
