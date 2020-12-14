package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FreeOnlinePlayersController implements Initializable {
    
    private ScheduledExecutorService scheduledExecutorService;
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private javafx.scene.control.ListView listView;
     String Name;
     String[] playerList;
    public void set_playerName(String name)
    {
        Name = name;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        getPlayerList();
        
    } 
    @FXML
    private void loadDataTOListView(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                    list.removeAll(list);
    listView.getItems().clear();
//    String a = "marwa";
//    String b = "norhan";
//    String c = "amr";
//    String d = "abdelrahman";
//       list.addAll(a,b,c,d);
        for(String s : playerList){
            System.out.println(s);
            if(s.equals(Name))
                continue;
            list.add(s);
        }
       listView.getItems().addAll(list);
                
            }
        });

}
    
     @FXML
    private void handleMouseClickAction(javafx.scene.input.MouseEvent event) throws IOException {
     
        
    String view1 = (String) listView.getSelectionModel().getSelectedItem();
    if(view1 == null||view1.isEmpty())
            {
               System.out.println("oooops it is empty");
            }
    else{
       
       
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/GameBord.fxml"));
        Parent viewparent = loader.load();
        Scene viewscene = new Scene(viewparent);
        GameBordController controller = loader.getController();
        controller.setText(Name ,view1 ,"x" ,"o","online");
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
    }
    }
    
     @FXML
     private void back_btn(ActionEvent event) throws IOException
     {
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
        Parent viewparent = loader.load();
        Scene viewscene = new Scene(viewparent);
        ENTERController controller = loader.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show(); 
     }
     
     public void getPlayerList(){
        try {
            Socket s = new Socket(SignINController.serverIP,5008);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            PrintStream ps = new PrintStream(s.getOutputStream());
              
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
             @Override
             public void run() {
                 try {
                     // Update the chart
                     ps.println("PLIST");
                     String msg = dis.readLine();
                     parsingPlayerList(msg);
                     System.out.println(msg);
                     loadDataTOListView();
                 } catch (IOException ex) {
                     Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
         }, 0, 5, TimeUnit.SECONDS);
        } catch (IOException ex) {
            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public void parsingPlayerList(String recievedMsg){
            playerList = recievedMsg.split("\\#");
        }
     

}
