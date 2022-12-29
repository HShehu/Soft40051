/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.FileMethods;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
/**
 *
 * @author ntu-user
 */
public class UserProfileController extends FileMethods implements Initializable {
    
    private String currentDir;
    
    @FXML
    private TilePane tilePane;

    @FXML
    private Button btnUpload;

    @FXML
    private Label lbUsername;
    
    UserProfileController(String owner)
    {   
        super(owner);
        this.currentDir = "./";
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        lbUsername.setText("Welcome " + owner);
        refreshGrid();      
    }
    
    public void UploadBtnClicked()
    {
        UploadFile();
        refreshGrid();
    };
    
 
    
    public void refreshGrid(){
//        gpGrid.getChildren().clear();
        
        tilePane.getChildren().clear();
        
        Map<String,Boolean> userItems = dbconnection.listDirectory(owner, currentDir);
//        int row = 1;
//        int column = 0;
        
        userItems.forEach((name,folder)->{
            System.out.println(name + ": " + folder);
        });
            

        for(Map.Entry<String, Boolean> file : userItems.entrySet()){
            try {
                //
                FXMLLoader fxLoader = new FXMLLoader();
                System.out.println("Loader Created");
                fxLoader.setLocation(getClass().getResource("file.fxml"));
                System.out.println("Location Set");

                VBox aPane = fxLoader.load();
                System.out.println("anchor Loaded");

                FileController fileController = fxLoader.getController();
                System.out.println("Controller Created");
                fileController.setData(file.getKey());
                System.out.println("controller Set");

                tilePane.getChildren().add(aPane);

//               
            } catch (IOException ex) {
                Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
}
