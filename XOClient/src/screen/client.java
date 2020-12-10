/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class client {
            Socket SClient;
            DataInputStream dis ;
            PrintStream ps ;
           
   public client(String data )
   {
            
        try {
        
            SClient = new Socket("127.0.0.1",1);
            dis = new DataInputStream(SClient.getInputStream());
            ps = new PrintStream(SClient.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
        }
         ps.println(data);
                ps.flush();
                
          new Thread(){
            public void run(){
                while(true)
                {
                    
                    try {
                        String msg = dis.readLine();
                        System.out.println( " my massege is ....."+msg);
                        if(msg.equals("false"))
                        {
                            
                           
                            JOptionPane.showMessageDialog(null, "invalid userName or Password");
                        }
                        else
                        {
                            
                           
                           System.out.println("move code here");  
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }.start();       
       
   }
}
