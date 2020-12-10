/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class SignUPController implements Initializable {
    

    @FXML
    private TextField pass_text;
    @FXML
    private TextField email_text;
    @FXML
    private Button signUPP_btn;
    
    @FXML
    private TextField name_text;
    @FXML
    private PasswordField passconf_text;
    @FXML
    private TextField name_txt_msg;
    @FXML
    private TextField email_txt_msg;
    @FXML
    private TextField pass_txt_msg;
    @FXML
    private TextField passConf_txt_msg;

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private void btnCLICK(ActionEvent event) throws IOException {
          
          boolean fn = isValidUsername(name_text.getText()); 
          boolean fe = isValidEmail(email_text.getText()); 
          boolean fp = isaValidpassword(pass_text.getText());
          boolean fpc = false;
          if( pass_text.getText() != null && pass_text.getText().equals(passconf_text.getText()) ){ fpc =true;}
          //////////////////////////
          
          
        if (fpc && fp && fn && fe){
            
          String email =email_text.getText();
          String name =name_text.getText();
          String pass =pass_text.getText(); 
          String str  = "XYZ"+"#"+email+"#"+pass+"#"+name;
         // new connection_signUP(str );
          //
         
 //             
        
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("ENTER.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          ENTERController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
          }
          
            if(!fn)
            { 
              name_txt_msg.setText("Invalid Name");
            }
            else 
            {
              name_txt_msg.clear();
               if (!fe)
               {
                   email_txt_msg.setText("Invalid E-mail");
               }
               else 
               {
                   email_txt_msg.clear();
                   if (!fp)
                   {
                   
                       pass_txt_msg.setText("Invalid password");
                    }
                     else {
                       pass_txt_msg.clear();
                       if (!fpc )
                       {
                           passConf_txt_msg.setText("Not confirmed passowrd");
                       }
                       else 
                       {
                           passConf_txt_msg.setText("confirm passowrd");
                       }
                   }
                }
          
          }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
    }    

    @FXML
    private void back_click(ActionEvent event) throws IOException {
          System.out.println("you clicked me!");
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("signIN.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          SignINController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    
    
    
    public static boolean isValidUsername(String name) 
    { 
        String regex = "^[A-Za-z]\\w{2,29}$"; 
        Pattern p = Pattern.compile(regex); 
 
        if (name == null) { 
            return false; 
        }
        if (name.length()>=30) { 
            return false; 
        }
        Matcher m = p.matcher(name); 
  
        return m.matches(); 
    }
    public static boolean isValidEmail(String email) 
    { 
        String emailRegex ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                          
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 

   public static boolean isaValidpassword(String password) 
    { 
        if (!((password.length() >= 5) 
              && (password.length() <= 29))) { 
            return false; 
        } 
        if (password.contains(" ")) { 
            return false; 
        } 
        if ((password.contains("#"))) { 
            return false;  
        } 
  
        return true; 
    } 

  
}
