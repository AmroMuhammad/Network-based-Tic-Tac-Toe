package xoserver.view;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import xoserver.model.DatabaseConnection;
import xoserver.model.GameHandler;
import xoserver.model.MainServer;

public class ServerGUI extends AnchorPane{

    protected final RadioButton btnOn;
    protected final RadioButton btnOff;
    protected final Text text;
    protected final Text txtServerStatus;
    protected final PieChart usersChart;
    protected final ToggleGroup group;
    private DatabaseConnection databaseConnection;
    private MainServer gameMain;
    ObservableList<PieChart.Data> pieChartData;
    private ScheduledExecutorService scheduledExecutorService;

    public ServerGUI() {
        btnOn = new RadioButton();
        btnOff = new RadioButton();
        text = new Text();
        txtServerStatus = new Text();
        group = new ToggleGroup();
        
        //added part
        databaseConnection = DatabaseConnection.getDatabaseInstance();
        databaseConnection.openConnection();  //initialize database with server
        pieChartData =FXCollections.observableArrayList(
        new PieChart.Data("Offline",databaseConnection.numOfflinePlayers()),
        new PieChart.Data("Online",databaseConnection.numOnlinePlayers()),       
        new PieChart.Data("playing",databaseConnection.numOnlinePlayers())               //initialize pie chart 
        );
        usersChart = new PieChart(pieChartData);
        usersChart.setVisible(false);
        usersChart.setAnimated(false);
                
        

        setId("AnchorPane");
        setPrefHeight(498.0);
        setPrefWidth(680.0);
        setStyle("-fx-background-color: #9BD8BB;");

        btnOn.setLayoutX(511.0);
        btnOn.setLayoutY(266.0);
        btnOn.setMnemonicParsing(false);
        btnOn.setText("ON");
        btnOn.setTextFill(javafx.scene.paint.Color.valueOf("#0a7c26"));

        btnOff.setLayoutX(511.0);
        btnOff.setLayoutY(295.0);
        btnOff.setMnemonicParsing(false);
        btnOff.setText("OFF");
        btnOff.setTextFill(javafx.scene.paint.Color.RED);

        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setLayoutX(135.0);
        text.setLayoutY(105.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("TIC TAC TOE SERVER");
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text.setWrappingWidth(410.13671875);
        text.setFont(new Font("Cambria Math", 40.0));

        AnchorPane.setRightAnchor(txtServerStatus, 37.86328125);
        txtServerStatus.setFill(javafx.scene.paint.Color.WHITE);
        txtServerStatus.setLayoutX(422.0);
        txtServerStatus.setLayoutY(244.0);
        txtServerStatus.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        txtServerStatus.setStrokeWidth(0.0);
        txtServerStatus.setText("Server is OFF");
        txtServerStatus.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        txtServerStatus.setWrappingWidth(220.13671875);
        txtServerStatus.setFont(new Font("Cambria Math", 20.0));

        usersChart.setLayoutX(74.0);
        usersChart.setLayoutY(178.0);
        usersChart.setLegendSide(javafx.geometry.Side.LEFT);
        usersChart.setPrefHeight(234.0);
        usersChart.setPrefWidth(304.0);
        usersChart.setTitle("Users Chart");
        usersChart.setTitleSide(javafx.geometry.Side.BOTTOM);

        getChildren().add(btnOn);
        getChildren().add(btnOff);
        getChildren().add(text);
        getChildren().add(txtServerStatus);
        getChildren().add(usersChart);

        //added parts
        
        btnOn.setToggleGroup(group);
        btnOff.setToggleGroup(group);
        btnOff.setSelected(true);
        
        
        togglingButtons();
        gameMain = new MainServer();

        btnOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameMain.start();
                usersChart.setVisible(true); 
            }
        });

        btnOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameMain.stop();  //stops main server when server is down (so when client enters server he cant send)
                gameMain.stopClients();  //stops sockets threads at clients side
                usersChart.setVisible(false); 
            }
        });
   
    }

    public void togglingButtons() {
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton tempBtn = (RadioButton) group.getSelectedToggle();
                if (tempBtn != null) {
                    String s = tempBtn.getText();
                    if (s.equals("ON")) {
                        txtServerStatus.setText("Server is running");
                    } else {
                        txtServerStatus.setText("Server is down");
                    }
                }
            }
        });
        
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        refreshPieChart();
    }
  
    public void refreshPieChart(){
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
             @Override
             public void run() {
                 // Update the chart

                 Platform.runLater(new Runnable() {
                     @Override
                     public void run() {
                         // put random number with current time
                         pieChartData.set(0,new PieChart.Data("Offline",GameHandler.offlinePlayers));
                         pieChartData.set(1,new PieChart.Data("Online",GameHandler.onlinePlayers));
                         pieChartData.set(2,new PieChart.Data("playing",GameHandler.playingPlayers));
                     }
                 });
             }
         }, 15, 1, TimeUnit.SECONDS);
    }
}
