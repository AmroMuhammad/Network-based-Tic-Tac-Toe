/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Amr
 */
public class DatabaseConnection {

    private Connection con;
    private static DatabaseConnection databaseObject;
    private PreparedStatement pst;
    private Statement stmt;
    private ResultSet rs;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getDatabaseInstance() {
        if (databaseObject == null) {
            databaseObject = new DatabaseConnection();
        }
        return databaseObject;
    }

    public void openConnection() {
        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/UsersDatabase", "amr", "amr");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addUser(String username, String password, String ip) {
        try {
            pst = con.prepareStatement("insert into users (Username,password,ip) VALUES (?,?,?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, ip);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public boolean checkUserExistance(String user){
//        try {
//            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//                    ResultSet.CONCUR_UPDATABLE);
//            rs = stmt.executeQuery("select * from users where USERNAME='user'" );
//            if(rs == null)
//                return false;
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//            return true;
//    }
    public boolean checkUserExistance(String user) {
        try {
            pst = con.prepareStatement("select username from users where USERNAME=?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, user);
            rs = pst.executeQuery();
            if (!rs.next()) {
                System.out.println("false");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("true");
        return true;
    }

    public boolean checkUserPassword(String user, String pass) {
        try {
            String databasePass;
            pst = con.prepareStatement("select password from users where USERNAME=?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, user);
            rs = pst.executeQuery();
            rs.next();
            databasePass = rs.getString(1);

            if (databasePass.equals(pass)) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void setPlayerStatus(String user) {
        try {
            pst = con.prepareStatement("update users set STATUS=? where username=?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setBoolean(1, true);
            pst.setString(2, user);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
