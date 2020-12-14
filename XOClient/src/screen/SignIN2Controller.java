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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import xoClientModel.signINBase;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class SignIN2Controller implements Initializable {

    @FXML
    private TextField userName_txt;
    @FXML
    private Button signIN_btn;
    @FXML
    private Button signUP_btn;
    @FXML
    private PasswordField password_txt;
    Socket SClient;
    DataInputStream dis;
    PrintStream ps;
    String ip;
    private int score=0;
    /**
     * Initializes the controller class.
     */
    public void ip_value(String IP) {
        ip = IP;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void signUP_click(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/signUP.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            SignUPController controller = loader.getController();
            controller.ip_value(ip);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML

    private void signIN_click(ActionEvent event) {
        String name = userName_txt.getText();
        String pass = password_txt.getText();
        if (name.isEmpty() || pass.isEmpty() || name.contains(" ") || pass.contains(" ")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data here is required without spaces.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
        } else {
            try {
                SClient = new Socket(ip, 5008);
                dis = new DataInputStream(SClient.getInputStream());
                ps = new PrintStream(SClient.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(signINBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            String Data = "SIN#" + name + "#" + pass + "#" + SignUPController.getHostIPAddress();
            System.out.println(Data);
            ps.println(Data);
            ps.flush();

            new Thread(new Runnable() {
                public void run() {

                    try {
                        String msg = dis.readLine();
                        System.out.println(" my massege is ....." + msg);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String parsedMsg=SignInParser(msg);
                                if (!parsedMsg.equals("SignIN Confirmed")) {

                                    Alert alert = new Alert(Alert.AlertType.ERROR, "invalid username or password", ButtonType.OK);
                                    alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                                    alert.show();
                                } else {

                                    try {
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
                                        Parent viewParent;
                                        viewParent = loader.load();
                                        Scene viewscene = new Scene(viewParent);
                                        ENTERController controller = loader.getController();
                                        controller.nPlayerName(userName_txt.getText());
                                        controller.nPlayerScore(score);
                                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        window.setScene(viewscene);
                                        window.show();
                                    } catch (IOException ex) {
                                        Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            }
                        });
                    } catch (IOException ex) {
                        Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }).start();
        }

    }

    @FXML
    private void back_click(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/signIN.fxml"));
        Parent viewParent;
        try {
            viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            SignINController controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String SignInParser(String msg) {
        String [] parsedMsg = msg.split("\\#");
        if(parsedMsg[0].equals("SignIN Confirmed"))
            score=Integer.parseInt(parsedMsg[1]);
        return parsedMsg[0];
    }
}
