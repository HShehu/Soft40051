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

/*! \brief This Class controls the Folderfxml.
 *  \author Joshua Miner   
 */
public class FolderController implements Initializable{
    
    private final UserFolder folder;   //!< UserFolder Object
    private final UserProfileController parent; //!< UserProfileController Object

    @FXML
    private Label lbFolderName;
    
/*
 * @param folder a UserFolder argument
 * @param parent a UserProfileController argument It does this to to send the information back to the exact instance of the controller.
 */
    FolderController(UserFolder folder,UserProfileController parentController){
        this.folder = folder;
        this.parent = parentController;
       
    }
    
/*! \brief On Click of VBox change the User Current Directory
 *  Takes the folder name and set Current Directory ot it      
 */
    public void Clicked()
    {
        parent.setCurDir(folder.getName());
    }

/*! \brief On Click of VBox change the User Current Directory
 *  Takes the folder name and set Current Directory ot it      
 */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         lbFolderName.setText(folder.getName());
    }

}
