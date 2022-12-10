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
* @brief create a class App and launch Application
* @brief using Button.fxml file to get the Login, Register and Delete Scene
*/

public class App extends Application {
   
    public static void main(String[] args) {
        launch();
    }
    
    protected static Scene scene_login;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Date date = Calendar.getInstance().getTime();          
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");  
        String strDate = dateFormat.format(date);  
        System.out.println(strDate);

        Parent root = FXMLLoader.load(getClass().getResource("Button.fxml"));
        scene_login = new Scene(root);
        stage.setTitle("Welcome!");
        stage.setResizable(false);
        stage.setScene(scene_login);
        stage.show();
    }
}