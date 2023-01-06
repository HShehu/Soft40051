/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.UserFolder;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ntu-user
 */
public class ShareDialog implements Initializable {
    private UserProfileController parent;
    private final ToggleGroup radioGroup = new ToggleGroup();
    
    @FXML
    private Button btnCancel;

    @FXML
    private ChoiceBox<String> cbShareWith;

    @FXML
    private RadioButton rbReadOnly;

    @FXML
    private Button btnShare;

    @FXML
    private RadioButton rbReadWrite;

    
    ShareDialog(UserProfileController controller){
        this.parent = controller;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        rbReadOnly.setToggleGroup(radioGroup);
        rbReadOnly.setSelected(true);
        rbReadWrite.setToggleGroup(radioGroup);
        
        btnCancel.setOnAction(e->{
            Node  source = (Node)  e.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        });

        btnShare.setOnAction(e->{
            Node  source = (Node)  e.getSource(); 
            Stage stage  = (Stage) source.getScene().getWindow();
            Map<String,Boolean> sharedUser = new HashMap<>(); 
            Boolean readWrite = false;
            
            if(Objects.isNull(cbShareWith.getSelectionModel().getSelectedItem()))
            {
                Alert btnAlert = new Alert(Alert.AlertType.ERROR);
                btnAlert.contentTextProperty().setValue("Select User to Share File With");
                btnAlert.show();
                return;
            }
            if(radioGroup.getSelectedToggle().equals(rbReadWrite))
            {
                readWrite = true;
            }
            sharedUser.put(cbShareWith.getSelectionModel().getSelectedItem(), readWrite);
            parent.ReceiveReadOrWrite(sharedUser);
            stage.close();
        });
        
    }   
    
    public void SetCBShareWith(List<String> users)
    {   
        cbShareWith.setItems(FXCollections.observableArrayList(users));
    }
    
}
