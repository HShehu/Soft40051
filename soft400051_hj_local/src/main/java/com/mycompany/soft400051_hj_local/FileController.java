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
public class FileController {
    @FXML
    private ImageView imgFile;

   @FXML
   private Label lbFileName;
   
   private String fileName;
   
    /**
     * Initializes the controller class.
     */
   

    void setData(String fName) {
        this.fileName = fName;
        lbFileName.setText(fName);
    }
    
}
