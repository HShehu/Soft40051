/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author harsh
 */
public class ButtonController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button Login,Register;
    //private Object loader;
    
    @FXML
    private void handleButtonAction (ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        String title = "Header";
        FXMLLoader loader = new FXMLLoader();
        
        if(event.getSource()==Login){
            System.out.println("--------Inside IF Login---------");
            stage = (Stage) Login.getScene().getWindow();
            
            try{
                System.out.println("-------Inside IF Try Login--------");
                //System.out.println("--------------------- "+loader.getLocation());
                //System.out.println("--------------------- "+loader.getClass());
                //System.out.println("--------------------- "+loader.getClassLoader());
                loader.setLocation(ButtonController.class.getResource("./test.fxml"));
                System.out.println("--------------------- "+loader.getLocation());
                title = "Login";
                //root = FXMLLoader.load(getClass().getResource("test.fxml"));
                //root = loader.load();
            }
            catch(Exception e){
                System.out.println("---------Unable to get Test----------");
            }
        }
        else{
            System.out.println(event.getSource());
            System.out.println("-------Inside ELSE Register--------");
            stage = (Stage) Register.getScene().getWindow();
            
            try{
                System.out.println("-------Inside ELSE Try Register--------");
                //System.out.println("--------------------- "+loader.getLocation());
                //System.out.println("--------------------- "+loader.getClass());
                //System.out.println("--------------------- "+loader.getClassLoader());
                loader.setLocation(ButtonController.class.getResource("./register.fxml"));
                title = "Register";
                //System.out.println("--------------------- "+loader.getLocation());
                //root = FXMLLoader.load(getClass().getClassLoader().getResource("./register.fxml"));
                //System.out.println("--------------------- "+loader.getLocation());
                //root = loader.load();
            }
            catch(Exception e){
                System.out.println("---------Unable to get register----------");
            }
        }
        root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setTitle(title);
        stage.setScene(scene);
        
        stage.show();
        //stage.toBack();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
