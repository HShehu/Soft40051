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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*! \brief This Class controls the fxml Page for Renaming a File.
 *  \author Joshua Miner  
 */

public class RenameDialog implements Initializable{
    
    @FXML
    private  Button btnCancel; //!< Cancel Button in renameDialogfxml

    @FXML
    private  Button btnSave;  //!< Save Button in renameDialogfxml

    @FXML
    private  TextField tfRename; //!< Text Field for New Name in renameDialogfxml

    @FXML
    private  Label lblRename;
    
    private final UserProfileController parentController; //!< Parent Controller opening the renameDialogfxml
    
/*! \brief The Rename Dialog Controller recieves the instance of the UserProfileController as parent.
                             It does this to to send the information back to the exact instance of the controller.
 *  @param controller UserProfileController argument   
 */
    
    RenameDialog(UserProfileController controller)
    {
        this.parentController = controller;
    }
    
    
    
/*! \brief On Initialize set the events for the Cancel and Save Buttons.
 *The Save button checks to ensure rename is not blank before closing the stage.
 *The Cancel button only closes the stage
 */
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
            if(tfRename.getText().isBlank())
            {
                Alert btnAlert = new Alert(Alert.AlertType.ERROR);
                btnAlert.contentTextProperty().setValue("Name Cannot be Blank");
                btnAlert.show();
                return;
            }
            parentController.ReceiveRename(tfRename.getText());
            stage.close();
        });
    }  

}