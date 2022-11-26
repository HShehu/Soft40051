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
import javafx.scene.control.Alert.AlertType;
import static com.mycompany.soft400051_hj_local.App.scene_login;
import static java.lang.System.exit;
import javafx.scene.control.Alert;

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
    Button Login,Register,login,register;
    //private Object loader;
    
    //for Login details
    @FXML
    public TextField log_username;
    @FXML
    public PasswordField log_password;
    
    //for Register details
    @FXML
    public TextField reg_username,reg_email;
    @FXML
    public PasswordField reg_password;
    
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
    public void login() throws Exception
    {
        String Name = log_username.getText();
        String Password = log_password.getText();
        String regexemail1 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        String regexemail2 = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String regexpassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        int loginflag =0;
        System.out.println("User Input Empty check for login " +Name+ " "+Password);
        if((Name.isEmpty() || Name.contains(" ")) || Password.isEmpty()){
            Alert a = new Alert(AlertType.ERROR);
            String fieldempty = ((Name.isEmpty() && !Name.contains(" ")) == Password.isEmpty())? "All Field are Empty":((Name.isEmpty() || Name.contains(" "))== true) ? "Name is Empty or Contains Space":"Password is Empty";
            a.setContentText(fieldempty);
            a.show();    
            loginflag =1;
        }
        else{
            System.out.println("Field is Not Empty");
        }
        if(loginflag == 0){
            if(Name.matches(regexemail1) && Name.matches(regexemail2)){
                if(Password.matches(regexpassword))
                {
                    //byte[] salt = dbconnection.getSalt();
                    //String password_hashed = dbconnection.getSecurePassword(Password, salt);
                    String password_hashed = dbconnection.toHexString(dbconnection.getSHA(Password));
                    System.out.println("Password Encrpted"+ password_hashed);
                    String user_log_status = dbconnection.data_login(Name, password_hashed);
                    if(user_log_status.contains("true")){
                        System.out.println("User Logged In Successfully " +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText(Name+ " Logged In");
                        a.show();
                    }
                    else if(user_log_status.contains("already logged in")){
                        System.out.println("User Already Logged in  " +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText(Name+ " is already Logged In");
                        a.show();
                    }
                    else{
                        System.out.println("No Such User Found" +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("No Details Found");
                        a.show();
                    }
                }
                else{
                    System.out.println("Password Format Invalid");
                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Password Invalid.\n"
                            + "It contains at least 8 characters and at most 20 characters.\n" 
                            + "It contains at least one digit.\n" 
                            + "It contains at least one upper case alphabet.\n" 
                            + "It contains at least one lower case alphabet.\n" 
                            + "It contains at least one special character which includes !@#$%&*()-+=^.\n" 
                            + "It doesn’t contain any white space." );
                    a.show();
                }
            }
            else{
                System.out.println("EmailID Format Invalid");
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }   
    }
    
    @FXML
    public void register() throws Exception
    {
        String Name = reg_username.getText();
        String Email = reg_email.getText();
        String Password = reg_password.getText();
        String regexemail1 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        String regexemail2 = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String regexpassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        int flagcheck = 0;
        if((Name.isEmpty() || Name.contains(" ")) || Email.isEmpty() || Password.isEmpty()){
            Alert a = new Alert(AlertType.ERROR);
            String fieldempty = (Name.isEmpty() == Email.isEmpty()) && (Name.isEmpty() == Password.isEmpty())? "All Field are Empty":(Email.isEmpty() == Password.isEmpty() && Name.isEmpty() == false) ? "EMail and Password Field is Empty":(Name.isEmpty()== true) ? "Name is Empty":(Email.isEmpty() == true)? "Email is Empty" : (Password.isEmpty()== true) ? "Password is Empty":"Validation Complete";
            a.setContentText(fieldempty);
            a.show();
            flagcheck = 1;
        }
        else{
            System.out.println("Field is Not Empty");
        }
        //flag to check if above loop has failed do not process next loop
        if(flagcheck == 0){
            if(Email.matches(regexemail1) && Email.matches(regexemail2)){
                if(Password.matches(regexpassword)){
                    System.out.println("User Input " +Name+" : "+Password+ " : "+Email);
                    boolean flag_data_exist = dbconnection.data_exist(Name, Email);
                    if(flag_data_exist == true){
                        Alert a = new Alert(AlertType.INFORMATION);
                        a.setContentText("User "+Name+ " : "+Email+ "Already Exist!! \n" + "Go Back to Login page");
                        a.show();
                    }
                    else{
                        //byte[] salt = dbconnection.getSalt();
                        //String password_hashed = dbconnection.getSecurePassword(Password, salt);
                        String password_hashed = dbconnection.toHexString(dbconnection.getSHA(Password));
                        System.out.println("Password Hashed - Register" +password_hashed);
                        boolean flag = dbconnection.data_insert(Name,password_hashed,Email);
                        if(flag == true){
                            System.out.println("User Registered Successfully " +Name+" : "+Email);
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("User "+Name+ " : "+Email+ "Registration Successfully \n" + "Go Back to Login page");
                            a.show();
                        }
                        else{
                            Alert a = new Alert(AlertType.INFORMATION);
                            a.setContentText("User "+Name+ " : "+Email+ "Registration UnSuccessfully \n" + " Please Contact Support");
                            a.show();
                        }
                    }
                }
                else{
                    System.out.println("Password Format Invalid");
                    Alert a = new Alert(AlertType.ERROR);
                    a.setContentText("Password Invalid.\n"
                            + "It contains at least 8 characters and at most 20 characters.\n" 
                            + "It contains at least one digit.\n" 
                            + "It contains at least one upper case alphabet.\n" 
                            + "It contains at least one lower case alphabet.\n" 
                            + "It contains at least one special character which includes !@#$%&*()-+=^.\n" 
                            + "It doesn’t contain any white space." );
                    a.show();
                }
            }
            else{
                System.out.println("EmailID Format Invalid");
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }
    }
        
}
