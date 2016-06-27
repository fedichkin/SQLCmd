package ru.fedichkindenis.SQLCmd;

import ru.fedichkindenis.SQLCmd.bd.BDManager;
import ru.fedichkindenis.SQLCmd.bd.PostgreSql;

import java.util.List;

/**
 * Created by Денис on 05.06.2016.
 */
public class Main {

    public static void main(String[] args) {

        BDManager bdManager = new BDManager(new PostgreSql("localhost", 5433, "cmd", "postgres", "mac"));

        List<String> listTable = bdManager.getListTable();

        for(String table : listTable) {

            System.out.println(table);
        }
    }
}
