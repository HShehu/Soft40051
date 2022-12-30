/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local;
import javafx.geometry.Insets;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
 
 
public class CreateDialog {
     
     static String name, content;
 
    public static String[] display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
         
        TextField text1 = new TextField();
        TextArea taContent = new TextArea();
         
        Button button = new Button("Submit");
        button.setOnAction(e -> {
             name = text1.getText();
             content = taContent.getText();
             stage.close();
        });
     
        Label label1 = new Label(" Create New FIle ");
        Label label2 = new Label("File Name:");
        Label label3 = new Label("File Content:");
         
        GridPane layout = new GridPane();
         
        layout.setPadding(new Insets(10, 10, 10, 10)); 
        layout.setVgap(5); 
        layout.setHgap(5); 
         
        layout.add(text1, 1,1);
        layout.add(taContent, 1,2);
        layout.add(button, 1,3);
        layout.add(label1, 1,0);
        layout.add(label2, 0,1);
        layout.add(label3, 0,2);
         
        Scene scene = new Scene(layout, 250, 150);          
        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.showAndWait();
        
        return new String[]{name,content};
    }
}
