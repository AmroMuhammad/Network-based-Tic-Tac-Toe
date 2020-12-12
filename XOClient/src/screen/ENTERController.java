package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ENTERController implements Initializable {

    @FXML
    private Label playerName;
    @FXML
    private Label score;
    @FXML
    private Button newGame_txt;
    @FXML
    private Button record_txt;
    String Name;
    /**
     * Initializes the controller class.
     */
    public void NplayerName(String name)
    {
        Name = name;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

   

    @FXML
    private void recordAction(ActionEvent event) throws IOException {
        
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/record.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          RecordController controller =loader.getController();
          controller.flag_value("guest");
          
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    
    @FXML
    private void NewGameAction(ActionEvent event) throws IOException {
       
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/FreeOnlinePlayers.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          FreeOnlinePlayersController controller =loader.getController();
          controller.set_playerName(Name);
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    


    @FXML
    private void back_click(ActionEvent event) throws IOException {
        
         
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/signIN2.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          SignIN2Controller controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }

    
}
