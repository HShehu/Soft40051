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

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class FileController {
    
    private String filePath;
    
    
    @FXML
    private Label lbFileName;




 
    public void setData(String fileName) {
        lbFileName.setText(fileName);
    }

    public String viewData() {
        return this.lbFileName.getText();
    }



}
