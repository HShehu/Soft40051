/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;



/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class FileController {
    
    @FXML
    private Label lbFileName;

    @FXML
    private ImageView imgFile;


 
    public void setData(String fileName) {
        lbFileName.setText(fileName);
    }

    public String viewData() {
        return this.lbFileName.getText();
    }


}
