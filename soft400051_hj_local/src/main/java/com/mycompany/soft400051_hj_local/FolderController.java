/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class FolderController{
    
    @FXML
    private ImageView imgFolder;
            
    @FXML
    private Label lbFolderName;
    
    private String folderPath;
    private String folderName;

  
    
    public void setData(String path , String fName)
    {
        this.folderName = fName;
        this.folderPath = path.concat("/"+fName);
        lbFolderName.setText(fName);
    }
}
