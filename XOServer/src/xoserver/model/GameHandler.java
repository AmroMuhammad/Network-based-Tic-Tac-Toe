/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.model;

/**
 *
 * @author Amr & abdelrahman
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
    Socket clientSocket;

    public GameHandler(Socket s) {
        try {
            clientSocket = s;
            databaseConnection = DatabaseConnection.getDatabaseInstance();
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
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
                System.out.println(msg);
                //sendMessageToAll(msg);
                if (msg == null) {
                    checkClientSocket();       //check if userSocket open or not.
                } else if (parsing(msg) == 1) {
                    if (!isUserExists(parsedMsg[1])) {
                        addUserToDatabase(parsedMsg[1], parsedMsg[2]);
                        System.out.println("done added");
                        ++MainServer.onlinePlayers;
                        signInPlayer(parsedMsg[1]);
                        ps.println("Register Confirmed");
                    } else {
                        System.out.println("user exists");
                        ps.println("Register Not Confirmed");    //send false to client to reset text fields as username exists
                    }
                } else if (parsing(msg) == 2) {
                    if (isUserExists(parsedMsg[1])) {
                        if (isPasswordCorrect(parsedMsg[1], parsedMsg[2])) {
                            signInPlayer(parsedMsg[1]);
                            System.out.println("username correct and password is correct"); //send true to client
                            ++MainServer.onlinePlayers;
                            --MainServer.offlinePlayers;
                            ps.println("SignIN Confirmed#" + getScore(parsedMsg[1]));
                        } else {
                            System.out.println("username correct and password is not correct");
                            ps.println("SignIN not Confirmed");        //send false to client to reset text fields as password is false
                        }
                    } else {
                        System.out.println("username is not correct");
                        ps.println("SignIN not Confirmed");      //send false to client to reset text fields as username doesn't exists
                    }
                } else if (parsing(msg) == 3) {
                    signOutPLayer(parsedMsg[1]);
                    ++MainServer.offlinePlayers;
                    --MainServer.onlinePlayers;
                } else if (parsing(msg) == 4) {
                    sendMessageToAll(msg);
                } else if (parsing(msg) == 5) {
                    ps.println(getPlayersList()); //sends players list to player
                } else if (parsing(msg) == 6) {
                    System.out.println(msg);
                    sendMessageToAll(msg); //sends players list to player
                } else if (parsing(msg) == 7) {
                    //System.out.println("PLAYING"); 
                    setPlaying(parsedMsg[1]);
                } else if (parsing(msg) == 8) {
                    //System.out.println("NOT PLAYING");
                    setNotPlaying(parsedMsg[1]);
                } else if (parsing(msg) == 9) {
                    //System.out.println("added to the DB: "+parsedMsg[1]+" "+parsedMsg[2]);
                    int scr = Integer.parseInt(parsedMsg[2]);
                    setScore(parsedMsg[1], scr);
                } else if (parsing(msg) == 10) {
                    //System.out.println("ISONLINE GH: "+parsedMsg[1]);
                    if (isOnline(parsedMsg[1])) {
                        sendMessageToAll("ONLINE#" + parsedMsg[1]);
                    } else {
                       sendMessageToAll("OFF#" + parsedMsg[1]); 
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

    public void addUserToDatabase(String user, String pass) {
        databaseConnection.addUser(user, pass);
    }

    public boolean isUserExists(String user) {
        if (databaseConnection.checkUserExistance(user)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordCorrect(String user, String pass) {
        if (databaseConnection.checkUserPassword(user, pass)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isOnline(String username) {
        if (databaseConnection.isOnline(username)) {
            System.out.println("ONLINE TRUE");
            return true;
        }
        System.out.println("ONLINE FALSE");
        return false;
    }

    public void signInPlayer(String user) {
        databaseConnection.signInPlayer(user);
    }

    public void signOutPLayer(String user) {
        databaseConnection.signOutPlayer(user);
    }

    ////////////////////////////////////////////////////////////////////////////done by me AN
    public void setPlaying(String user) {
        databaseConnection.setPlaying(user);
    }

    public void setNotPlaying(String user) {
        databaseConnection.setNotPlaying(user);
    }

    public void setScore(String user, int scr) {
        databaseConnection.setScore(user, scr);
    }

    ////////////////////////////////////////////////////////////////////////////
    public int parsing(String requestMessage) {
        if (requestMessage.equals(null)) {
            return -1;
        }
        parsedMsg = requestMessage.split("\\#");
        if (parsedMsg[0].equals("REG")) {
            return 1;     //register request
        } else if (parsedMsg[0].equals("SIN")) {
            return 2;     //sign in request
        } else if (parsedMsg[0].equals("SOUT")) {
            return 3; //sign out request
        } else if (parsedMsg[0].equals("GAME")) {
            return 4; // finished playing
        } else if (parsedMsg[0].equals("PLIST")) {
            return 5; //request playing list
        } else if (parsedMsg[0].equals("DUWTP") || parsedMsg[0].equals("PREQ")) {
            return 6; //request playing and answer
        } else if (parsedMsg[0].equals("PLN")) {
            return 7;
        } else if (parsedMsg[0].equals("NPLN")) {
            return 8;
        } else if (parsedMsg[0].equals("SCR")) {
            return 9;
        } else if (parsedMsg[0].equals("ISONLINE")) {
            return 10;
        } else {//7=PLN 8=NPLN 9=SCR
            return 11; //sign out
        }
    }

    public int getScore(String username) {
        return databaseConnection.getScore(username);
    }

    public String getPlayersList() {
        System.out.println(databaseConnection.getOnlinePlayersList() + "." + databaseConnection.getPlayingPlayersList());
        return databaseConnection.getOnlinePlayersList() + "." + databaseConnection.getPlayingPlayersList();
    }

    public void checkClientSocket() {
        try {
            if (clientSocket.getInputStream().read() == -1) {
                try {
                    ps.close();
                    dis.close();
                    clientVector.remove(this);
                    clientSocket.close();
                    this.stop();
                } catch (IOException ex) {
                    Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
