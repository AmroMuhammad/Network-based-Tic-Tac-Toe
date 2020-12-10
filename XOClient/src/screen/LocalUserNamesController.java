/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class LocalUserNamesController implements Initializable {

    @FXML
    private TextField player1 , player2;

     @FXML
    private void handleLocalPlayersAction(ActionEvent event) throws IOException {
       
        
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("GameBord.fxml"));
        Parent viewparent = loader.load();
        Scene viewscene = new Scene(viewparent);
        GameBordController controller = loader.getController();
        controller.setText(player1.getText() ,player2.getText() ,"x" ,"o" , "local");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
    }
    @FXML
    private void back_btn(ActionEvent event) throws IOException{
      
        FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("newGame.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          NewGameController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
