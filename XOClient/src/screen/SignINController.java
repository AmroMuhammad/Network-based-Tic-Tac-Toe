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
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class SignINController implements Initializable {

    @FXML
    private TextField userName_txt;
    @FXML
    private TextField password_txt;
    @FXML
    private Button signIN_btn;
    @FXML
    private Button signUP_btn;
    @FXML
    private AnchorPane root;
    @FXML
    private Button signIN_btn1;
   
   

  
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       if(!Screen.isSplashloaded){
       loadSplashScreen();
       }
 
    }    

    private void loadSplashScreen() {
        
        try {
            Screen.isSplashloaded=true;
            AnchorPane pane=FXMLLoader.load(getClass().getResource("splash.fxml"));
            root.getChildren().setAll(pane);
           
            FadeTransition fadeIn =new FadeTransition(Duration.seconds(3),pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);
            
            
            FadeTransition fadeOut =new FadeTransition(Duration.seconds(4),pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);
       
            fadeIn.play();
            fadeIn.setOnFinished((e)->{
                fadeOut.play();
            });
            
            fadeOut.setOnFinished((e)->{
               try {
                   AnchorPane parentConted =FXMLLoader.load(getClass().getResource("signIN.fxml"));
                   root.getChildren().setAll(parentConted);
                } catch (IOException ex) {
                    Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
                }
              
                
            });
            
        } catch (IOException ex) {
            Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signUP_click(ActionEvent event) {
       
        try {
            FXMLLoader loader =new FXMLLoader();
            loader.setLocation(getClass().getResource("signUP.fxml"));
            Parent viewParent =loader.load();
            Scene viewscene =new Scene (viewParent);
            SignUPController controller =loader.getController();
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    
    private void signIN_click (ActionEvent event) {
        String name = userName_txt.getText();
        String pass = password_txt.getText();
        if(name.isEmpty() || pass.isEmpty() || name.contains(" ") || pass.contains(" "))
        {
     
            Alert alert = new Alert(AlertType.ERROR, "please, insert your data in this fields", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        }else
        {
            try {
                // String Data  = "ABC#"+name+"#"+pass;
                // System.out.println(Data);
                //  new client(Data );
                //System.out.println(C.mssg + "--------------------------------");
                
                
                FXMLLoader loader =new FXMLLoader();
                loader.setLocation(getClass().getResource("ENTER.fxml"));
                Parent viewParent =loader.load();
                Scene viewscene =new Scene (viewParent);
                ENTERController controller =loader.getController();
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(viewscene);
                window.show();
            } catch (IOException ex) {
                Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    @FXML
    private void guest_click(ActionEvent event) throws IOException {
          System.out.println("you clicked me!");
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("newGame.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          NewGameController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }

   
    
}
