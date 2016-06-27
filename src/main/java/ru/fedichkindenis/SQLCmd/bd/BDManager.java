package ru.fedichkindenis.SQLCmd.bd;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Денис on 27.06.2016.
 */
public class BDManager {

    private DataBase dataBase;

    public BDManager(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public List<String> getListTable() {

        List<String> listTable = new LinkedList<String>();

        Connection connection = dataBase.getConnect();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("select table_name as nameTable " +
                    "from information_schema.tables " +
                    "where table_schema = 'public' " +
                    "order by table_name");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                listTable.add(resultSet.getString("nameTable"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            closeResult(resultSet);
            closeStatement(statement);
            dataBase.closeConnect();
        }

        return listTable;
    }

    private void closeStatement(Statement statement){

        try {
            if(statement != null){

                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeResult(ResultSet resultSet){

        try {
            if(resultSet != null){

                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
