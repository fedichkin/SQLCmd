package ru.fedichkindenis.SQLCmd.integration;

import config.TestBD;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import ru.fedichkindenis.SQLCmd.controller.Commands.Command;
import ru.fedichkindenis.SQLCmd.model.DBManager;
import ru.fedichkindenis.SQLCmd.view.View;

/**
 * Интеграционные тесты на систему
 */
public class IntegrationTest {

    private Command command;
    private DBManager dbManager;
    private View view;
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
}
