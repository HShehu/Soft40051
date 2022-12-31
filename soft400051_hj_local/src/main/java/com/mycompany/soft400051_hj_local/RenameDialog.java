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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */


public class RenameDialog implements Initializable{
    
    @FXML
    private  Button btnCancel;

    @FXML
    private  Button btnSave;

    @FXML
    private  TextField tfRename;

    @FXML
    private  Label lblRename;
    
    private final UserProfileController parentController;
    
    RenameDialog(UserProfileController controller)
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
            parentController.ReceiveRename(tfRename.getText());
            stage.close();
        });
    }
    
   
    
    

}