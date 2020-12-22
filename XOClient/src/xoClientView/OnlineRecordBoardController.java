/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoClientView;

import java.io.IOException;
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
import screen.FreeOnlinePlayersController;
import screen.GameBordController;
import screen.SignIN2Controller;

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
    @FXML
    private Button btnShow;
    String gameMoves = "1#X#5#O#4#X#2#O#7#X";
    //String gameMoves = "1#1#5#1#4#1#2#1#7#1";
    String[] parsedMoves;
    Thread recordThread;
    int i = 0;
    Button btn = new Button();
    boolean flag=true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void Back_btn(ActionEvent event) {
    }

    @FXML
    private void btnAction(ActionEvent event) {
        parser(gameMoves);

        for (i = 0; i < parsedMoves.length; i += 2) {
            if(parsedMoves[i + 1].equals("O")){
            flag=false;
            }
            else if(parsedMoves[i + 1].equals("X")){
            flag=true;
            }
            /*Platform.runLater(new Runnable() {
                @Override
                public void run() {
            
                
                btn = getBtn(Integer.parseInt(parsedMoves[i]));
                System.out.println("POS: " + parsedMoves[i]);
                System.out.println("SIGN: " + parsedMoves[i + 1]);
                
                
                btn.setText(parsedMoves[i + 1]);
                
              
                }*/

            //System.out.println(move);
            /*switch (Integer.parseInt(parsedMoves[i])) {
                case 1:
                    Btn1.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 2:
                    Btn2.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 3:
                    Btn3.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 4:
                    Btn4.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 5:
                    Btn5.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 6:
                    Btn6.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 7:
                    Btn7.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 8:
                    Btn8.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 9:
                    Btn9.setText(parsedMoves[i + 1]);
                    try {
                        Thread.sleep(500);
                        //System.out.println(parsedMoves.length);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;

            }

            //});
            if (i == 8) {
                break;
            }*/
        }
        
        new Thread(new Runnable() {
            public void run(){
           for(int i = 0; i<parsedMoves.length ;i+=2)
               { 
                 switch (Integer.parseInt(parsedMoves[i])) {
                            case 1:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn1.setText("X");
                                       }
                                     });
                            
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn1.setText("O");
                                       }
                                     });
                                break;
                            case 2:
                                if(flag)
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn2.setText("X");
                                       }
                                     });
                                else
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn2.setText("O");
                                       }
                                     });
                                break;
                            case 3:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn3.setText("X");
                                       }
                                     });
                                else
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn3.setText("O");
                                       }
                                     });
                                break;
                            case 4:
                                if(flag)
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn4.setText("X");
                                       }
                                     });
                                else
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn4.setText("O");
                                       }
                                     });
                                break;
                            case 5:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn5.setText("X");
                                       }
                                     });
                                else
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn5.setText("O");
                                       }
                                     });
                                break;
                            case 6:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn6.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn6.setText("O");
                                       }
                                     });
                                break;
                            case 7:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn7.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn7.setText("O");
                                       }
                                     });
                                break;                           
                            case 8:
                                   if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn8.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn8.setText("O");
                                       }
                                     });
                                break;
                            case 9:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn9.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn9.setText("O");
                                       }
                                     });
                                break;
                            default:
                                break;
                        }
      
                if(flag)
                   flag = false;
                else
                   flag = true;
                try {
                    Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
        }  
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
        for (String pars : parsedMoves) {
            System.out.println("PARSED: " + pars);
        }
    }

    private void hold() {

    }

}
