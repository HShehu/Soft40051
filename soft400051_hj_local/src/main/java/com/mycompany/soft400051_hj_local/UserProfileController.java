/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.FileMethods;
import com.mycompany.soft400051_hj_local.model.UserFile;
import com.mycompany.soft400051_hj_local.model.UserFolder;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ObservableList<UserFile> filesList = FXCollections.observableArrayList();
    
    @FXML
    private Button btnCreateFile;

    @FXML
    private TilePane tilePane;

    @FXML
    private Button btnDeleteFile;

    @FXML
    private Button btnRenameFile;

    @FXML
    private TableView<UserFile> tableFiles;

    @FXML
    private Button btnCopyFile;

    @FXML
    private Button btnUpload;

    @FXML
    private Label lbUsername;

    @FXML
    private Button btnMoveFile;
    
    @FXML
    private TableColumn<?, ?> colSharedWith;


    @FXML
    private TableColumn<?, ?> colFileName;

    @FXML
    private TableColumn<?, ?> colCreatedAt;
    
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
        
        tilePane.getChildren().clear();
        filesList.clear();
        
        List<UserFile> userFiles = dbconnection.listDirectory(owner, currentDir);
        List<UserFolder> userFolders = new ArrayList<>();
        
        System.out.println("Start iteration");
        
        userFiles.forEach(item ->{     
            if(item.isFolderPath()){
                UserFolder folder = new UserFolder(item.getName(),item.getPath(),item.isFolderPath());
                userFolders.add(folder);
                userFiles.remove(item);
            }
        });
        
        System.out.println("Create Link");
        
        filesList = FXCollections.observableArrayList(userFiles);
        tableFiles.setItems(filesList);
        
        System.out.println("Llinking");
        colFileName.setCellValueFactory(new PropertyValueFactory("name"));
        System.out.println("Table Made");
//        
//            
//        
//        for(UserFolder folder : userFolders){
//            try {
//                FXMLLoader fxLoader = new FXMLLoader();
//                System.out.println("Loader Created");
//                fxLoader.setLocation(getClass().getResource("folder.fxml"));
//                System.out.println("Location Set");
//
//                VBox vBox = fxLoader.load();
//                System.out.println("anchor Loaded");
//
//                FolderController folderController = new FolderController(folder);
//                System.out.println("Controller Created");
//                fxLoader.setController(folderController);
//                System.out.println("controller Set");
//
//                tilePane.getChildren().add(vBox);          
//            } catch (IOException ex) {
//                Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }
   
}
