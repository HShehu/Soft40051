/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author harsh
 */

/**
* @brief Creating a class ButtonController to control clicks on button
* @brief All function are performed in class LoginController
* @brief Loading Fields for Login, Register, Delete 
*/

public class ButtonController implements Initializable {

    protected static Stage stage;
    protected static Scene scene2;
    
    //for Login details
    @FXML
    protected TextField log_username;
    @FXML
    protected PasswordField log_password;
    
    //for Register details
    @FXML
    protected TextField reg_username,reg_email;
    @FXML
    protected PasswordField reg_password;
    
    //for Delete details
    @FXML
    protected TextField del_username;
    @FXML
    protected PasswordField del_password;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    // TODO
    }    
    
    //ON Click Button - Login
    @FXML
    public void login(ActionEvent event) 
    {
        Logger_Controller.log_info("Login Button Clicked");
        Node node = (Node)event.getSource();
//         Stage thisStage = (Stage) node.getScene().getWindow();
//         thisStage.hide();
        //System.out.println("Node ..... " +node);
        String Name = log_username.getText();
        String Password = log_password.getText();
        try{
            Logger_Controller.log_info("Login Function Execution to Start");
            LoginController login_detail = new LoginController();
            login_detail.login_details_check(Name,Password,node);
            Logger_Controller.log_info("Login Function Execution Success");
        }
        catch(Exception e){
            Logger_Controller.log_info("Exception in Login_Details_check function in Login Controller");
            System.out.println("Exception In login_details_check function in LoginController");
        }
    }
    
    //ON Click Button - Register
    @FXML
    public void register()
    {
        Logger_Controller.log_info("Register Button Clicked");
        String Name = reg_username.getText();
        String Email = reg_email.getText();
        String Password = reg_password.getText();
        
        try{
            Logger_Controller.log_info("Register Function Execution to Start");
            LoginController register_detail = new LoginController();
            register_detail.register_details(Name,Password,Email);
            Logger_Controller.log_info("Register Function Execution Success");
            reg_username.clear();
            reg_email.clear();
            reg_password.clear();
        }
        catch(Exception e){
            Logger_Controller.log_info("Exeception in register_details function in LoginController");
            System.out.println("Exception In register_details function in LoginController");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    //ON Click Button - Delete
    @FXML
    public void deleteuser(){
        Logger_Controller.log_info("Delete Button Clicked");
        String Name = del_username.getText();
        String Password = del_password.getText();
        try{
            Logger_Controller.log_info("Delete Function Execution to Start");
            LoginController delete_detail = new LoginController();
            delete_detail.delete_details(Name,Password);
            Logger_Controller.log_info("Delete Function Execution Success");
            del_username.clear();
            del_password.clear();
        }
        catch(Exception e){
            Logger_Controller.log_info("Exception in delete_detail function in LoginController");
            System.out.println("Exception In delete_detail function in LoginController");
        }               
    }
}
