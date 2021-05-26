package sample.Models;

import java.sql.*;

import sample.dbConnection.DbConnection;

public class LoginModel {
    public Connection conn;
    public static String CURRENT_USER;

    public LoginModel(){
        try {
            this.conn = DbConnection.getConnection();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        if(this.conn == null){
            System.exit(1);
        }
    }

    public boolean isDbConn(){
        return this.conn != null;
    }

    public boolean isLogin(String user, String pass) throws Exception{

        Statement state = conn.createStatement();

        String sql = "SELECT * FROM users WHERE name = ? AND pasw = ?";
        PreparedStatement pr = this.conn.prepareStatement(sql);
        try{
            System.out.println("t");

            pr.setString(1, user);
            pr.setString(2, pass);

            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                CURRENT_USER = user;
                System.out.println("tru");
                conn.close();
                return  true;
            }else{
                return false;
            }
        }
        catch (SQLException ex){
            System.out.println("tttttt");

            return false;
        }

    }

}
