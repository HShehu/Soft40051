/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.UserFolder;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class FolderController{
    
    private UserFolder folder;

    @FXML
    private Label lbFolderName;
    

    FolderController(UserFolder folder){
        this.folder = folder;
        lbFolderName.setText(folder.getName());
    }

//    public void setData(UserFolder folder)
//    {
//        
//    }

}
