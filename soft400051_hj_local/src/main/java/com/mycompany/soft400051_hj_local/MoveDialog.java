/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.UserFolder;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class MoveDialog implements Initializable {
    
    @FXML
    private Button btnCancel;

    @FXML
    private Label lblMoveTo;

    @FXML
    private ChoiceBox<UserFolder> cbFolderList;

    @FXML
    private Button btnConfirm;
    
    private final UserProfileController parentController;
    
    MoveDialog(UserProfileController controller)
    {
        this.parentController = controller;
        
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnCancel.setOnAction(e->{
            Node  source = (Node)  e.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        });

        btnConfirm.setOnAction(e->{
            Node  source = (Node)  e.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            parentController.RecieveMoveFolder(cbFolderList.getSelectionModel().getSelectedItem());
            stage.close();
        });
    }

public void SetCBFolder(List<UserFolder> folders)
{   
    cbFolderList.setItems(FXCollections.observableArrayList(folders));
}
    
}
