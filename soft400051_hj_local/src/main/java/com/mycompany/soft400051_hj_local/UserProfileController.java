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
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author ntu-user
 */


/*! \brief This Class controls the fxml Page for the Users Profile.
 *        
 */

public class UserProfileController extends FileMethods implements Initializable {
    
    private ObservableList<UserFile> filesList = FXCollections.observableArrayList(); //!< List of files to display
    private List<UserFolder> userFolders = new ArrayList<>(); //!< List of folders to display
    private String childReceive = ""; //!< New Name received from renameDialog fxml
    private UserFolder dstFolder; //!< selectedFolder received from moveDialog fxml
    private String[] createFileContent = null; //!< File Name and Content received from createDialog fxml
    private final LoginController parent; //!< Parent loginController
    

    
 
    
    @FXML
    private Button btnCreateFile; //!< Create File Button

    @FXML
    private TilePane tilePane; //!< Tile Pane showing list of folders

    @FXML
    private Button btnDeleteFile; //!< Delete File Button

    @FXML
    private Button btnRenameFile; //!< Rename File Button
    
    @FXML
    private Button btnRecycleBin; //!< Recycle Bin Button
    @FXML
    private Button btnShareFile;
    @FXML
    private Button btnDownloadFile;
    @FXML
    private TableView<UserFile> tableFiles; //!< Table of Files in Current Directory

    @FXML
    private Button btnCopyFile; //!< Copy File Button

    @FXML
    private Button btnUploadFile; //!< Upload File Button

    @FXML
    private Label lbUsername;
    @FXML
    private Label lblCurDir;

    @FXML
    private Button btnMoveFile; //!< Move File Button
    
    @FXML
    private TableColumn<?, ?> colSharedWith;


    @FXML
    private TableColumn<?, ?> colFileName;

    @FXML
    private TableColumn<?, ?> colCreatedAt;
    
   
    /*! \brief The Rename Dialog Controller recieves the instance of the UserProfileController as parent.
                             It does this to to send the information back to the exact instance of the controller.
 *  @param controller UserProfileController argument   
 */
    UserProfileController(String owner,LoginController controller)
    {   
        super(owner);
        this.parent = controller;

    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
        lbUsername.setText("Welcome " + getOwner());
        refreshGrid();
    }
    
    public void Logout() throws IOException
    {
        Alert btnAlert = new Alert(Alert.AlertType.CONFIRMATION);
        btnAlert.contentTextProperty().setValue("Are you Sure you want to Logout?");
        
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.get() == ButtonType.OK){
            
            parent.user_logout(getOwner());
    
        }

    }
    public void CopyBtnClicked()
    {
        Alert btnAlert = new Alert(AlertType.CONFIRMATION);
        btnAlert.contentTextProperty().setValue("Are you Sure you want to copy this File?");
        
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            UserFile srcFile = tableFiles.getSelectionModel().getSelectedItem();  
            CopyFile(srcFile);
            refreshGrid();
        }  
    }
    
    public void CreateBtnClicked()
    {
       try {
            Stage createWindow = new Stage();
            createWindow.initModality(Modality.APPLICATION_MODAL);
            createWindow.setTitle("Create New File ");
            
            CreateDialog create = new CreateDialog(this);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createDialog.fxml"));
            loader.setController(create);
            Parent root = loader.load();
            
            Scene scene = new Scene(root);
            createWindow.setScene(scene);
            createWindow.showAndWait();
            
           
            if(createFileContent != null){
                
                CreateFile(createFileContent[0],createFileContent[1]);
                createFileContent = null;
                refreshGrid();
            }
        } catch (IOException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
                childReceive = "";
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
                String folderName = dstFolder.getName();
                if(folderName.equals("home"))
                {
                    folderName = "./";
                }
                MoveFile(folderName,srcFile);
                dstFolder = null;
                refreshGrid();
            }
        } catch (IOException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void DeleteBtnClicked()
    {
        Alert btnAlert = new Alert(AlertType.CONFIRMATION);
        btnAlert.contentTextProperty().setValue("Are you Sure you want to Delete this File?");
        
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            DeleteFile(tableFiles.getSelectionModel().getSelectedItem());
            refreshGrid();
        }  
        
    }
    
    public void RecycleBtnClicked()
    {
        setCurDir("Deleted");
        
    }
 
    public void HomeBtnClicked()
    {
        setCurDir("./");   
    }
    
    public void ShareBtnClicked(){
        try {
            UserFile srcFile = tableFiles.getSelectionModel().getSelectedItem();
            Stage shareWindow = new Stage();
            shareWindow.initModality(Modality.APPLICATION_MODAL);
            shareWindow.setTitle("Share " + srcFile.getName());
            
            ShareDialog share = new ShareDialog(this);
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("shareDialog.fxml"));
            loader.setController(share);
            
            Parent root = loader.load();
            List<String> users = Arrays.asList("You ","Me","DunDundun");
            share.SetCBShareWith(users);
            
            Scene scene = new Scene(root);
            shareWindow.setScene(scene);
            shareWindow.showAndWait();
            
//            if(Objects.nonNull(dstFolder))
//            {
//                String folderName = dstFolder.getName();
//                if(folderName.equals("home"))
//                {
//                    folderName = "./";
//                }
//                MoveFile(folderName,srcFile);
//                dstFolder = null;
//                refreshGrid();
//            }
        } catch (IOException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void setCurDir(String currentDir)
    {
        if(currentDir.equals("home"))
        {
            currentDir = "./";
        }
        this.currentDir = currentDir;
        refreshGrid();
    }
    public void RestoreBtnClicked()
    {
        Alert btnAlert = new Alert(AlertType.CONFIRMATION);
        btnAlert.contentTextProperty().setValue("Are you Sure you want to Restore this File?");
        
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            RestoreFile(tableFiles.getSelectionModel().getSelectedItem());
            refreshGrid();
        }  
    }
 
    public void refreshGrid(){
        
        SetLblCurDir(getCurDir());
        SetButtons();
        
        tilePane.getChildren().clear();
        filesList.clear();
        userFolders.clear();
        
        List<UserFile> userFiles = dbconnection.listDirectory(getOwner(), getCurDir());
        List<UserFile> onlyUserFiles = new ArrayList<>();
        
        userFiles.forEach((UserFile item) ->{     
            if(item.isFolderPath()){
                UserFolder folder = new UserFolder(item.getName(),item.getPath());
                userFolders.add(folder);
            }else{
                onlyUserFiles.add(item);
            }
        });
        
        
        filesList = FXCollections.observableArrayList(onlyUserFiles);
        tableFiles.setItems(filesList);
       
        colFileName.setCellValueFactory(new PropertyValueFactory("name"));
        
        loadFolders();
        
        
    }
    public void loadFolders(){
        if(!currentDir.equals("./")){
            UserFolder homeFolder = new UserFolder("home");
            userFolders.add(homeFolder);
        }    
        
        for(UserFolder folder : userFolders){
            try {
                FXMLLoader fxLoader = new FXMLLoader();
                System.out.println("Loader Created");
                fxLoader.setLocation(getClass().getResource("folder.fxml"));
                System.out.println("Location Set");

                FolderController folderController = new FolderController(folder,this);
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
        
        if(getCurDir().equals("Deleted"))
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
    public void ReceiveCreateFileContent(String[] newFile){this.createFileContent = newFile;}
    public void RecieveMoveFolder(UserFolder dstFolder){this.dstFolder = dstFolder;};
    public void ReceiveReadOrWrite(){}
   
}
