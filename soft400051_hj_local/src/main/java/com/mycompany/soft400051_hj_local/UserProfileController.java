/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.mycompany.soft400051_hj_local.model.FileMethods;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;

/**
 *
 * @author ntu-user
 */
public class UserProfileController extends FileMethods implements Initializable {
    
    private String currentDir;
    
    @FXML
    private Label lbUsername;
    
    @FXML
    private GridPane gpGrid;
    
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
        gpGrid.getChildren().clear();
        
        Map<String,Boolean> userItems = dbconnection.listDirectory(owner, currentDir);
        int row = 1;
        int column = 0;
        
        userItems.forEach((name,folder)->{
            System.out.println(name + ": " + folder);
        });
            

        for(Map.Entry<String, Boolean> file : userItems.entrySet()){
            try {
                FXMLLoader loader = new FXMLLoader();
                System.out.println("Loader Created");
                loader.setLocation(getClass().getResource("file.fxml"));
                System.out.println("LoaderSet");

                AnchorPane aPane = loader.load();
                System.out.println("anchor Loaded");

                FileController fileController = loader.getController();
                System.out.println("Controller Created");
                fileController.setData(file.getKey());
                System.out.println("controller Set");

                gpGrid.add(aPane, column++, row);

                gpGrid.minWidth(Region.USE_COMPUTED_SIZE);             
                gpGrid.prefWidth(Region.USE_COMPUTED_SIZE);             
                gpGrid.maxWidth(Region.USE_PREF_SIZE);

                gpGrid.minHeight(Region.USE_COMPUTED_SIZE);             
                gpGrid.prefHeight(Region.USE_COMPUTED_SIZE);             
                gpGrid.maxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(aPane,new Insets(10));
            } catch (IOException ex) {
                Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
}
