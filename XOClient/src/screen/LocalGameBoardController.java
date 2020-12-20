/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class LocalGameBoardController implements Initializable {
 @FXML
    private Label player1, player2, player1Symbol, player2Symbol;

    @FXML
    private Button Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9;
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;
    @FXML
    private Label winner_loser_txt;
    @FXML
    private Button Done_Btn;
    @FXML
    private GridPane Btns;
    @FXML
    private Pane pane2;
    public ArrayList<Integer> gameMoves = new ArrayList<>();
    private String startGame ;
    int xoWinner=-2;
    int buttonUsed [] = {0,0,0,0,0,0,0,0,0};
    int[][] winningPositions = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
     public void setText(String text1 , String text2 , String text3 , String text4 )
    {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        startGame =text3;
  
    }
 

    @FXML
    private void Done_btn(ActionEvent event) {
        try {
            mediaPlayer.stop();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
            Parent viewparent = loader.load();
            Scene viewscene = new Scene(viewparent);
            NewGameController controller = loader.getController();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LocalGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void Btn_action(ActionEvent event) {
       
        Platform.runLater(() -> {
        Button btn=(Button)event.getSource();
        String[] ID = btn.getId().split("n");
        int number = Integer.parseInt(ID[1]);
        if(buttonUsed[number-1]==0){
            if(startGame.equals("X"))
            {
                btn.setText(startGame);
                buttonUsed[number-1]=1; 
            }
            else
            {
                btn.setText(startGame);
                buttonUsed[number-1]=2;
            }
            gameMoves.add(number);
        choese();
        WinnerGame();
        }
         });
   
       
    }
    
    public void WinnerGame() {
        
        for (int[] win : winningPositions) {
            if (buttonUsed[win[0]] == buttonUsed[win[1]] && buttonUsed[win[1]] == buttonUsed[win[2]] && buttonUsed[win[0]] != 0) {
               
                if (buttonUsed[win[0]]==1){ xoWinner= 0;}
                else {xoWinner= 1;}
                disable();
                winnerName();
                VidioShow("F:\\\\vidoes2/VID-20201107-WA0012.mp4");
               
            }
            else
            {
                if(buttonUsed[0] != 0 && buttonUsed[1] != 0 && buttonUsed[2] != 0 && buttonUsed[3] != 0
                        && buttonUsed[4] != 0 && buttonUsed[5] != 0 && buttonUsed[6] != 0 && buttonUsed[7] != 0 
                        && buttonUsed[8] != 0)
                {
                    xoWinner = -1;
                    winnerName();
                    disable();
                    winner_loser_txt.setText("No players wins ");
                    VidioShow("F:\\\\vidoes2/VID-20201107-WA0012.mp4");
                    break;
                    
                }
               
            }
        }
    }
    
    private void choese (){
    
        if (startGame.equals("X"))
        {
            startGame ="O";
        }
        else 
        {
            startGame ="X";
        }  
    }
    
     public void winnerName(){
        String Data ="" ;
        for (int i = 0; i < gameMoves.size();i++ )
        {
           Data += gameMoves.get(i) + "#";
            System.out.println("moveeeeee" + Data);
        }
        System.out.println(Data);
         switch (xoWinner) {
            case 0:{
                
                if(player1Symbol.getText().equals("X")){
                    
                    winner_loser_txt.setText(player1.getText()+" is winner");
                    System.out.println(player1.getText());
                    Data += player1Symbol.getText()+"&"+ player1.getText()+ "@" + "X@"+player2.getText()+"@O@";
                }
                else{
                    winner_loser_txt.setText(player2.getText()+" is winner");
                    System.out.println(player2.getText());
                    Data += player1Symbol.getText()+"&"+ player2.getText() + "@" + "X@"+player1.getText()+"@O@";
                }
               
                break;
            }
            case 1:{
                
                if(player1Symbol.getText().equals("O")){
                    winner_loser_txt.setText(player1.getText()+" is winner");
                    System.out.println(player1.getText());
                    Data +=player1Symbol.getText()+"&"+  player1.getText()+ "@" + "O@"+player2.getText()+"@X@";
                }
                else{
                    winner_loser_txt.setText(player2.getText()+" is winner");
                    System.out.println(player2.getText());
                    Data +=player1Symbol.getText()+"&"+ player2.getText() + "@" + "O@"+player1.getText()+"@X@";
                }
                break;
            }
            case -1: {
                Data += player1Symbol.getText()+"&"+ player1.getText() + "@"+ player1Symbol.getText() +"@"+player2.getText()+"@"+ player2Symbol.getText()+"@";
                break;
            }
            
        }
         System.out.println(Data);
        BufferedWriter out ;
       
     try {
         out = new BufferedWriter(
         new FileWriter("GameRecord.txt", true));
         out.write(Data + "!");
         out.close();  
     } catch (IOException ex) {
         Logger.getLogger(LocalGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
     }
            
    
    }
     
    public void VidioShow(String vidioPath){
         
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
        public void run()
        {
         pane2.setVisible(true);
         Done_Btn.setVisible(true);
         winner_loser_txt.setVisible(true); 
         mediaView.setVisible(true);
         player1.setVisible(false);
         player2.setVisible(false);
         player1Symbol.setVisible(false);
         player2Symbol.setVisible(false);
         Btns.setVisible(false);
         String path = vidioPath;  
         media = new Media(new File(path).toURI().toString());  
        // animateUsingScaleTransition(mediaView);
         mediaPlayer = new MediaPlayer(media);
         mediaView.setMediaPlayer(mediaPlayer);
         mediaPlayer.setAutoPlay(true);
         }
        };
        timer.schedule(task,500l); 
       
        
    }
     public void disable ()
        {
            Btn1.setDisable(true);
            Btn2.setDisable(true);
            Btn3.setDisable(true);
            Btn4.setDisable(true);
            Btn5.setDisable(true);
            Btn6.setDisable(true);
            Btn7.setDisable(true);
            Btn8.setDisable(true);
            Btn9.setDisable(true);
    }
    public void color(Button btn1, Button btn2  , Button btn3)
       {
            btn1.setStyle("-fx-background-color: #cc00cc");
            btn2.setStyle("-fx-background-color: #cc00cc");
            btn3.setStyle("-fx-background-color: #cc00cc");
   
    }
    
     
}
