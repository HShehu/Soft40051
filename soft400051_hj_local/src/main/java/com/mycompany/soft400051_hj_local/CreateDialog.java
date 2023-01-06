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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*! \brief This Class controls the fxml Page for Creating a new File.
 *        
 */

public class CreateDialog implements Initializable{
    
    @FXML
    private Button btnCancel; //!< Cancel Button in createDialogfxml

    @FXML
    private TextArea tafileContent; //!< Text Area for File Content in createDialogfxml

    @FXML
    private TextField tfFileName; //!< Text Field for File Name in createDialogfxml

    @FXML
    private Label lbFileContent;

    @FXML
    private Label lbFileName;

    @FXML
    private Button btnSave; //!< Save Button in createDialogfxml
    
    private final UserProfileController parentController; //!< Parent Controller opening the createDialogfxml
    
/*! \brief The Create Dialog Controller recieves the instance of the UserProfileController as parent.
                             It does this to to send the information back to the exact instance of the controller.
 *  @param controller UserProfileController argument   
 */
    CreateDialog(UserProfileController controller)
    {
        this.parentController = controller;
    }
    
/*! \brief On Initialize set the events for the Cancel and Save Buttons.
 *The Save button checks to ensure all fields are not blank before closing the stage.
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
            String[] newFile = {tfFileName.getText().trim(),tafileContent.getText()};
            String message = null;
            
            if(newFile[0].isBlank() && newFile[1].isBlank())
            {
                message = "All fields Are Blank\n Please Input File Name and Content";
              
            }
            if(newFile[0].isBlank() && !newFile[1].isBlank())
            {
                message = "File Name cannot be empty\n Please Input File Name";
                
            }
            if(!newFile[0].isBlank() && newFile[1].isBlank())
            {
                message = "File Content cannot be empty\n Please Write Something";
                
            }
            if(newFile[0].contains(" "))
            {
                message = "File Name cannot have spaces";
                
            }
            
            if(message != null)
            {
                Alert btnAlert = new Alert(Alert.AlertType.ERROR);
                btnAlert.contentTextProperty().setValue(message);
                btnAlert.show();
                return;
            }
            
            parentController.ReceiveCreateFileContent(newFile);
            stage.close();
        });
    }  
}
