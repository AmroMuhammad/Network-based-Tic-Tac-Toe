/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

/**
 *
 * @author Amr
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amr
 */
public class GameHandler extends Thread {
    
    private DataInputStream dis;
    private PrintStream ps;
    static Vector<GameHandler> clientVector = new Vector<>();
    private DatabaseConnection databaseConnection;
    private String[] parsedMsg;
    
    public GameHandler(Socket s) {
        try {
            databaseConnection = DatabaseConnection.getDatabaseInstance();
            dis = new DataInputStream(s.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            clientVector.add(this);
            
            start();
        } catch (IOException ex) {
            Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        initializeDatabase();
        while (true) {
            try {
                String msg = dis.readLine();
                sendMessageToAll(msg);
                if (parsing(msg) == 1) {
                    if (!isUserExists(parsedMsg[1])) {
                        addUserToDatabase(parsedMsg[1], parsedMsg[2], parsedMsg[3]);
                        System.out.println("done added");
                    } else {
                        System.out.println("user exists"); //send false to client to reset text fields as username exists
                    }
                } else if (parsing(msg) == 2) {
                    if (isUserExists(parsedMsg[1])) {
                        if (isPasswordCorrect(parsedMsg[1], parsedMsg[2])) {
                            setPlayerStatus(parsedMsg[1]);
                            System.out.println("username correct and password is correct"); //send true to client
                        } else {
                            System.out.println("username correct and password is not correct"); //send false to client to reset text fields as password is false
                        }
                    } else {
                        System.out.println("username is not correct"); //send false to client to reset text fields as username doesn't exists
                    }
                }
            } catch (IOException ex) {
                stop(); //handling exception when closing clients
                Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void sendMessageToAll(String msg) {
        for (GameHandler s : clientVector) {
            s.ps.println(msg);
        }
    }
    
    public void initializeDatabase() {
        databaseConnection.openConnection();
    }
    
    public void addUserToDatabase(String user, String pass, String ip) {
        databaseConnection.addUser(user, pass, ip);
    }
    
//    public boolean isItClientReqisterRequest(String requestMessage) {   //parse recieved data to check whether request is to register or not
//        parsedMsg = requestMessage.split("\\#");
//        if (parsedMsg[0].equals("-*-*-")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    
    public boolean isUserExists(String user) {
        if (databaseConnection.checkUserExistance(user)) {
            return true;
        } else {
            return false;
        }
    }
    
//    public boolean isItClientSignInRequest(String requestMessage) {   //parse recieved data to check whether request is to register or not
//        parsedMsg = requestMessage.split("\\#");
//        if (parsedMsg[0].equals("*-*-*")) {
//            return true;
//        } else {
//            return false;
//        }
//    }
    
    public boolean isPasswordCorrect(String user, String pass) {
        if (databaseConnection.checkUserPassword(user, pass)) {
            return true;
        } else {
            return false;
        }
    }
    
    public void setPlayerStatus(String user) {
        databaseConnection.setPlayerStatus(user);
    }
    
    public int parsing(String requestMessage){
        parsedMsg = requestMessage.split("\\#");
        if(parsedMsg[0].equals("-*-*-"))
            return 1;     //register request
        else if(parsedMsg[0].equals("*-*-*"))
            return 2;     //sign in request
        else
            return 3; //forget password
    }

//    InetAddress inetAddress = InetAddress.getLocalHost();
//    System.out.println("IP Address:- " + inetAddress.getHostAddress());  //to get ip address for user (will be added later in client side)
}
