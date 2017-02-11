package config;

import ru.fedichkindenis.SQLCmd.controller.Commands.Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * Класс для генерации тестовой БД
 */
public class TestBD {

    private String nameDB;

    public void generate() throws Exception {

        Connection connection = getConnection(true);
        createDB(connection);
        connection.close();

        connection = getConnection(false);
        createTable(connection);
        connection.close();
    }

    private Connection getConnection(boolean noDb) throws Exception {

        Class.forName("org.postgresql.Driver");
        JDBCProperties jdbcProperties = new JDBCProperties("postgesql.config.properties");
        Properties properties = jdbcProperties.getProperties();

        String host = properties.getProperty("db.host");
        String port = properties.getProperty("db.port");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");
        nameDB = properties.getProperty("db.dbName");
        String url = "jdbc:postgresql://" + host + ":" + port + "/" + (noDb ? "" : nameDB);

        return DriverManager.getConnection(url, username, password);
    }

    private void createDB(Connection connection) throws Exception {

        Statement statement = connection.createStatement();
        statement.executeUpdate("create DATABASE " + nameDB);
        statement.close();
    }

    private void createTable(Connection connection) throws Exception {

        Statement statement = connection.createStatement();

        statement.executeUpdate("create table delete_table ()");

        statement.executeUpdate("create table user_info " +
                "(id bigint not null, usr bigint, name varchar, surname varchar)");

        statement.executeUpdate("create table usr " +
                "(id bigint not null, login varchar, password varchar)");

        statement.close();
    }

    public void deleteDB() throws Exception {

        Connection connection = getConnection(true);
        deleteDB(connection);
        connection.close();
    }

    private void deleteDB(Connection connection) throws Exception {

        Statement statement = connection.createStatement();
        statement.executeUpdate("drop DATABASE " + nameDB);
        statement.close();
    }
}
