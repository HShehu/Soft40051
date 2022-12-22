package com.mycompany.soft400051_hj_local;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * JavaFX App
 */

/**
* @brief creating a class App and launch Application
* @brief Loading Button.fxml file to get the Screens
*/

public class App extends Application {
   
    public static void main(String[] args) {
        launch();
    }
    
    protected static Scene scene_login;

    static void setRoot(String secondary) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        Logger_Controller.log_info("Application Resource Loading...");
        
        Date date = Calendar.getInstance().getTime();          
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");  
        String strDate = dateFormat.format(date);  
        System.out.println("Application Started "+strDate);

        Parent root = FXMLLoader.load(getClass().getResource("userProfile.fxml"));
        scene_login = new Scene(root);
        stage.setTitle("Welcome!");
        stage.setResizable(false);
        stage.setScene(scene_login);
        stage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("Button.fxml"));
//        scene_login = new Scene(root);
//        stage.setTitle("Welcome!");
//        stage.setResizable(false);
//        stage.setScene(scene_login);
//        stage.show();
        
        Logger_Controller.log_info("Application Started...");
    }
}