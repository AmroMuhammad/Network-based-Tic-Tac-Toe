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
    private Button btn_1;
    @FXML
    private Button btn_4;
    @FXML
    private Button btn_2;
    @FXML
    private Button btn_5;
    @FXML
    private Button btn_7;
    @FXML
    private Button btn_8;
    @FXML
    private Button btn_3;
    @FXML
    private Button btn_6;
    @FXML
    private Button btn_9;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
     private String startGame ;
     public void setText(String text1 , String text2 , String text3 , String text4 )
    {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        
        startGame = text3;
      
    }
 

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
    }

    @FXML
    private void btn_4(ActionEvent event) {
    }

    @FXML
    private void btn_2(ActionEvent event) {
    }

    @FXML
    private void btn_5(ActionEvent event) {
    }

    @FXML
    private void btn_7(ActionEvent event) {
    }

    @FXML
    private void btn_8(ActionEvent event) {
    }

    @FXML
    private void btn_3(ActionEvent event) {
    }

    @FXML
    private void btn_6(ActionEvent event) {
    }

    @FXML
    private void btn_9(ActionEvent event) {
    }
    
}
