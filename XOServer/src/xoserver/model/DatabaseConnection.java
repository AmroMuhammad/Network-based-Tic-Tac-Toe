/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
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
    public static Vector<String> playerList;

    private DatabaseConnection() {
        playerList = new Vector<>();
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
            pst = con.prepareStatement("insert into AMR.users (Username,password,ip) VALUES (?,?,?)");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, ip);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkUserExistance(String user) {
        try {
            pst = con.prepareStatement("select username from AMR.users where USERNAME=?", ResultSet.TYPE_SCROLL_SENSITIVE,
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
            pst = con.prepareStatement("select password from AMR.users where USERNAME=?", ResultSet.TYPE_SCROLL_SENSITIVE,
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
            pst = con.prepareStatement("update AMR.users set STATUS=? where username=?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setBoolean(1, true);
            pst.setString(2, user);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int numOnlinePlayers() {
        int online = 0;
        try {

            pst = con.prepareStatement("select * from AMR.users where status=true and playstatus=false", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pst.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                ++online;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return online;
    }

    public int numOfflinePlayers() {
        int offline = 0;
        try {

            pst = con.prepareStatement("select * from AMR.users where status = false", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pst.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                ++offline;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return offline;
    }

    public int getScore(String username) {
        int score=0;
        try {
            pst = con.prepareStatement("select score from AMR.users where username = ?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, username);
            rs = pst.executeQuery();
            if(rs.next()){
            score=rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return score;
    }
    
    public String getOnlinePlayersList(){
            String players=null;
            playerList.clear();
        try {
            pst = con.prepareStatement("select USERNAME from AMR.users where status = true and playstatus = false", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            rs = pst.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                playerList.add(rs.getString(1));
            }
            
            for(String s : playerList){
                if(players == null)
                    players = s;
                else
                    players = players +("#"+s);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(players);
        return players;
    }
}
