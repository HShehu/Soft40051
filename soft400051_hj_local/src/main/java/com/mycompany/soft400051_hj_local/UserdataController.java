package com.mycompany.soft400051_hj_local;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class UserdataController {
    
        @FXML 
        private Label label_username;
        
        public void initialize(String name) {
            
        }  
        
        public void setLabelText(String text){
            label_username.setText(text);
        }
        
        public void logout()
        {   
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are You Sure Logout?");
            a.showAndWait();
            
            ButtonType t = a.getResult();
            System.out.println("User Selected :" +t);
            if("OK".equals(t.getText())){
                String Name = label_username.getText();
                System.out.println("Clicked Logout " +Name);
                String[] name = Name.split(" ");
                System.out.println("Name of User is " + name[1]);
                try{
                    LoginController logout_checker = new LoginController();
                    logout_checker.user_logout(name[1]);
                    System.out.println("User Logged Out");
                }
                catch(IOException e){
                    System.out.println("Error Logging Out");
                }
                
            }
            else{
                System.out.println("User Cancelled Logout");
            }
            
            
        }
        
}
