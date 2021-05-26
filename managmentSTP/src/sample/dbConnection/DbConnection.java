package sample.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String CONN = "jdbc:sqlite:db.db";

    public static Connection getConnection() throws SQLException{
        try{
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(CONN);
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
