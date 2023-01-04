/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.UserFolder;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class FolderController implements Initializable{
    
    private UserFolder folder;
    private final UserProfileController parent;

    @FXML
    private Label lbFolderName;
    

    FolderController(UserFolder folder,UserProfileController parentController){
        this.folder = folder;
        this.parent = parentController;
       
    }

    public void Clicked()
    {
        parent.setCurDir(folder.getName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         lbFolderName.setText(folder.getName());
    }

}
