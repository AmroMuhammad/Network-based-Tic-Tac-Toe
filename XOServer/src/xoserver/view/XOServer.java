/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Amr & abdelrahman
 */
public class XOServer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new ServerGUI();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("TIC TAC TOE SERVER");   //ADDED NEW
        stage.setResizable(false);  //ADDED NEW
        stage.centerOnScreen();     //ADDED NEW
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
