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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author ntu-user
 */
public class UserProfileController extends FileMethods implements Initializable {
    
    private String currentDir;
    private ObservableList<UserFile> filesList = FXCollections.observableArrayList();
    String childReceive = "";
    private UserFolder dstFolder;
    private List<UserFolder> userFolders = new ArrayList<>();
    
    @FXML
    private Button btnCreateFile;

    @FXML
    private TilePane tilePane;

    @FXML
    private Button btnDeleteFile;

    @FXML
    private Button btnRenameFile;
    
    @FXML
    private Button btnRecycleBin;

    @FXML
    private TableView<UserFile> tableFiles;

    @FXML
    private Button btnCopyFile;

    @FXML
    private Button btnUploadFile;

    @FXML
    private Label lbUsername;
    @FXML
    private Label lblCurDir;

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
    
    public void CopyBtnClicked()
    {
        Alert btnAlert = new Alert(AlertType.WARNING);
        btnAlert.contentTextProperty().setValue("Are you Sure you want to copy this File?");
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.isPresent())
        {
            UserFile srcFile = tableFiles.getSelectionModel().getSelectedItem();  
            CopyFile(srcFile);
            refreshGrid();
        }  
    }
    
    public void CreateBtnClicked()
    {
        String[] createdFile = CreateDialog.display();
        
        CreateFile(createdFile[0],createdFile[1],currentDir);
        refreshGrid();
    }
    
    public void UploadBtnClicked()
    {
        UploadFile();
        refreshGrid();
    };
    
    public void RenameBtnClicked()
    {
        try {
            UserFile srcFile = tableFiles.getSelectionModel().getSelectedItem();
            Stage renameWindow = new Stage();
            renameWindow.initModality(Modality.APPLICATION_MODAL);
            renameWindow.setTitle("Rename " + srcFile.getName());
            
            RenameDialog rename = new RenameDialog(this);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("renameDialog.fxml"));
            loader.setController(rename);
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            renameWindow.setScene(scene);
            renameWindow.showAndWait();
            
            if(!childReceive.isBlank()){
                
                RenameFile(childReceive,srcFile);
                refreshGrid();
            }
        } catch (IOException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void MoveBtnClicked()
    {
        try {
            UserFile srcFile = tableFiles.getSelectionModel().getSelectedItem();
            Stage moveWindow = new Stage();
            moveWindow.initModality(Modality.APPLICATION_MODAL);
            moveWindow.setTitle("Move " + srcFile.getName());
            
            MoveDialog move = new MoveDialog(this);
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("moveDialog.fxml"));
            loader.setController(move);
            
            Parent root = loader.load();
            move.SetCBFolder(userFolders);
            
            Scene scene = new Scene(root);
            moveWindow.setScene(scene);
            moveWindow.showAndWait();
            
            if(Objects.nonNull(dstFolder))
            {
                MoveFile(dstFolder.getPath().concat(dstFolder.getName()),srcFile);
                refreshGrid();
            }
        } catch (IOException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void DeleteBtnClicked()
    {
        Alert btnAlert = new Alert(AlertType.WARNING);
        btnAlert.contentTextProperty().setValue("Are you Sure you want to Delete this File?");
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.isPresent())
        {
            DeleteFile(tableFiles.getSelectionModel().getSelectedItem());
            refreshGrid();
        }
    }
    
    public void RecycleBtnClicked()
    {
        this.currentDir = "Deleted";
        
        refreshGrid();
        
    }
 
    public void HomeBtnClicked()
    {
        this.currentDir = "./";
        refreshGrid();
        
    }
    
    public void RestoreBtnClicked()
    {
    }
 
    
    public void refreshGrid(){
        
        SetLblCurDir(this.currentDir);
        SetButtons();
        
        tilePane.getChildren().clear();
        filesList.clear();
        userFolders.clear();
        
        List<UserFile> userFiles = dbconnection.listDirectory(owner, currentDir);
        List<UserFile> onlyUserFiles = new ArrayList<>();
        
        System.out.println("Initialize");
        
        userFiles.forEach((UserFile item) ->{     
            if(item.isFolderPath()){
                UserFolder folder = new UserFolder(item.getName(),item.getPath(),item.isFolderPath());
                userFolders.add(folder);
            }else{
                onlyUserFiles.add(item);
            }
        });
        
        
        
        filesList = FXCollections.observableArrayList(onlyUserFiles);
        tableFiles.setItems(filesList);
        
        filesList.forEach(user->{
            System.out.println(user.toString());
        });
        colFileName.setCellValueFactory(new PropertyValueFactory("name"));
        
        
            
        
        for(UserFolder folder : userFolders){
            try {
                FXMLLoader fxLoader = new FXMLLoader();
                System.out.println("Loader Created");
                fxLoader.setLocation(getClass().getResource("folder.fxml"));
                System.out.println("Location Set");

                FolderController folderController = new FolderController(folder);
                System.out.println("Controller Created");
                fxLoader.setController(folderController);
                System.out.println("controller Set");

                VBox vBox = fxLoader.load();
                System.out.println("anchor Loaded");

                tilePane.getChildren().add(vBox);          
            } catch (IOException ex) {
                Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void SetLblCurDir(String curDir)
    {
        if("./".equals(curDir)){
            lblCurDir.setText("Home");
        }
        else{
            lblCurDir.setText(curDir);
        }
    }
    public void SetButtons()
    {
        btnRecycleBin.visibleProperty().setValue(Boolean.FALSE);
        List<Button> allButtons = Arrays.asList(btnMoveFile,btnCopyFile,btnDeleteFile,btnRenameFile,btnUploadFile,btnCreateFile);
        
        allButtons.forEach((button)->{
            if(button.disableProperty().isBound())
                {
                    button.disableProperty().unbind();
                }
            
            button.disableProperty().setValue(Boolean.FALSE);
             
            if(!(button.textProperty().getValue().equals("Upload File") || button.textProperty().getValue().equals("Create File"))){
                button.disableProperty().bind(Bindings.isNull(tableFiles.getSelectionModel().selectedItemProperty()));
            }
        });
        
        if(this.currentDir.equals("Deleted"))
        {
            btnRecycleBin.visibleProperty().setValue(Boolean.TRUE);
            btnRecycleBin.disableProperty().bind(Bindings.isNull(tableFiles.getSelectionModel().selectedItemProperty()));
            allButtons.forEach((button)->{
                if(button.disableProperty().isBound())
                {
                    button.disableProperty().unbind();
                }
                button.disableProperty().setValue(Boolean.TRUE);
            });
        }
    }
    public void ReceiveRename(String newName){this.childReceive = newName;}
    public void RecieveMoveFolder(UserFolder dstFolder){this.dstFolder = dstFolder;};
   
}
