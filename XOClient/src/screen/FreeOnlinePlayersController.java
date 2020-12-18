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
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javax.swing.JPanel;

/**
 * FXML Controller class
 *
 * @author Dell
 */
public class FreeOnlinePlayersController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private javafx.scene.control.ListView listView;
    String userName;
    String[] parsedMsg;
    boolean flag = false;
    Socket s;
    Socket s2;
    DataInputStream dis;
    DataInputStream dis2;
    PrintStream ps;
    PrintStream ps2;
    Thread requestThread;
    Thread replyThread;
    @FXML
    private ProgressIndicator waitingIndicator;

    public void set_playerName(String name) {
        userName = name;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            waitingIndicator.setVisible(false);
            s = new Socket(SignINController.serverIP, 5008);
            s2 = new Socket(SignINController.serverIP, 5008);
            dis = new DataInputStream(s.getInputStream());
            dis2 = new DataInputStream(s2.getInputStream());
            ps = new PrintStream(s.getOutputStream());
            ps2 = new PrintStream(s2.getOutputStream());
            System.out.println("opened everything");
            getPlayerListAndPlayRequest();
        } catch (IOException ex) {
            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDataTOListView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                list.removeAll(list);
                listView.getItems().clear();
                for (String s : parsedMsg) {
                    if (s.equals(userName) || s.equals("PLIST")) {
                        continue;
                    }
                    list.add(s);
                }
                listView.getItems().addAll(list);
            }
        });
    }

    @FXML
    private void handleMouseClickAction(javafx.scene.input.MouseEvent event) throws IOException {
        String opponentName = (String) listView.getSelectionModel().getSelectedItem();
        if (opponentName == null || opponentName.isEmpty()) {
            System.out.println("oooops it is empty");
            System.out.println("if " + userName + " and " + opponentName);
        } else {
            listView.setMouseTransparent(true);
            listView.setFocusTraversable(false);
            waitingIndicator.setVisible(true);
            waitingIndicator.setProgress(-1);
            requestThread = new Thread() {
                public void run() {
                    String sentMsg = new String("DUWTP#" + opponentName + "#" + userName);
                    ps2.println(sentMsg);
                    System.out.println("pressed on" + opponentName);
                    int d = 0;
                    while (true) {
                        String recievedReqeustMsg = null;
                        try {
                            System.out.println(++d + "");
                            recievedReqeustMsg = dis2.readLine();
                            parsing(recievedReqeustMsg);
                        } catch (IOException ex) {
                            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.out.println(recievedReqeustMsg);
                        if (parsedMsg[0].equals("PREQ") && parsedMsg[1].equals("accept") && parsedMsg[2].equals(userName)) {
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        System.out.println("acception received");
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("/xoClientView/GameBord.fxml"));
                                        Parent viewparent = loader.load();
                                        Scene viewscene = new Scene(viewparent);
                                        GameBordController controller = loader.getController();
                                        controller.setText(userName, opponentName, "x", "o", "online");
                                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        System.out.println("stage: " + window.toString());
                                        System.out.println("javafx.stage.Stage@482c88ed");
                                        window.setScene(viewscene);
                                        window.show();

                                    } catch (IOException ex) {
                                        Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                                    } finally {
                                        try {
                                            dis2.close();
                                            ps2.close();
                                            s2.close();
                                            ps.close();
                                            dis.close();
                                            s.close();
                                            requestThread.stop();
                                            replyThread.stop();
                                        } catch (IOException ex) {
                                            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            });
                        } else if (parsedMsg[0].equals("PREQ") && parsedMsg[1].equals("reject") && parsedMsg[2].equals(userName)) {
                            System.out.println("rejection received");
                            listView.getSelectionModel().clearSelection();
                            listView.setMouseTransparent(false);
                            listView.setFocusTraversable(true);
                            waitingIndicator.setVisible(false);
                            requestThread.stop();
                        }
                    }
                }
            };
            requestThread.start();
        }
    }

    @FXML
    private void back_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
        Parent viewparent = loader.load();
        Scene viewscene = new Scene(viewparent);
        ENTERController controller = loader.getController();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
        dis2.close();
        ps2.close();
        s2.close();
        ps.close();
        dis.close();
        s.close();
        requestThread.stop();
        replyThread.stop();

    }

    public void getPlayerListAndPlayRequest() {
        replyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ps.println("PLIST");
                while (true) {
                    try {
                        String msg = dis.readLine();
                        parsing(msg);
                        if (parsedMsg[0].equals("DUWTP") && parsedMsg[1].equals(userName)) {
                            final String oppName = parsedMsg[2];
                            System.out.println("play request for me  " + oppName);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (confirmationToPlay() == true) {
                                        System.out.println("acception sent");
                                        String sentMsg = new String("PREQ#accept#" + oppName + "#" + userName);
                                        ps.println(sentMsg);
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                try {
                                                    ps.close();
                                                    dis.close();
                                                    s.close();
                                                    showBoardForOpponent(oppName, userName);
                                                } catch (IOException ex) {
                                                    Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                                                } finally {
                                                    replyThread.stop();
                                                }
                                            }
                                        });
                                    } else {
                                        System.out.println("rejection sent");
                                        String sentMsg = new String("PREQ#reject#" + oppName + "#" + userName);
                                        System.out.println(sentMsg);
                                        ps.println(sentMsg);
                                    }
                                }
                            });
                        } else if (parsedMsg[0].equals("PLIST")) {
                            loadDataTOListView();
                            ps.println("PLIST");
                        }
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        replyThread.start();
    }

    public void parsing(String recievedMsg) {
        parsedMsg = recievedMsg.split("\\#");
    }

    public boolean confirmationToPlay() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Playing Confirmation");
        alert.setHeaderText("Playing Confirmation");
        alert.setContentText("Do you want to play?");
        ButtonType buttonTypeAccept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("yes");
            return true;
        } else {
            System.out.println("no");
            return false;
        }
    }

    public void showBoardForOpponent(String opp, String mainPlayer) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/GameBord.fxml"));
            Parent viewparent = loader.load();
            Scene viewscene = new Scene(viewparent);
            GameBordController controller = loader.getController();
            controller.setText(opp, mainPlayer, "x", "o", "online");
            Stage window = (Stage) listView.getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
