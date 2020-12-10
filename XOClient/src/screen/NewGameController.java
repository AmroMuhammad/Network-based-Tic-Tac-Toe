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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class NewGameController implements Initializable {

    @FXML
    private Button singleplay_btn;
    @FXML
    private Button multLOCALi_btn;
    @FXML
    private Button Record_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void back_click(ActionEvent event) throws IOException {
          System.out.println("you clicked me!");
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("signIN.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          SignINController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
     @FXML
    private void HandleSinglePlayer(ActionEvent event) throws IOException
    {
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("SingleUserName.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          SingleUserNameController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    @FXML
    private void HandleLocalPlayers(ActionEvent event) throws IOException
    {
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("LocalUserNames.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          LocalUserNamesController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
     @FXML
    private void RecordingAction(ActionEvent event) throws IOException {
       
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("record.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          RecordController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    
}
