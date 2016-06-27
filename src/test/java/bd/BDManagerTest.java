package bd;

import org.junit.Test;
import ru.fedichkindenis.SQLCmd.bd.BDManager;
import ru.fedichkindenis.SQLCmd.bd.PostgreSql;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Денис on 27.06.2016.
 */
public class BDManagerTest {

    @Test
    public void getListTable() {

        List<String> listTable = new LinkedList<String>();
        listTable.add("user_info");
        listTable.add("users");

        BDManager bdManager = new BDManager(new PostgreSql("localhost", 5433, "cmd", "postgres", "mac"));

        assertEquals("user_info", bdManager.getListTable().get(0));
    }
}
