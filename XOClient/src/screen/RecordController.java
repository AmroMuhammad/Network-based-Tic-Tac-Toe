package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class RecordController implements Initializable {

    @FXML
    private Button Btn1, Btn2, Btn3, Btn4, Btn5;

    String flag;
    public String[] lines;
    BufferedReader br;
    FileReader fr;
    String L;

    /**
     * Initializes the controller class.
     */
    public void flag_value(String FLAG) {
        flag = FLAG;
    }

    public String[] parsing_lines(String requestMessage) {
        String[] arrOfStr = requestMessage.split("!");
        return arrOfStr;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FileReader fr;
        try {
            fr = new FileReader("GameRecord.txt");
            BufferedReader br = new BufferedReader(fr);
            L = br.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Btn_click(ActionEvent event) {
        boolean flag_switch = true;
        Button btn = (Button) event.getSource();
        String[] ID = btn.getId().split("n");
        int number = Integer.parseInt(ID[1]);
        System.out.println(ID);
        try {
            lines = parsing_lines(L);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/GameBord.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            GameBordController controller = loader.getController();
            if ((lines.length - (number - 1)) > 0) {
                controller.GameRecord(lines[lines.length - number]);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(viewscene);
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No game recording is available", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                alert.show();
                flag_switch = false;
            }

        } catch (IOException ex) {
            Logger.getLogger(RecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void back_click(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        if (flag == "guest") {
            loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            ENTERController controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } else {

            loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            NewGameController controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();

        }
    }
}
