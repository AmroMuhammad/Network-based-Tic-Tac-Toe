/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Screen extends Application{

    
    public static Boolean isSplashloaded = false;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("signIN.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(710);
        stage.setHeight(550);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
      
      
       launch(args);
      
    }
 
}

 
