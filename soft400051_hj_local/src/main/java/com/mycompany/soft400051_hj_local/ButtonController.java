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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import static com.mycompany.soft400051_hj_local.App.scene_login;
import static java.lang.System.exit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class ButtonController implements Initializable {

    /**
     * Initializes the controller class.
     */
    protected static Stage stage;
    protected static Scene scene2;
    
    @FXML
    Button Login,Register;
    //private Object loader;
    
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
    
   @FXML 
   public void handleregister() throws Exception
   {
       System.out.println("Regsiter Page Loading...");
       Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
       stage = (Stage) Register.getScene().getWindow();
       stage.setScene(new Scene(root));
   }
   
   @FXML
   public void handlelogin() throws Exception
   {
       System.out.println("Login Page Loading...");
       Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
       stage = (Stage) Login.getScene().getWindow();
       stage.setScene(new Scene(root));       
   }
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        String title = "Header";
        FXMLLoader loader = new FXMLLoader();
        
        if(event.getSource()==Login){
            System.out.println("--------Inside IF Login---------");
            stage = (Stage) Login.getScene().getWindow();   
            System.out.println("-------Inside IF Try Login--------");
            //System.out.println("--------------------- "+loader.getLocation());
            //System.out.println("--------------------- "+loader.getClass());
            //System.out.println("--------------------- "+loader.getClassLoader());
            root = FXMLLoader.load(getClass().getResource("./test.fxml"));
            //loader.setLocation(ButtonController.class.getResource("./test.fxml"));
            title = "Login";
            //root = FXMLLoader.load(getClass().getResource("test.fxml"));
            //root = loader.load();
        }
        else{
            System.out.println(event.getSource());
            System.out.println(event.getTarget());
            System.out.println(event.getEventType());
            System.out.println(event.getClass());
            System.out.println("-------Inside ELSE Register--------");
            stage = (Stage) Register.getScene().getWindow();
            System.out.println("-------Inside ELSE Try Register--------");
            //System.out.println("--------------------- "+loader.getLocation());
            //System.out.println("--------------------- "+loader.getClass());
            //System.out.println("--------------------- "+loader.getClassLoader());
            root = FXMLLoader.load(getClass().getResource("./register.fxml"));
            //loader.setLocation(ButtonController.class.getResource("./register.fxml"));
            title = "Register";
            //root = FXMLLoader.load(getClass().getClassLoader().getResource("./register.fxml"));
            //root = loader.load();
        }
        //root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void nextScreen(ActionEvent event) throws Exception
    {
        System.out.println("Next Page Loading...");
        Node node = (Node)event.getSource();
        System.out.println("Node ..... " +node);
        stage = (Stage) node.getScene().getWindow();
        
        Parent root = FXMLLoader.load(getClass().getResource("./register.fxml"));
        scene2 = new Scene(root);
        stage.setTitle("Register");
        stage.setScene(scene2);
        //stage.show();
    }
    
    @FXML
    private void onclickgoback(ActionEvent event) throws Exception
    {
        System.out.println("Going Back to Login Page...");
        //window.setTitle("Login");
        stage.setScene(scene_login);
        //window.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void login(ActionEvent event) 
    {
        //Logger log = Logger_Controller.log_createfile();
        //log.info("Logging Button Clicked");
        Logger_Controller.log_info("Logging Button Clicked");
        Node node = (Node)event.getSource();
        System.out.println("Node ..... " +node);
        String Name = log_username.getText();
        String Password = log_password.getText();
        try{
            Logger_Controller.log_info("Logging Function Execution to Start");
            //log.info("Logging Function Execution to Start");
            LoginController login_detail = new LoginController();
            login_detail.login_details_check(Name,Password,node);
            Logger_Controller.log_info("Logging Function Execution Success");
            //log.info("Logging Function Execution Success");
        }
        catch(Exception e){
            System.out.println("Exception In login_details_check function in Login Controller");
        }
    }
    
    @FXML
    public void register()
    {
        String Name = reg_username.getText();
        String Email = reg_email.getText();
        String Password = reg_password.getText();
        
        try{
            LoginController register_detail = new LoginController();
            register_detail.register_details(Name,Password,Email);
            reg_username.clear();
            reg_email.clear();
            reg_password.clear();
        }
        catch(Exception e){
            System.out.println("Exception In register_details function in Login Controller");
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @FXML
    public void deleteuser(){
        String Name = del_username.getText();
        String Password = del_password.getText();
        try{
            LoginController delete_detail = new LoginController();
            delete_detail.delete_details(Name,Password);
            del_username.clear();
            del_password.clear();

        }
        catch(Exception e){
            System.out.println("Exception In delete_detail function in Login Controller");
        }               
    }
        
}
