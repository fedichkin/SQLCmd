package ru.fedichkindenis.SQLCmd.integration;

import config.ConsoleInputStream;
import config.TestBD;
import org.junit.*;
import ru.fedichkindenis.SQLCmd.controller.Controller;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.model.JDBCManager;
import ru.fedichkindenis.SQLCmd.view.Console;
import ru.fedichkindenis.SQLCmd.view.TableConsoleView;
import ru.fedichkindenis.SQLCmd.view.View;

import java.io.*;

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

        in.add("exit");
        runApplication();

        String expectedText =
                "Приветствую тебя пользователь!\n" +
                "Для начала работы с ситемой установи соединение с базой данных с помощью команды: \n" +
                "connect|host|port|dbName|userName|password\n" +
                "\n" +
                "Введите команду (help для справки): \n" +
                "До свидания!\n";

        assertEquals(expectedText , getData());
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