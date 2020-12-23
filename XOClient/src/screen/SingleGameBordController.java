package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.lang.ClassLoader;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class SingleGameBordController implements Initializable {

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
    String flag;
    int turnFlag = 0;//0 me / 1 pc
    int oScore, xScore, tieScore = 0;
    int isWinner = -2;// 0 => x is winner / 1 => o is winner / -1 draw
    String finalResult;
    int cpuMove = 0;
    int player = -1;
    int pc = -1;
    //0 first player
    //1 computer
    //2 empty
    int[] gameState = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2};
    /*
    {2, 2, 2, 2, 0, 2, 2, 2, 2};
    {2, 1, 2, 2, 0, 2, 2, 2, 2};
     */
    int[][] winningPositions = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };

    public ArrayList<Integer> gameMoves = new ArrayList<>();
    int statePointer = 0;
    boolean activePlayer = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    public void setText(String text1, String text2, String text3, String text4) {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        if (text3.equals("X")) {
            player = 0;
            pc = 1;
        } else {
            player = 1;
            pc = 0;
        }
        player2Symbol.setText(text4);
    }

    @FXML
    private void Done_btn(ActionEvent event) {

        try {
            mediaPlayer.stop();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
            Parent viewparent = loader.load();
            Scene viewscene = new Scene(viewparent);
            NewGameController controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SingleGameBordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Btn_action(ActionEvent event) {
        Platform.runLater(() -> {
            Button btn = (Button) event.getSource();
            String[] ID = btn.getId().split("n");//Btn1
            int number = Integer.parseInt(ID[1]);
            setTurn();
            if (btn.getText().equals("")) {
                gameMoves.add(number);
                btn.setText(getTurn());
                btn.setDisable(true);
                gameState[number - 1] = 0;
                if (Winner(player) || isDraw()) {
                    endGame();
                } else {
                    nextCpuMove();
                }
            }
        });
    }

    public void nextCpuMove() {
        if (isDraw()) {
            endGame();
        } else {
            //checkWinner();
            cpuMove = generateRand();
            if (gameMoves.contains(cpuMove)) {
                nextCpuMove();
            } else {
                int number = cpuMove;
                Button btn;
                btn = getBtn(number);
                Platform.runLater(() -> {
                    if (btn.getText().equals("")) {
                        gameMoves.add(number);
                        setTurn();
                        btn.setText(getTurn());
                        btn.setDisable(true);
                        gameState[number - 1] = 1;
                        if (Winner(pc)) {
                            endGame();
                        }
                    }
                });
            }
        }
    }

    public void endGame() {

        Btn1.setDisable(true);
        Btn2.setDisable(true);
        Btn3.setDisable(true);
        Btn4.setDisable(true);
        Btn5.setDisable(true);
        Btn6.setDisable(true);
        Btn7.setDisable(true);
        Btn8.setDisable(true);
        Btn9.setDisable(true);
        switch (isWinner) {
            case 0: {
                finalResult = "Player X is the winner \n";
                if (player1Symbol.getText().equals("X")) {
                    winner_loser_txt.setText(player1.getText() + " is winner");
                } else {
                    winner_loser_txt.setText(player2.getText() + " is winner");
                }

                break;
            }
            case 1: {
                finalResult = "Player O is the winner \n";
                if (player1Symbol.getText().equals("O")) {
                    winner_loser_txt.setText(player1.getText() + " is winner");
                } else {
                    winner_loser_txt.setText(player2.getText() + " is winner");
                }
                break;
            }
            case -1: {
                finalResult = "That's a Draw \n";
                winner_loser_txt.setText("That's a Draw ");
                break;
            }
        }

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

    public void setTurn() {

        if (turnFlag == 0) {
            turnFlag = 1;
        } else {
            turnFlag = 0;
        }
    }

    public String getTurn() {
        if (turnFlag == 0) {
            return player2Symbol.getText();//"X";
        } else {
            return player1Symbol.getText();//"O";
        }
    }

    public int generateRand() {
        return (int) (Math.random() * ((9 - 1) + 1)) + 1;
    }

    public boolean Winner(int player) {
        boolean winnerResult = false;
        for (int[] win : winningPositions) {
            if (gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2) {
                winnerResult = true;
                isWinner = player;

            }
        }
        return winnerResult;
    }

    public boolean isDraw() {

        boolean draw = false;
        if (Btn1.isDisabled() && Btn2.isDisabled() && Btn3.isDisabled()
                && Btn4.isDisabled() && Btn5.isDisabled() && Btn6.isDisabled()
                && Btn7.isDisabled() && Btn8.isDisabled() && Btn9.isDisabled()) {
            isWinner = -1;
            draw = true;
        }
        return draw;
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

    public void color(Button b1, Button b2, Button b3) {
        b1.setStyle("-fx-background-color: red");
        b2.setStyle("-fx-background-color: red");
        b3.setStyle("-fx-background-color: red");
    }

    private void animateUsingScaleTransition(MediaView heart) {
        ScaleTransition scaleTransition = new ScaleTransition(
                Duration.seconds(1), heart
        );
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setFromZ(1);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setToZ(1.1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.play();
    }

}
