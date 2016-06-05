package ru.fedichkindenis.SQLCmd;

import ru.fedichkindenis.SQLCmd.bd.Connect;
import ru.fedichkindenis.SQLCmd.bd.PostgreSql;

import java.sql.*;

/**
 * Created by Денис on 05.06.2016.
 */
public class Main {

    public static void main(String[] args) {

        Connect connect = new PostgreSql("localhost", 5433, "cmd", "postgres", "mac");
        Connection connection = connect.getConnect();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("select * from users u order by u.id");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ResultSetMetaData metaData = resultSet.getMetaData();
                for(int i = 0; i < metaData.getColumnCount(); i++) {

                    System.out.println(metaData.getColumnName(i + 1) + ": " + resultSet.getString(i + 1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement != null){

                    statement.close();
                }

                if(resultSet != null) {

                    resultSet.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            connect.closeConnect();
        }
    }
}
