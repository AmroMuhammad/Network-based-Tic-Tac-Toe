package xoserver.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import xoserver.model.DatabaseConnection;
import xoserver.model.MainServer;

public class ServerGUI extends AnchorPane {

    protected final RadioButton btnOn;
    protected final RadioButton btnOff;
    protected final TextField txtServerStatus;
    protected final ToggleGroup group;
    //protected final TextField txtTest;
    private DatabaseConnection databaseConnection;
    private MainServer gameMain;

    public ServerGUI() {

        btnOn = new RadioButton();
        btnOff = new RadioButton();
        txtServerStatus = new TextField();
        group = new ToggleGroup();
        //txtTest = new TextField();

        setId("AnchorPane");
        setPrefHeight(200);
        setPrefWidth(320);

        btnOn.setLayoutX(102.0);
        btnOn.setLayoutY(100.0);
        btnOn.setMnemonicParsing(false);
        btnOn.setText("ON");

        btnOff.setLayoutX(102.0);
        btnOff.setLayoutY(129.0);
        btnOff.setMnemonicParsing(false);
        btnOff.setText("OFF");

        txtServerStatus.setEditable(false);
        txtServerStatus.setLayoutX(86.0);
        txtServerStatus.setLayoutY(52.0);
        txtServerStatus.setText("Server is down");

//        txtTest.setEditable(false);
//        txtTest.setLayoutX(86.0);
//        txtTest.setLayoutY(32.0);
//        txtTest.setText("Hola");

        btnOn.setToggleGroup(group);
        btnOff.setToggleGroup(group);
        btnOff.setSelected(true);

        getChildren().add(btnOn);
        getChildren().add(btnOff);
        getChildren().add(txtServerStatus);
//        getChildren().add(txtTest);

        // databaseThread = new Thread(this);
        togglingButtons();
        gameMain = new MainServer();
        databaseConnection = DatabaseConnection.getDatabaseInstance();
        databaseConnection.openConnection();  //initialize database with server

        btnOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                txtTest.setText("hooo");
                gameMain.start();
            }

        });

        btnOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                txtTest.setText("hiiii");
                gameMain.stop();  //stops main server when server is down (so when client enters server he cant send)
                gameMain.stopClients();  //stops sockets threads at clients side
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
    }

}
