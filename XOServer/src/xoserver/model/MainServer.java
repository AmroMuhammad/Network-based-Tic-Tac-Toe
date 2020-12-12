/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amr
 */
public class MainServer extends Thread {

    static Vector<GameHandler> socketVector;
    private ServerSocket mainSocket;
    private GameHandler handler;
    private DatabaseConnection dbConnection;
    public static int onlinePlayers;
    public static int offlinePlayers;
    public static int playingPlayers;

    public MainServer() {
        try {
            mainSocket = new ServerSocket(5008);
            socketVector = new Vector<>();
            dbConnection = DatabaseConnection.getDatabaseInstance();
            onlinePlayers = dbConnection.numOnlinePlayers();
            offlinePlayers = dbConnection.numOfflinePlayers();
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket s = mainSocket.accept();
                handler = new GameHandler(s);
                socketVector.add(handler);
            } catch (IOException ex) {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stopClients() {
        for (GameHandler s : socketVector) {
            s.stop();
        }
    }
}
