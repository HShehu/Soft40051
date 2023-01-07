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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
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
    private Map<String,Boolean> readWritePerm = new HashMap<>();
    private final LoginController parent; //!< Parent loginController
    private static final UserFolder sharedFolder = new UserFolder("Shared");
    private static final UserFolder homeFolder = new UserFolder("Home");
    
 
    
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
    private Button btnUnshareFile;
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
    UserProfileController(String owner,LoginController controller){   
        super(owner);
        this.parent = controller;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
        lbUsername.setText("Welcome " + getOwner());
        dbconnection.initFilesTable();
        dbconnection.initDeletedTable();
        setCurDir(currentDir);
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
        btnAlert.contentTextProperty().setValue("Are you Sure you want to Copy this File?");
        
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
            userFolders.remove(sharedFolder);
            move.SetCBFolder(userFolders);
            
            Scene scene = new Scene(root);
            moveWindow.setScene(scene);
            moveWindow.showAndWait();
            
            if(Objects.nonNull(dstFolder))
            {
                String folderName = dstFolder.getName();
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
        setCurDir("Home");   
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
            List<String> users = dbconnection.listUsers(getOwner());
            if(users.isEmpty())
            {
                Alert btnAlert = new Alert(Alert.AlertType.ERROR);
                btnAlert.contentTextProperty().setValue("No Users");
                btnAlert.show();
                return;
            }
            share.SetCBShareWith(users);
            
            Scene scene = new Scene(root);
            shareWindow.setScene(scene);
            shareWindow.showAndWait();
            
            if(!readWritePerm.isEmpty())
            {
                ShareFile(srcFile,readWritePerm);
                Alert btnAlert = new Alert(Alert.AlertType.INFORMATION);
                btnAlert.contentTextProperty().setValue("File: "+srcFile.getName()+"\nShared Successfully With: "+readWritePerm.keySet().toArray()[0].toString());
                btnAlert.show();
                readWritePerm.clear();
                refreshGrid();
            }
        } catch (IOException ex) {
            Logger.getLogger(UserProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void UnshareBtnClicked(){
        UserFile srcFile = tableFiles.getSelectionModel().getSelectedItem();
        
        Alert btnAlert = new Alert(AlertType.CONFIRMATION);
        btnAlert.contentTextProperty().setValue("Are you Sure you want to Unshare this File?\nFile Name: "+srcFile.getName());
        
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
           UnshareFile(srcFile);
            refreshGrid();
        }  
        
        
    }
    public void DownloadBtnClicked(){
        UserFile srcFile = tableFiles.getSelectionModel().getSelectedItem();
        
        Alert btnAlert = new Alert(AlertType.CONFIRMATION);
        btnAlert.contentTextProperty().setValue("Do you Want to Download this File?\nFile Name: "+srcFile.getName());
        
        Optional<ButtonType> result = btnAlert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
           DownloadFile(srcFile);
           refreshGrid();
        }  
        
    }
   
    public void RestoreBtnClicked(){
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
        
        //SetLblCurDir(getCurDir());
        SetButtons();
        
        tilePane.getChildren().clear();
        filesList.clear();
        userFolders.clear();
        
        List<UserFile> userFiles = dbconnection.listDirectory(getOwner(), getCurDir());
        userFolders = dbconnection.listFolders(getOwner(), getCurDir());

       
        filesList = FXCollections.observableArrayList(userFiles);
        tableFiles.setItems(filesList);
       
        colFileName.setCellValueFactory(new PropertyValueFactory("name"));
        
        loadFolders(); 
    }
    public void loadFolders(){
        if(!currentDir.equals("Deleted") && !currentDir.equals("Home") ){
            
            userFolders.add(homeFolder);
        }
        if(currentDir.equals("Home")){
            
            userFolders.add(sharedFolder);
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
    //public void SetLblCurDir(String curDir){lblCurDir.setText(curDir);}
        @Override
    public void setCurDir(String currentDir)
    {
        this.currentDir = currentDir;
        lblCurDir.setText(getCurDir());
        refreshGrid();
    }
    public void SetButtons(){
        
        BooleanBinding isShared = Bindings.when(Bindings.equal(getCurDir(), new SimpleStringProperty(sharedFolder.getName())))
                    .then(true)
                    .otherwise(false);
        BooleanBinding isDeleted = Bindings.when(Bindings.equal(getCurDir(), new SimpleStringProperty("Deleted")))
                    .then(true)
                    .otherwise(false);
        
        btnRecycleBin.visibleProperty().bind(isDeleted);
        btnRecycleBin.managedProperty().bind(isDeleted);
        btnUnshareFile.visibleProperty().bind(isShared);
        btnUnshareFile.managedProperty().bind(isShared);
        btnShareFile.visibleProperty().bind(isShared.not());
        btnShareFile.managedProperty().bind(isShared.not());
        
        List<Button> allButtons = Arrays.asList(btnMoveFile,btnCopyFile,btnDeleteFile,btnRenameFile,btnUploadFile,btnCreateFile,btnShareFile,btnDownloadFile,btnUnshareFile);
        
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
            btnRecycleBin.disableProperty().bind(Bindings.isNull(tableFiles.getSelectionModel().selectedItemProperty()));
            allButtons.forEach((button)->{
                if(button.disableProperty().isBound())
                {
                    button.disableProperty().unbind();
                }
                button.disableProperty().setValue(Boolean.TRUE);
            });
        }
        
        if (getCurDir().equals(sharedFolder.getName())){
            
            allButtons.forEach((Button button)->{
                if(button.disableProperty().isBound())
                {
                    button.disableProperty().unbind();
                }
                
                if(button.equals(btnCreateFile) || button.equals(btnUploadFile) || button.equals(btnMoveFile) || button.equals(btnCopyFile))
                {
                    button.disableProperty().setValue(Boolean.TRUE);
                }
                
                if(button.equals(btnUnshareFile) || button.equals(btnDeleteFile))
                {
                    BooleanBinding buttonDis = Bindings.when(Bindings
                            .and(Bindings.isNotNull(tableFiles.getSelectionModel().selectedItemProperty()),Bindings.selectBoolean(tableFiles.getSelectionModel().selectedItemProperty(), "isOwned") ))
                            .then(false).otherwise(true);
                    button.disableProperty().bind(buttonDis);
                }
                
                if(button.equals(btnRenameFile)){
                    BooleanBinding buttonDis = Bindings.when(Bindings
                            .and(Bindings.isNotNull(tableFiles.getSelectionModel().selectedItemProperty()),Bindings.or(Bindings.selectBoolean(tableFiles.getSelectionModel().selectedItemProperty(), "isOwned"), Bindings.selectBoolean(tableFiles.getSelectionModel().selectedItemProperty(), "isWritable") )  ))
                            .then(false).otherwise(true);
                    button.disableProperty().bind(buttonDis);
                }
                if(button.equals(btnDownloadFile)){
                    button.disableProperty().bind(Bindings.isNull(tableFiles.getSelectionModel().selectedItemProperty()));
                }
                
            });
            
        }
    }
    
    public void ReceiveRename(String newName){this.childReceive = newName;}
    public void ReceiveCreateFileContent(String[] newFile){this.createFileContent = newFile;}
    public void RecieveMoveFolder(UserFolder dstFolder){this.dstFolder = dstFolder;}
    public void ReceiveReadOrWrite(Map<String,Boolean> readWrite){this.readWritePerm = readWrite;}
   
}
