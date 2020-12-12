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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class RecordController implements Initializable {

    @FXML
    private Button game1_btn;
    @FXML
    private Button game2_btn;
    @FXML
    private Button game3_btn;
    @FXML
    private Button game4_btn;
    @FXML
    private Button game5_btn;
    
    String flag;
    /**
     * Initializes the controller class.
     */
    public void flag_value(String FLAG)
    {
        flag = FLAG;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void back_click(ActionEvent event) throws IOException {
          
          FXMLLoader loader =new FXMLLoader();
       if (flag == "guest"){
          loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          ENTERController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
       }else{
           
          loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          NewGameController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
          
          }
    }
    
}
