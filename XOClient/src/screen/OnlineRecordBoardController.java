/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author SW
 */
public class OnlineRecordBoardController implements Initializable {

    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player1Symbol;
    @FXML
    private Label player2Symbol;
    @FXML
    private Button Btn1;
    @FXML
    private Button Btn4;
    @FXML
    private Button Btn2;
    @FXML
    private Button Btn5;
    @FXML
    private Button Btn7;
    @FXML
    private Button Btn8;
    @FXML
    private Button Btn3;
    @FXML
    private Button Btn6;
    @FXML
    private Button Btn9;

    /**
     * Initializes the controller class.
     */
    String gameMoves = "1#X#5#O#4#X#2#O#7#X#";
    String[] parsedMoves;
    @FXML
    private Button btnShow;

    public void setText(String text1, String text2, String text3, String text4, String text5, String text6) {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void btnAction(ActionEvent event) {
        parser(gameMoves);
        new Thread(new Runnable() {
            public void run() {
                Platform.runLater(() -> {
                    for (int i = 0; i < parsedMoves.length; i += 2) {
                        Button btn = new Button();
                        btn = getBtn(Integer.parseInt(parsedMoves[i]));
                        btn.setText(parsedMoves[i + 1]);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }).start();

    }

    public Button getBtn(int id) {
        Button btn;
        switch (id) {
            case 1:
                btn = Btn1;
                break;
            case 2:
                btn = Btn2;
                break;
            case 3:
                btn = Btn3;
                break;
            case 4:
                btn = Btn4;
                break;
            case 5:
                btn = Btn5;
                break;
            case 6:
                btn = Btn6;
                break;
            case 7:
                btn = Btn7;
                break;
            case 8:
                btn = Btn8;
                break;
            case 9:
                btn = Btn9;
                break;
            default:
                btn = Btn1;
                break;

        }
        return btn;
    }

    private void parser(String moves) {
        parsedMoves = moves.split("\\#");
    }

    private void hold() {
    }

    @FXML
    private void Back_btn(ActionEvent event) {
    }

}
