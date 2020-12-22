/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class NetworkGameBoardController implements Initializable, Runnable {

    @FXML
    private Label player1, player2, player1Symbol, player2Symbol;

    @FXML
    private Button Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9;
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;
    @FXML
    private Label winner_loser_txt;
    @FXML
    private Button Done_Btn;
    @FXML
    private GridPane Btns;
    @FXML
    private Pane pane2;
    int player1Score = -1;
    int player2Score = -1;
    String name;
    String opponent, mainPlayer;
    public static Thread th;
    public static boolean isPlayThreadOn=false;
    String[] parsedMsg;

    private String startGame;
    private int xoWinner = 0;
    private int xCount = 0;
    private int oCount = 0;
    int butttonUsed[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    //0 first player
    //1 second player
    //empty
    int gameState[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };
    String gameMoves = "";
    boolean secPlayer = false;

    //////////////////////////////////////////////////////////////////////////////
    public void setText(String text1, String text2, String text3, String text4, String opponent, String player, int score) {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        name = text1;
        startGame = text3;
        this.opponent = opponent;
        mainPlayer = player;
        player1Score = score;
        System.out.println("SCORE 1: " + score);
        if (opponent.equals(text1)) {
            name = player;
            secPlayer = true;
            disable();
            player2Score = score;
            System.out.println("SCORE 2: " + score);
        }
        SignIN2Controller.ps.println("PLN#" + mainPlayer);
        SignIN2Controller.ps.println("PLN#" + opponent);
    }
    //////////////////////////////////////////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isPlayThreadOn=true;
        pane2.setVisible(false);
        th = new Thread(this);
        th.start();

    }

    @FXML
    private void Done_btn(ActionEvent event) {
        try {
            SignIN2Controller.ps.println("NPLN#" + opponent);
            mediaPlayer.stop();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            ENTERController controller = loader.getController();
            if (!secPlayer) {
                controller.nPlayerScore(player1Score);
                //SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player1Score);
            } else {
                controller.nPlayerScore(player2Score);
                //SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player2Score);
            }
            controller.nPlayerName(name);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(NetworkGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            th.stop();
        }
    }

    @FXML
    private void Btn(ActionEvent event) {
        Platform.runLater(() -> {
            Button btn = (Button) event.getSource();
            String[] ID = btn.getId().split("n");
            int number = Integer.parseInt(ID[1]);
            if (butttonUsed[number - 1] == 0) {
                btn.setText(startGame);
                gameMoves += number + "#";
                gameMoves += startGame;
                gameMoves += "#";
                //System.out.println("GAME#SIGN#" + startGame + "#POS#" + number + "#" + opponent);
                SignIN2Controller.ps.println("GAME#SIGN#" + startGame + "#POS#" + number + "#" + opponent + "#" + xoWinner);

                //System.out.println("after press: " + xoWinner);
                butttonUsed[number - 1] = 1;
                gameState[number - 1] = xoWinner;
                winnerGame();
                choese();

                /*for (int arr : gameState) {
                    System.out.print(arr);
                }
                System.out.println("");*/
                disable();
            }
        });
    }

    private void choese() {
        if (startGame.equalsIgnoreCase("X")) {
            xoWinner = 1;
            startGame = "O";
        } else {
            xoWinner = 0;
            startGame = "X";
        }
    }

    private void winnerGame() {
        boolean flag = true;
        for (int[] win : winningPositions) {
            if (gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2) {
                flag = false;
                System.out.println("gameMoves WON: " + xoWinner);
                if (xoWinner == 0) {
                    player1Score++;
                    player2Score--;
                    if (player2Score < 0) {
                        player2Score = 0;
                    }

                } else if (xoWinner == 1) {
                    player2Score++;
                    player1Score--;
                    if (player1Score < 0) {
                        player1Score = 0;
                    }

                }
                if (!secPlayer) {
                //controller.nPlayerScore(player1Score);
                SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player1Score);
            } else {
                //controller.nPlayerScore(player2Score);
                SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player2Score);
            }

                disable();
                VidioShow();
            }
        }
        if (isDraw() && flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "NO Players wins ", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
            disable();

        }

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

    public boolean isDraw() {
        boolean draw = true;
        for (int btn : butttonUsed) {
            if (btn != 1) {
                draw = false;
            }
        }
        return draw;
    }

    public void VidioShow() {

        switch (xoWinner) {
            case 0: {

                winner_loser_txt.setText(player1.getText() + " is winner");
                System.out.println(player1.getText());
                if (secPlayer) {
                    winner_loser_txt.setText(player2.getText() + " is loser");
                    System.out.println(player2.getText());
                }
                break;
            }

            case 1: {
                winner_loser_txt.setText(player2.getText() + " is winner");
                System.out.println(player2.getText());
                if (!secPlayer) {
                    winner_loser_txt.setText(player1.getText() + " is loser");
                    System.out.println(player1.getText());
                }
            }
            break;
        }

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                pane2.setVisible(true);
                Done_Btn.setVisible(true);
                winner_loser_txt.setVisible(true);
                mediaView.setVisible(true);
                player1.setVisible(false);
                player2.setVisible(false);
                player1Symbol.setVisible(false);
                player2Symbol.setVisible(false);
                Btns.setVisible(false);
                String path = "build/classes/Style/video.mp4";
                media = new Media(new File(path).toURI().toString());
                // animateUsingScaleTransition(mediaView);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                mediaPlayer.setAutoPlay(true);

            }
        };
        timer.schedule(task, 500l);

    }

    public void disable() {
        Btn1.setDisable(true);
        Btn2.setDisable(true);
        Btn3.setDisable(true);
        Btn4.setDisable(true);
        Btn5.setDisable(true);
        Btn6.setDisable(true);
        Btn7.setDisable(true);
        Btn8.setDisable(true);
        Btn9.setDisable(true);
    }

    public void enableBtns() {
        Btn1.setDisable(false);
        Btn2.setDisable(false);
        Btn3.setDisable(false);
        Btn4.setDisable(false);
        Btn5.setDisable(false);
        Btn6.setDisable(false);
        Btn7.setDisable(false);
        Btn8.setDisable(false);
        Btn9.setDisable(false);
    }

    public void color(Button btn1, Button btn2, Button btn3) {
        btn1.setStyle("-fx-background-color: #cc00cc");
        btn2.setStyle("-fx-background-color: #cc00cc");
        btn3.setStyle("-fx-background-color: #cc00cc");

    }

    @Override
    public void run() {
        while (true) {
            String msg;
            String sign;
            int pos;

            try {
                msg = SignIN2Controller.dis.readLine();
                //println("recieved MSG: " + msg);
                parsing(msg);
                if (parsedMsg[0].equals("GAME") && parsedMsg[5].equals(mainPlayer)) {
                    //xoWinner=1;
                    enableBtns();
                    sign = parsedMsg[2];
                    pos = Integer.parseInt(parsedMsg[4]);
                    butttonUsed[pos - 1] = 1;
                    gameState[pos - 1] = xoWinner;
                    Platform.runLater(() -> {
                        Button btn;
                        btn = getBtn(pos);
                        btn.setText(sign);
                        gameMoves += pos + "#";
                        gameMoves += sign;
                        gameMoves += "#";

                        winnerGame();
                        choese();
                    });
                }
            } catch (IOException ex) {
                Logger.getLogger(NetworkGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void parsing(String recievedMsg) {
        parsedMsg = recievedMsg.split("\\#");
    }
}
