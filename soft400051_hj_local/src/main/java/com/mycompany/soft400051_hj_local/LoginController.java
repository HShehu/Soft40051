package com.mycompany.soft400051_hj_local;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
* @brief Creating a class LoginController to control Function of button
* @brief All function are performed in class LoginController based on flow from ButtonController
* @brief Loading Fields for Register and Flags  
*/

public class LoginController {
    
    protected static Stage stage;
    protected static Stage stage2;
        
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
        Logger_Controller.log_info("Function login_details_check Started");
        int loginflag =0;
        System.out.println("Check if User Input is Empty for " +Name);
        if((Name.isEmpty() || Name.contains(" ")) || Password.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            String fieldempty = ((Name.isEmpty() && !Name.contains(" ")) == Password.isEmpty())? "All Field are Empty":((Name.isEmpty() || Name.contains(" "))== true) ? "Name is Empty or Contains Space":"Password is Empty";
            a.setContentText(fieldempty);
            a.show();
            Logger_Controller.log_info("Login Screen Fields are Empty");
            Logger_Controller.log_info(fieldempty);
            loginflag =1;
        }
        else{
            Logger_Controller.log_info("Login Field Check 1 Success");
            System.out.println("Login Field is Not Empty");
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
                        Logger_Controller.log_info("User Logged in Successfully "+Name);
                        System.out.println("User Logged In Successfully " +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText(Name+ " Logged In");
                        a.showAndWait();
                        //System.out.println("Node ..... " +node);
                        //FXMLLoader loader = new FXMLLoader(getClass().getResource("logged_user.fxml"));
                        UserProfileController controller = new UserProfileController(Name);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("userProfile.fxml"));
                        loader.setController(controller);
                        Parent root = loader.load();
//                       UserdataController controller = loader.getController();
                        
                        stage2 = new Stage();
                        stage2.setScene(new Scene(root));
                        stage2.show();
                        Logger_Controller.log_info("Login Screen Loaded to User ");
                    }
                    else if(user_log_status.contains("already logged in")){
                        Logger_Controller.log_info("User Already Logged in "+Name);
                        System.out.println("User Already Logged in  " +Name);
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText(Name+ " is already Logged In");
                        a.show();
                    }
                    else{
                        Logger_Controller.log_info("No Account Details Found "+Name);
                        System.out.println("No Such User Found" +Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("No Details Found");
                        a.show();
                    }
                }
                else{
                    Logger_Controller.log_info("Password Format Invalid "+Name);
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
                Logger_Controller.log_info("Invalid Email Format "+Name);
                System.out.println("EmailID Format Invalid");
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }
    }

    public void register_details(String Name, String Password, String Email) throws Exception
    {
        Logger_Controller.log_info("Function register_details Started");
        int flagcheck = 0;
        if((Name.isEmpty() || Name.contains(" ")) || Email.isEmpty() || Password.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            String fieldempty = (Name.isEmpty() == Email.isEmpty()) && (Name.isEmpty() == Password.isEmpty())? "All Field are Empty":(Email.isEmpty() == Password.isEmpty() && Name.isEmpty() == false) ? "EMail and Password Field is Empty":(Name.isEmpty()== true) ? "Name is Empty":(Email.isEmpty() == true)? "Email is Empty" : (Password.isEmpty()== true) ? "Password is Empty":"Validation Complete";
            a.setContentText(fieldempty);
            a.show();
            Logger_Controller.log_info("Register Screen Field Empty");
            Logger_Controller.log_info(fieldempty);
            flagcheck = 1;
        }
        else{
            System.out.println("Field is Not Empty");
            Logger_Controller.log_info("Register Screen check 1 Success");
        }
        //flag to check if above loop has failed do not process next loop
        if(flagcheck == 0){
            if(Email.matches(regexemail1) && Email.matches(regexemail2)){
                if(Password.matches(regexpassword)){
                    //System.out.println("User Input " +Name+" : "+Password+ " : "+Email);
                    boolean flag_data_exist = dbconnection.data_exist(Name, Email);
                    if(flag_data_exist == true){
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("User "+Name+ " : "+Email+ "\n Already Exist!! \n" + "Go Back to Login page");
                        a.show();
                        Logger_Controller.log_info("User Already Exist "+Name);
                    }
                    else{
                        //byte[] salt = dbconnection.getSalt();
                        //String password_hashed = dbconnection.getSecurePassword(Password, salt);
                        String password_hashed = dbconnection.toHexString(dbconnection.getSHA(Password));
                        System.out.println("Password Hashed - Register");
                        Logger_Controller.log_info("Register Password Hash Success");
                        boolean flag = dbconnection.data_insert(Name,password_hashed,Email);
                        if(flag == true){
                            System.out.println("User Registered Successfully " +Name+" : "+Email);
                            Logger_Controller.log_info("User Registered Successfully "+Email);
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText("User "+Name+ " : "+Email+ "\n Registration Successfully \n" + "Go Back to Login page");
                            a.show();
                        }
                        else{
                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setContentText("User "+Name+ " : "+Email+ "\n Registration UnSuccessfull \n" + " Please Contact Support");
                            a.show();
                            Logger_Controller.log_info("Registration UnSuccessfull" +Email);
                        }
                    }
                }
                else{
                    System.out.println("Password Format Invalid");
                    Logger_Controller.log_info("Password Format Invalid "+Email);
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
                Logger_Controller.log_info("EmailID Format Invalid "+Email);
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }
    }

    public void delete_details(String Name, String Password)throws Exception
    {
        Logger_Controller.log_info("Function delete_details Started");
        int deleteflag =0;
        System.out.println("User Input Empty check for delete " +Name);
        if((Name.isEmpty() || Name.contains(" ")) || Password.isEmpty()){
            Alert a = new Alert(Alert.AlertType.ERROR);
            String fieldempty = ((Name.isEmpty() && !Name.contains(" ")) == Password.isEmpty())? "All Field are Empty":((Name.isEmpty() || Name.contains(" "))== true) ? "Name is Empty or Contains Space":"Password is Empty";
            a.setContentText(fieldempty);
            a.show();    
            deleteflag =1;
            Logger_Controller.log_info("Delete Screen Fields Empty");
            Logger_Controller.log_info(fieldempty);
        }
        else{
            System.out.println("Field is Not Empty");
            Logger_Controller.log_info("Delete Screen check 1 Success");
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
                        Logger_Controller.log_info("User Found - Delete "+Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setContentText(Name+ " Delete User");
                        a.showAndWait();
                        ButtonType t = a.getResult();
                        System.out.println("User Selected :" +t);
                        if("OK".equals(t.getText())){
                            String delete_log_status = dbconnection.data_del(Name, password_hashed,1);
                            System.out.println("User Deleted");
                            Logger_Controller.log_info("Account Deleted "+Name);
                        }
                        else{
                            System.out.println("User Cancelled Delete");
                            Logger_Controller.log_info("User Clicked Cancel on Confirmation "+Name);
                        }
                    }
                    else{
                        System.out.println("No Such User Found " +Name);
                        Logger_Controller.log_info("No Account Found "+Name);
                        //System.out.println("User Input " +Name+ " "+Password);    
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setContentText("No Details Found");
                        a.show();
                    }
                }
                else{
                    System.out.println("Password Format Invalid");
                    Logger_Controller.log_info("Password Format Invalid");
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
                Logger_Controller.log_info("EmailID Format Invalid");
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Invalid Email Format");
                a.show();
            }
        }
    }
    
    public void user_logout(String Name) throws IOException{
        Logger_Controller.log_info("Function user_logout Started");
        String logout_status = dbconnection.user_logout(Name);
        if(logout_status.contains("true")){
            System.out.println("User Logged Out Successfully " +Name);
            Logger_Controller.log_info("User Logged Out Success "+Name);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(Name+ " Logged Out");
            a.showAndWait();
            stage2.close();
        }
        else if(logout_status.contains("already logged in")){
            System.out.println("User Already Logged in  " +Name);
            Logger_Controller.log_info("User Already Logged in "+Name);
            //System.out.println("User Input " +Name+ " "+Password);    
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(Name+ " is already Logged In");
            a.show();
        }
        else{
            System.out.println("Error Logging-Out Contact Admin  " +Name);
            Logger_Controller.log_info("Error Logging-out Contact Admin "+Name);
            //System.out.println("User Input " +Name+ " "+Password);    
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(Name+ " is already Logged In(Error)");
            a.show();
        }
    }    
}
