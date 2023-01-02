/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CreateDialog implements Initializable{
    
    @FXML
    private Button btnCancel;

    @FXML
    private TextArea tafileContent;

    @FXML
    private TextField tfFileName;

    @FXML
    private Label lbFileContent;

    @FXML
    private Label lbFileName;

    @FXML
    private Button btnSave;
    
    private final UserProfileController parentController;
    
    CreateDialog(UserProfileController controller)
    {
        this.parentController = controller;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        btnCancel.setOnAction(e->{
            Node  source = (Node)  e.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        });

        btnSave.setOnAction(e->{
            Node  source = (Node)  e.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            String[] newFile = {tfFileName.getText(),tafileContent.getText()};
            parentController.ReceiveCreateFileContent(newFile);
            stage.close();
        });
    }  
}
