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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javax.swing.JOptionPane;

/**
 *
 * @author HP
 */

public class connection_signUP {
    
    Socket socket;
    DataInputStream dis;
    PrintStream ps ;
    
    

   public connection_signUP(String str )
   {
            
        try {
        
            socket = new Socket("127.0.0.1",1);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
        }
         ps.println(str);
         ps.flush();
                
          new Thread(){
            public void run(){
                while(true)
                {
                    
                    try {
                        String msg = dis.readLine();
                        if(msg.equals("false"))
                        {
                           
                            JOptionPane.showMessageDialog(null, "invalid userName or Password.");
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
