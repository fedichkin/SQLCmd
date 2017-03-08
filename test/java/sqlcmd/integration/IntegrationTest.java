package ru.fedichkindenis.sqlcmd.integration;

import config.ConsoleInputStream;
import config.JDBCProperties;
import config.TestBD;
import org.junit.*;
import ru.fedichkindenis.sqlcmd.controller.Controller;
import ru.fedichkindenis.sqlcmd.model.DBManager;
import ru.fedichkindenis.sqlcmd.model.JDBCManager;
import ru.fedichkindenis.sqlcmd.view.Console;
import ru.fedichkindenis.sqlcmd.view.TableConsoleView;
import ru.fedichkindenis.sqlcmd.view.View;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Интеграционные тесты на систему
 */
public class IntegrationTest {

    private ByteArrayOutputStream out;
    private ConsoleInputStream in;
    private static TestBD testBD;

    @BeforeClass
    public static void initDB() throws Exception {

        testBD = new TestBD();
        testBD.generate();
    }

    @AfterClass
    public static void dropDB() throws Exception {

        testBD.deleteDB();
    }

    @Before
    public void run() throws Exception {

        out = new ByteArrayOutputStream();
        in = new ConsoleInputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @After
    public void end() throws Exception {

        out.close();
        in.close();
    }

    @Test
    public void EntryAndExitApplication() throws Exception {

        //given
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                "connect|host|port|dbName|userName|password\n" +
                "\n" +
                "Введите команду (help для справки): \n" +
                "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void commandWithoutConnectTest() throws Exception {

        //given
        in.add("list-table");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                "connect|host|port|dbName|userName|password\n" +
                "\n" +
                "Введите команду (help для справки): \n" +
                "Произошла ошибка: Соединение не установленно! Установите соединение!\n" +
                "\n" +
                "Введите команду (help для справки): \n" +
                "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void connectBDTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void badCommandTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("listCommand");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Произошла ошибка: Указан не верный формат команды\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void getListTableTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("list-table");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════════╗\n" +
                        "║delete_table║\n" +
                        "╠════════════╣\n" +
                        "║user_info   ║\n" +
                        "╠════════════╣\n" +
                        "║usr         ║\n" +
                        "╚════════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void insertDataAndClearTableTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("data-table|usr");
        in.add("insert-row|usr|id|1|login|admin|password|qwerty");
        in.add("insert-row|usr|id|2|login|user|password|1234");
        in.add("data-table|usr");
        in.add("clear-table|usr");
        in.add("data-table|usr");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════╦════════╦════════╗\n" +
                        "║id      ║login   ║password║\n" +
                        "╠════════╬════════╬════════╣\n" +
                        "║        ║        ║        ║\n" +
                        "╚════════╩════════╩════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблицу usr была добавлена строка!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблицу usr была добавлена строка!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════╦════════╦════════╗\n" +
                        "║id      ║login   ║password║\n" +
                        "╠════════╬════════╬════════╣\n" +
                        "║1       ║admin   ║qwerty  ║\n" +
                        "║2       ║user    ║1234    ║\n" +
                        "╚════════╩════════╩════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Таблица usr очищена!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════╦════════╦════════╗\n" +
                        "║id      ║login   ║password║\n" +
                        "╠════════╬════════╬════════╣\n" +
                        "║        ║        ║        ║\n" +
                        "╚════════╩════════╩════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void deleteAndCreateTableTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("list-table");
        in.add("delete-table|delete_table");
        in.add("list-table");
        in.add("create-table|delete_table");
        in.add("list-table");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════════╗\n" +
                        "║delete_table║\n" +
                        "╠════════════╣\n" +
                        "║user_info   ║\n" +
                        "╠════════════╣\n" +
                        "║usr         ║\n" +
                        "╚════════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Таблица delete_table была удалена!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═════════╗\n" +
                        "║user_info║\n" +
                        "╠═════════╣\n" +
                        "║usr      ║\n" +
                        "╚═════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Была создана таблица delete_table!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════════╗\n" +
                        "║delete_table║\n" +
                        "╠════════════╣\n" +
                        "║user_info   ║\n" +
                        "╠════════════╣\n" +
                        "║usr         ║\n" +
                        "╚════════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void updateDataTableTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("data-table|user_info");
        in.add("insert-row|user_info|id|1|usr|1|name|Ivan|surname|Ivanov");
        in.add("insert-row|user_info|id|2|usr|2|name|Sergey|surname|Ivanov");
        in.add("data-table|user_info");
        in.add("update-row|user_info|surname|Kirienko|!IF|name|=|Sergey");
        in.add("data-table|user_info");
        in.add("clear-table|user_info");
        in.add("data-table|user_info");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═══════╦═══════╦═══════╦═══════╗\n" +
                        "║id     ║usr    ║name   ║surname║\n" +
                        "╠═══════╬═══════╬═══════╬═══════╣\n" +
                        "║       ║       ║       ║       ║\n" +
                        "╚═══════╩═══════╩═══════╩═══════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблицу user_info была добавлена строка!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблицу user_info была добавлена строка!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═══════╦═══════╦═══════╦═══════╗\n" +
                        "║id     ║usr    ║name   ║surname║\n" +
                        "╠═══════╬═══════╬═══════╬═══════╣\n" +
                        "║1      ║1      ║Ivan   ║Ivanov ║\n" +
                        "║2      ║2      ║Sergey ║Ivanov ║\n" +
                        "╚═══════╩═══════╩═══════╩═══════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблице user_info была обновлена строка!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════╦════════╦════════╦════════╗\n" +
                        "║id      ║usr     ║name    ║surname ║\n" +
                        "╠════════╬════════╬════════╬════════╣\n" +
                        "║1       ║1       ║Ivan    ║Ivanov  ║\n" +
                        "║2       ║2       ║Sergey  ║Kirienko║\n" +
                        "╚════════╩════════╩════════╩════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Таблица user_info очищена!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═══════╦═══════╦═══════╦═══════╗\n" +
                        "║id     ║usr    ║name   ║surname║\n" +
                        "╠═══════╬═══════╬═══════╬═══════╣\n" +
                        "║       ║       ║       ║       ║\n" +
                        "╚═══════╩═══════╩═══════╩═══════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void deleteDataTableTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("data-table|user_info");
        in.add("insert-row|user_info|id|1|usr|1|name|Ivan|surname|Ivanov");
        in.add("insert-row|user_info|id|2|usr|2|name|Sergey|surname|Ivanov");
        in.add("data-table|user_info");
        in.add("delete-row|user_info|!IF|name|=|Sergey");
        in.add("data-table|user_info");
        in.add("delete-row|user_info|!IF|usr|>|0");
        in.add("data-table|user_info");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═══════╦═══════╦═══════╦═══════╗\n" +
                        "║id     ║usr    ║name   ║surname║\n" +
                        "╠═══════╬═══════╬═══════╬═══════╣\n" +
                        "║       ║       ║       ║       ║\n" +
                        "╚═══════╩═══════╩═══════╩═══════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблицу user_info была добавлена строка!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблицу user_info была добавлена строка!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═══════╦═══════╦═══════╦═══════╗\n" +
                        "║id     ║usr    ║name   ║surname║\n" +
                        "╠═══════╬═══════╬═══════╬═══════╣\n" +
                        "║1      ║1      ║Ivan   ║Ivanov ║\n" +
                        "║2      ║2      ║Sergey ║Ivanov ║\n" +
                        "╚═══════╩═══════╩═══════╩═══════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблице user_info были удалены строки!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═══════╦═══════╦═══════╦═══════╗\n" +
                        "║id     ║usr    ║name   ║surname║\n" +
                        "╠═══════╬═══════╬═══════╬═══════╣\n" +
                        "║1      ║1      ║Ivan   ║Ivanov ║\n" +
                        "╚═══════╩═══════╩═══════╩═══════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "В таблице user_info были удалены строки!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔═══════╦═══════╦═══════╦═══════╗\n" +
                        "║id     ║usr    ║name   ║surname║\n" +
                        "╠═══════╬═══════╬═══════╬═══════╣\n" +
                        "║       ║       ║       ║       ║\n" +
                        "╚═══════╩═══════╩═══════╩═══════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    @Test
    public void userQueryTest() throws Exception {

        //given
        in.add(getParametersForConnect());
        in.add("data-table|usr");
        in.add("user-query|<insert into usr (id, login, password) values(1, 'admin', '1q2w3e')");
        in.add("data-table|usr");
        in.add("user-query|<delete from usr");
        in.add("data-table|usr");
        in.add("exit");

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                        "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                        "connect|host|port|dbName|userName|password\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Соединение установлено!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════╦════════╦════════╗\n" +
                        "║id      ║login   ║password║\n" +
                        "╠════════╬════════╬════════╣\n" +
                        "║        ║        ║        ║\n" +
                        "╚════════╩════════╩════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Была выполнен запрос: insert into usr (id, login, password) values(1, 'admin', '1q2w3e')!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════╦════════╦════════╗\n" +
                        "║id      ║login   ║password║\n" +
                        "╠════════╬════════╬════════╣\n" +
                        "║1       ║admin   ║1q2w3e  ║\n" +
                        "╚════════╩════════╩════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "Была выполнен запрос: delete from usr!\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "╔════════╦════════╦════════╗\n" +
                        "║id      ║login   ║password║\n" +
                        "╠════════╬════════╬════════╣\n" +
                        "║        ║        ║        ║\n" +
                        "╚════════╩════════╩════════╝\n" +
                        "\n" +
                        "Введите команду (help для справки): \n" +
                        "До свидания!\n";
        //when
        runApplication();
        //then
        assertEquals(expectedText , getData());
    }

    private String getParametersForConnect() throws Exception {

        String parameters = "connect|";

        JDBCProperties jdbcProperties = new JDBCProperties("postgesql.config.properties");
        Properties properties = jdbcProperties.getProperties();

        parameters += properties.get("db.host") + "|";
        parameters += properties.get("db.port") + "|";
        parameters += properties.get("db.dbName") + "|";
        parameters += properties.get("db.username") + "|";
        parameters += properties.get("db.password");

        return parameters;
    }

    private void runApplication() throws Exception {

        View view = new Console();
        DBManager dbManager = new JDBCManager();

        Controller controller = new Controller(new TableConsoleView(view), dbManager);
        controller.run();
    }

    private String getData() throws Exception {

        String CRLF = "\r\n";
        String LF = "\n";

        String result = new String(out.toByteArray(),"UTF-8");
        out.reset();
        return result.replace(CRLF, LF);

    }
}
