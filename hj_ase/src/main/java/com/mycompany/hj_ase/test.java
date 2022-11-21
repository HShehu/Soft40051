/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hj_ase;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
/**
 *
 * @author ntu-user
 */
public class test extends Application {
    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();
        
        var root = new GridPane();
        root.setVgap(20);
        root.setHgap(15);
        root.setPadding(new Insets(20));
        
        var label = new Label("Hello, JavaFX ");
        var username = new Label("Name");
        var username_field = new TextField();
        
        var password = new Label("Password");
        var password_field = new TextField();
        
        username.setLabelFor(username_field);
        username.setMnemonicParsing(true);
        
        password.setLabelFor(password_field);
        password.setMnemonicParsing(true);
             
        root.add(label,1,0);
        root.add(username, 0, 1);
        root.add(username_field, 2, 1);
        root.add(password,0,2);
        root.add(password_field,2,2);
        
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setHalignment(username, HPos.RIGHT);
        GridPane.setHalignment(password, HPos.RIGHT);
        var scene = new Scene(root);
        //var scene = new Scene(new StackPane(label), 640, 480);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}
