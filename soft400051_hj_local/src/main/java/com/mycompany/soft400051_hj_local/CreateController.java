/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class CreateController implements Initializable {


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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void cancelBtnClicked(){
        
    }
    
    
}
