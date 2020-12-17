/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
    private Label player1 , player2, player1Symbol ,player2Symbol;

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
    @FXML
    private Button btn_1 ,btn_2,btn_3,btn_4,btn_5, btn_6,btn_7,btn_8,btn_9;
   
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
        
        startGame = text3;
      
    }
     private String startGame ;
     private int xCount =0;
     private int oCount =0;
     int butttonUsed [] = {0,0,0,0,0,0,0,0,0};
 

    @FXML
    private void Done_btn(ActionEvent event) {
        try {
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
    private void btn_1(ActionEvent event) {
        if(butttonUsed[0]==0){
        btn_1.setText(startGame);
        butttonUsed[0]=1;
        choese();
        winnerGame();
    }
    }
   
    @FXML
    private void btn_2(ActionEvent event) {
        if(butttonUsed[1]==0){
        btn_2.setText(startGame);
        butttonUsed[1]=1;
        choese();
        winnerGame();
    }}
    
    @FXML
    private void btn_3(ActionEvent event) {
        if(butttonUsed[2]==0){
        btn_3.setText(startGame);
        butttonUsed[2] = 1;
        choese();
        winnerGame();
    }}
    
    @FXML
    private void btn_4(ActionEvent event) {
        if(butttonUsed[3]==0){ 
        btn_4.setText(startGame);
        butttonUsed[3]=1;
        choese();
        winnerGame();
    }}
   
    @FXML
    private void btn_5(ActionEvent event) {
        if(butttonUsed[4]==0){ 
        btn_5.setText(startGame);
        butttonUsed[4]=1;
       
        choese();
        winnerGame();
    }}

    @FXML
    private void btn_6(ActionEvent event) {
        if(butttonUsed[5]==0){ 
        btn_6.setText(startGame);
        butttonUsed[5]=1;
        choese();
        winnerGame();
    }
    }
    @FXML
    private void btn_7(ActionEvent event) {
        if(butttonUsed[6]==0){ 
        btn_7.setText(startGame);
        butttonUsed[6]=1;
        choese();
        winnerGame();
    }
    }

    @FXML
    private void btn_8(ActionEvent event) {
        if(butttonUsed[7]==0){
        btn_8.setText(startGame);
        butttonUsed[7]=1;
        choese();
        winnerGame();
        }
    }

    @FXML
    private void btn_9(ActionEvent event) {
        if(butttonUsed[8]==0){
        btn_9.setText(startGame);
        butttonUsed[8]=1;
        choese();
        winnerGame();
    }
 
}
    private void choese (){
         
        if (startGame.equalsIgnoreCase("X"))
        {
            startGame ="O";
        }
        else 
        {
            startGame ="X";
        }
    }
     private void winnerGame(){
        String b1 = btn_1.getText();
        String b2 = btn_2.getText();
        String b3 = btn_3.getText();
        String b4 = btn_4.getText();
        String b5 = btn_5.getText();
        String b6 = btn_6.getText();
        String b7 = btn_7.getText();
        String b8 = btn_8.getText();
        String b9 = btn_9.getText();
        
        
        if(b1.equals(b2) &&  b1.equals(b3)&& (b1=="X"|| b1=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+ b1 + " wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_1 ,btn_2 ,btn_3);
            disable();
        
        }
        else if(b4.equals(b5) &&  b4.equals(b6) &&(b4=="X"|| b4=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+ b4 +" wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_4 ,btn_5 ,btn_6);
            disable();
            
        }
        else if(b7.equals(b8)&& b7.equals(b9)&&(b7=="X"|| b7=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+b7+" wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_7 ,btn_8 ,btn_9);
            disable();
        }
        else if(b1.equals(b4)&& b1.equals(b7)&&(b1=="X"|| b1=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+b1+" wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_1 ,btn_4 ,btn_7);
            disable();
        }
        else if(b2.equals(b5)&& b2.equals(b8)&&(b2=="X"|| b2=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+b2+" wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_2 ,btn_5 ,btn_8);
            disable();
        }
        else if(b3.equals(b6)&& b3.equals(b9)&&(b3=="X"|| b3=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+b3+" wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_3 ,btn_6 ,btn_9);
            disable();
        }
        else if(b1.equals(b5)&& b1.equals(b9)&&(b1=="X"|| b1=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+b1+" wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_1 ,btn_5 ,btn_9);
            disable();
        }
        else if(b3.equals(b5)&& b3.equals(b7)&&(b3=="X"|| b3=="O"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Player "+ b3 +" wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            color(btn_3 ,btn_5 ,btn_7);
            disable();
        }
        
          
         else if (butttonUsed[0] == 1 &&  butttonUsed[1] == 1 &&
                 butttonUsed[2] == 1 &&  butttonUsed[3] == 1 &&
                 butttonUsed[4] == 1 &&  butttonUsed[5] == 1 &&
                 butttonUsed[6] == 1 &&  butttonUsed[7] == 1 &&
                 butttonUsed[8] == 1 )
        {
               Alert alert = new Alert(Alert.AlertType.INFORMATION, "NO Players wins ",ButtonType.OK);
               alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
               alert.show();
               disable();
        }
 
    }
    
    public void disable ()
        {
            btn_1.setDisable(true);
            btn_2.setDisable(true);
            btn_3.setDisable(true);
            btn_4.setDisable(true);
            btn_5.setDisable(true);
            btn_6.setDisable(true);
            btn_7.setDisable(true);
            btn_8.setDisable(true);
            btn_9.setDisable(true);
    }
    public void color(Button btn1, Button btn2  , Button btn3)
       {
            btn1.setStyle("-fx-background-color: #cc00cc");
            btn2.setStyle("-fx-background-color: #cc00cc");
            btn3.setStyle("-fx-background-color: #cc00cc");
   
    }

    
}
