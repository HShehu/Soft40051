/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.UserFolder;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


/*! \brief This Class controls the fxml Page for Moving a File.
 *      \author Joshua Miner 
 */

public class MoveDialog implements Initializable {
    
    @FXML
    private Button btnCancel; //!< Cancel Button in moveDialogfxml

    @FXML
    private Label lblMoveTo;

    @FXML
    private ChoiceBox<UserFolder> cbFolderList;  //!< List of Folders

    @FXML
    private Button btnConfirm;  //!< Confirm Button in moveDialogfxml
    
    private final UserProfileController parentController; //!< Parent Controller opening the moveDialogfxml
  
/*! \brief The Move Dialog Controller recieves the instance of the UserProfileController as parent.
                             It does this to to send the information back to the exact instance of the controller.
 *  @param controller UserProfileController argument   
 */
    MoveDialog(UserProfileController controller)
    {
        this.parentController = controller;
        
    }

/*! \brief On Initialize set the events for the Cancel and Save Buttons.
 *The Confirm button checks to ensure destination is not blank before closing the stage.
 *The Cancel button only closes the stage
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
            if(Objects.isNull(cbFolderList.getSelectionModel().getSelectedItem()))
            {
                Alert btnAlert = new Alert(Alert.AlertType.ERROR);
                btnAlert.contentTextProperty().setValue("You have to Select a Folder");
                btnAlert.show();          
                return;
            }
            parentController.RecieveMoveFolder(cbFolderList.getSelectionModel().getSelectedItem());
            stage.close();
        });
    }

    /*! \brief This sets the ChoiceBox of Folders
                             
 *  @param folders List of UserFolder   
 */
public void SetCBFolder(List<UserFolder> folders)
{   
    cbFolderList.setItems(FXCollections.observableArrayList(folders));
}
    
}
