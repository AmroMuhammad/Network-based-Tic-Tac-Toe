/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import xoClientModel.HardLevel;
import xoClientModel.Evaluation;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
public class HardSingleGameBordController implements Initializable {

   @FXML
    private Label player1 , player2, player1Symbol ,player2Symbol;

@FXML
    private Button Btn1,Btn2,Btn3,Btn4,Btn5,Btn6,Btn7,Btn8,Btn9;
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
    
    String flag ;
    

    int moveNum = 0;
    HardLevel.Move bestMove;
    int oScore, xScore, tieScore = 0;
    Button[][] board = new Button[3][3];
        
   
      public void setText(String text1 , String text2 , String text3 , String text4 )
    {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        
        HardLevel.player = player2Symbol.getText();
        HardLevel.opponent = player1Symbol.getText();
       
         func();
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
     
        
        board [0][0] = Btn1;
        board [0][1] = Btn2;
        board [0][2] = Btn3;
        board [1][0] = Btn4;
        board [1][1] = Btn5;
        board [1][2] = Btn6;
        board [2][0] = Btn7;
        board [2][1] = Btn8;
        board [2][2] = Btn9;
      
    }
    public void func(){
        for (Button[] btns : board) {
            for (Button btn : btns) {

                btn.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {

                    btn.setText(player1Symbol.getText());
                    System.out.println("xxx");
                    btn.setMouseTransparent(true);
                    if (moveNum + 1 < 9) {
                        bestMove = HardLevel.findBestMove(board);
                        board[bestMove.row][bestMove.col].setText(player2Symbol.getText());
                        board[bestMove.row][bestMove.col].setMouseTransparent(true);
                    }
                    
                    moveNum += 2;
                    if (moveNum >= 5) {
                                  int x = 0;
                        int result = Evaluation.evaluate(board);
                        if (result == 10) {
                            System.out.println("You lost :(");
                            winner_loser_txt.setText(player1.getText()+ " is loser");
                            x = 1;
                            set_color();
                        } else if (result == -10) {
                            System.out.println("You won ^^");
                            winner_loser_txt.setText(player1.getText() + "is winner");
                            x = 1;
                            set_color();
                        } else if (HardLevel.isMoveLeft(board) == false) {
                            System.out.println("No One Wins !");
                            winner_loser_txt.setText("That's Draw");
                            x = 1;
                        }
                        if( x == 1)
                        {
                           
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
                                String path = "F:\\vidoes2/VID-20201107-WA0012.mp4";  
                                media = new Media(new File(path).toURI().toString());  
        //                      animateUsingScaleTransition(mediaView);
                                mediaPlayer = new MediaPlayer(media);
                                mediaView.setMediaPlayer(mediaPlayer);
                                mediaPlayer.setAutoPlay(true);
         
               
                            }

                          };
                                timer.schedule(task,500l);
                        }
                        
                    }
                });
            }
        }
    } 
    @FXML
    private void Done_btn(ActionEvent event) throws IOException
    {
             mediaPlayer.stop();
             FXMLLoader loader =new FXMLLoader();
             loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
             Parent viewParent =loader.load();
             Scene viewscene =new Scene (viewParent);
             NewGameController controller =loader.getController(); 
             Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
             window.setScene(viewscene);
             window.show();
    }
  

        
                    public void set_color(){
                           if(Evaluation.winRow == 123 )
                            {
                                 board[0][Evaluation.winCol].setStyle("-fx-background-color: red");
                                 board[1][Evaluation.winCol].setStyle("-fx-background-color: red");
                                 board[2][Evaluation.winCol].setStyle("-fx-background-color: red");
                             }
                             else if(Evaluation.winCol == 123)
                             {
                                 board[Evaluation.winRow][0].setStyle("-fx-background-color: red");
                                 board[Evaluation.winRow][1].setStyle("-fx-background-color: red");
                                 board[Evaluation.winRow][2].setStyle("-fx-background-color: red"); 
                             }
                             else if(Evaluation.winRow == 20)
                             {
                                 board[0][0].setStyle("-fx-background-color: red");
                                 board[1][1].setStyle("-fx-background-color: red");
                                 board[2][2].setStyle("-fx-background-color: red");
                             }
                             else if(Evaluation.winRow == 40)
                             {
                                 board[0][2].setStyle("-fx-background-color: red");
                                 board[1][1].setStyle("-fx-background-color: red");
                                 board[2][0].setStyle("-fx-background-color: red");
                             }
                           
    }

   

    
}
