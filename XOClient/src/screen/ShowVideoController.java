/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.File;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class ShowVideoController implements Initializable {
 @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;
    String flag;
    
     public void FlagValue(String FLAG)
    {
       
        flag = FLAG;
      
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       String path = "F:\\vidoes2/VID-20201107-WA0012.mp4";  
          
        //Instantiating Media class  
         media = new Media(new File(path).toURI().toString());  
         mediaPlayer = new MediaPlayer(media);
         mediaView.setMediaPlayer(mediaPlayer);
         mediaPlayer.setAutoPlay(true);
         
         
         
    } 
    @FXML
    private void done_btn(ActionEvent event) throws IOException{
        mediaPlayer.stop();
        FXMLLoader loader =new FXMLLoader();
          if(flag == "online")
          {
             loader.setLocation(getClass().getResource("ENTER.fxml"));
             Parent viewParent =loader.load();
             Scene viewscene =new Scene (viewParent);
             ENTERController controller =loader.getController(); 
             Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
             window.setScene(viewscene);
             window.show();
          }
          else if (flag == "local" || flag == "single")
          {
              loader.setLocation(getClass().getResource("newGame.fxml"));
             Parent viewParent =loader.load();
             Scene viewscene =new Scene (viewParent);
             NewGameController controller =loader.getController(); 
             Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
             window.setScene(viewscene);
             window.show();
          }
          
          
    }
    
    
    
}
