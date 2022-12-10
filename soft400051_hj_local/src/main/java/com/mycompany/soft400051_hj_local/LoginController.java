package com.mycompany.soft400051_hj_local;

import java.io.IOException;
import java.util.Vector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    
    protected static Stage stage;
    protected static Stage stage2;
    
    @FXML
    Button login;
    
    //for Register details
    @FXML
    protected TextField reg_username,reg_email;
    @FXML
    protected PasswordField reg_password;
    
    String regexemail1 = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    String regexemail2 = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    String regexpassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
    
    public void login_details_check(String Name, String Password, Node node) throws Exception
    {        
        int loginflag =0;
        System.out.println("User Input Empty check for login " +Name+ " "+Password);
        if((Name.isEmpty() || Name.contains(" ")) || Password.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
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
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText(Name+ " Logged In");
                        a.showAndWait();
                        System.out.println("Node ..... " +node);
                        //FXMLLoader loader = new FXMLLoader();
                        //Parent root = FXMLLoader.load(getClass().getResource("./logged_user.fxml"));
                        
                        //System.out.println("Loaded Root");
                        //stage = (Stage) node.getScene().getWindow();
                        //System.out.println("Window Loaded");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("logged_user.fxml"));
                        Parent root = loader.load();
                        UserdataController controller = loader.getController();
                        controller.setLabelText("Welcome "+Name);
                        //stage.setScene(new Scene(root));
                        //stage.show();
                        
                        stage2 = new Stage();
                        stage2.setScene(new Scene(root));
                        stage2.show();
                    }
                    else if(user_log_status.contains("already logged in")){
                        System.out.println("User Already Logged in  " +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText(Name+ " is already Logged In");
                        a.show();
                    }
                    else{
                        System.out.println("No Such User Found" +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("No Details Found");
                        a.show();
                    }
                }
                else{
                    System.out.println("Password Format Invalid");
                    Alert a = new Alert(Alert.AlertType.ERROR);
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
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }
    
    }

    public void register_details(String Name, String Password, String Email) throws Exception
    {
        int flagcheck = 0;
        if((Name.isEmpty() || Name.contains(" ")) || Email.isEmpty() || Password.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
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
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
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
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText("User "+Name+ " : "+Email+ "\n Registration Successfully \n" + "Go Back to Login page");
                            a.show();
                        }
                        else{
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText("User "+Name+ " : "+Email+ "\n Registration UnSuccessfully \n" + " Please Contact Support");
                            a.show();            
                        }
                    }
                }
                else{
                    System.out.println("Password Format Invalid");
                    Alert a = new Alert(Alert.AlertType.ERROR);
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
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }
    }

    public void delete_details(String Name, String Password)throws Exception
    {
        int deleteflag =0;
        System.out.println("User Input Empty check for login " +Name+ " "+Password);
        if((Name.isEmpty() || Name.contains(" ")) || Password.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            String fieldempty = ((Name.isEmpty() && !Name.contains(" ")) == Password.isEmpty())? "All Field are Empty":((Name.isEmpty() || Name.contains(" "))== true) ? "Name is Empty or Contains Space":"Password is Empty";
            a.setContentText(fieldempty);
            a.show();    
            deleteflag =1;
        }
        else{
            System.out.println("Field is Not Empty");
        }
        if(deleteflag == 0){
            if(Name.matches(regexemail1) && Name.matches(regexemail2)){
                if(Password.matches(regexpassword))
                {
                    //byte[] salt = dbconnection.getSalt();
                    //String password_hashed = dbconnection.getSecurePassword(Password, salt);
                    String password_hashed = dbconnection.toHexString(dbconnection.getSHA(Password));
                    System.out.println("Password Encrpted"+ password_hashed);
                    String user_log_status = dbconnection.data_del(Name, password_hashed,0);
                    if(user_log_status.contains("true")){
                        System.out.println("User Matched Found " +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setContentText(Name+ " Delete User");
                        a.showAndWait();
                        ButtonType t = a.getResult();
                        System.out.println("User Selected :" +t);
                        if("OK".equals(t.getText())){
                            String delete_log_status = dbconnection.data_del(Name, password_hashed,1);
                            System.out.println("User Deleted");
                        }
                        else{
                            System.out.println("User Cancelled Delete");
                        }
                    }
                    else{
                        System.out.println("No Such User Found " +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("No Details Found");
                        a.show();
                    }
                }
                else{
                    System.out.println("Password Format Invalid");
                    Alert a = new Alert(Alert.AlertType.ERROR);
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
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }
    }
    
    public void user_logout(String Name) throws IOException{
        String logout_status = dbconnection.user_logout(Name);
        if(logout_status.contains("true")){
            System.out.println("User Logged Out Successfully " +Name);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(Name+ " Logged Out");
            a.showAndWait();
            stage2.close();
        }
        else if(logout_status.contains("already logged in")){
            System.out.println("User Already Logged in  " +Name);
            //System.out.println("User Input " +Name+ " "+Password);    
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(Name+ " is already Logged In");
            a.show();
        }
        else{
            System.out.println("Error Logging-Out Contact Admin  " +Name);
            //System.out.println("User Input " +Name+ " "+Password);    
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(Name+ " is already Logged In");
            a.show();
        }
    }
    
}
