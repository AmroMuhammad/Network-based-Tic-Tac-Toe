package screen;

 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class GameBordController implements Initializable {
@FXML
    private Button Btn1,Btn2,Btn3,Btn4,Btn5,Btn6,Btn7,Btn8,Btn9;
@FXML
    private Label player1;
@FXML
    private Label player2;
@FXML
    private Label player1Symbol;
@FXML
    private Label player2Symbol;

 private String[] Steps;
 private String[] BeginSymbol;
 private String[] playerinfo;
 String Data;
     public void setText(String text1 , String text2 , String text3 , String text4 )
    {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        
      
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    
     public String[] parsing1(String Message){
        String[] arrOfStr = Message.split("#"); 
       return arrOfStr;
        
    }
     public String[] parsing2(String Message){
         String[] arrOfStr = Message.split("&");   
         return arrOfStr; 
    }
     
     public String[] parsing3(String Message){
         String[] arrOfStr = Message.split("@");
         return arrOfStr;   
    }
     public void GameRecord(String line){
        Data = line;
        set_Game(Data);
         System.out.println("done");
         
    }
     
    
     
      public void set_Game(String Message){
          System.out.println("MSG: "+Message);
        new Thread(new Runnable() {
            public void run(){
            boolean flag;
            Steps= parsing1(Message); 
            BeginSymbol = parsing2(Steps[Steps.length - 1]);
            playerinfo = parsing3(BeginSymbol[1]);
            player1.setText(playerinfo[0]);
            player1Symbol.setText(playerinfo[1]);
            player2.setText(playerinfo[2]);
            player2Symbol.setText(playerinfo[3]);
                System.out.println(BeginSymbol[0]+"--------------------"+BeginSymbol[1]);
            if(BeginSymbol[0].equals("X"))
                flag = true;
            else
                flag = false;
           for(int i = 0; i<Steps.length-1 ;i++)
               { 
                 switch (Steps[i]) {
                            case "1":
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn1.setText("X");
                                       }
                                     });
                            
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn1.setText("O");
                                       }
                                     });
                                break;
                            case "2":
                                if(flag)
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn2.setText("X");
                                       }
                                     });
                                else
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn2.setText("O");
                                       }
                                     });
                                break;
                            case "3":
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn3.setText("X");
                                       }
                                     });
                                else
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn3.setText("O");
                                       }
                                     });
                                break;
                            case "4":
                                if(flag)
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn4.setText("X");
                                       }
                                     });
                                else
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn4.setText("O");
                                       }
                                     });
                                break;
                            case "5":
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn5.setText("X");
                                       }
                                     });
                                else
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn5.setText("O");
                                       }
                                     });
                                break;
                            case "6":
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn6.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn6.setText("O");
                                       }
                                     });
                                break;
                            case "7":
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn7.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn7.setText("O");
                                       }
                                     });
                                break;                           
                            case "8":
                                   if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn8.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn8.setText("O");
                                       }
                                     });
                                break;
                            case "9":
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn9.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn9.setText("O");
                                       }
                                     });
                                break;
                            default:
                                break;
                        }
      
                if(flag)
                   flag = false;
                else
                   flag = true;
                try {
                    Thread.sleep(400);
                    } catch (InterruptedException ex) {
                Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
        }  
                }
           }).start(); 
 
    }
       @FXML
    private void Back_btn(ActionEvent event) 
    {
    try {
      
        FXMLLoader loader =new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/Record.fxml"));
        Parent viewParent =loader.load();
        Scene viewscene =new Scene (viewParent);
        RecordController controller =loader.getController();
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
    } catch (IOException ex) {
        Logger.getLogger(NetworkGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 
}