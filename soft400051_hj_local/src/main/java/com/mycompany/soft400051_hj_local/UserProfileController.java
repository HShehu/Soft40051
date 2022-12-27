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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;

/**
 *
 * @author ntu-user
 */
public class UserProfileController implements FileMethods, Initializable{
    
    private String currentDir;
    private Map<String,Boolean> userItems = new HashMap();
    private final String owner;
    
    @FXML
    private Label lbUsername;
    
    @FXML
    private GridPane gpGrid;
    
    UserProfileController(String nowner)
    {
        this.owner = nowner;
        this.currentDir = "./";
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        lbUsername.setText("Welcome " + owner);
        refreshGrid();      
    }
    
    @Override
    public void CreateFile(String fileName, String filecontent) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public void CreateFile(File selectedFile){
        try{
            selectedFile.createNewFile();
        }
        catch (IOException ex) {
            Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void setCurrentDir(String directory)
    {
        this.currentDir = directory;
    }
    
    public void setUsername(String username){
        lbUsername.setText(username);
    }
    
    
     public void ChunkFile(File ogFile)
    {
        try{
            byte[] fileBytes = Files.readAllBytes(ogFile.toPath());
            int sizeOfChunk = Arrays.toString(fileBytes).length()/ 4 ;
            int start = 0;
            int counter = 0;
           
            List<File> chunks = Arrays.asList(
               new File("./",UUID.randomUUID().toString()+".txt"),
               new File("./",UUID.randomUUID().toString()+".txt"),
               new File("./",UUID.randomUUID().toString()+".txt"),
               new File("./",UUID.randomUUID().toString()+".txt")
            );
            
            
            for(File chunk:chunks)
            {
                try(FileWriter writer = new FileWriter(chunk.getPath())){
                    if(counter == 3)
                    {
                        int remChunkSize = Arrays.toString(fileBytes).length() - (sizeOfChunk*3);
                        writer.write(Arrays.toString(fileBytes), start, remChunkSize); 
 
                    }
                    else
                    {
                        
                        writer.write(Arrays.toString(fileBytes), start, sizeOfChunk);
                    }
                    
                }
                counter++;
                start += sizeOfChunk;
            }
            
            
            chunks.forEach((chunk)->{
            
              System.out.println(chunk.getPath());
                
            });
            
            SendFile(ogFile.getName(),chunks);
        }
        catch(IOException ioerr){
            
        }
    }
    
     public void SendFile(String fileName, List<File> chunks)
    {
        List<String> containers = Arrays.asList("172.18.0.3","172.18.0.4","172.18.0.5","172.18.0.6");
        List<String> newChunks = new ArrayList<>();
        Collections.shuffle(containers);
        int counter = 0;
        for(File chunk:chunks)
        {
            try {
                JSch jsch = new JSch();
                jsch.setKnownHosts("~/.ssh/known_hosts");
                jsch.addIdentity("~/.ssh/id_rsa");
                String host = containers.get(counter);
                Session jschSession = jsch.getSession("root",host);
                jschSession.connect();
                ChannelSftp sftp = (ChannelSftp)jschSession.openChannel("sftp");
                sftp.connect();

                sftp.put(chunk.getPath(),chunk.getName());
                sftp.exit();
                String newname = host+":"+chunk.getName();
                newChunks.add(newname);
                System.out.println("Working" +newname);
                System.out.println("Working");

                sftp.disconnect();
                jschSession.disconnect();
            } catch (Exception ex) {
                Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
            counter++;
        }
        dbconnection.filesInsert(fileName, owner, newChunks);
        
        chunks.forEach((chunk)->{
        chunk.delete();
        });
        newChunks.clear();
        
    } 
    public void UploadFile(){
        FileChooser selectFile = new FileChooser();
        File uploadFile = selectFile.showOpenDialog(null);
      
        if(uploadFile == null)
        {
            return;
        }
        ChunkFile(uploadFile);
        refreshGrid();
        
    }
    
    public void refreshGrid(){
        gpGrid.getChildren().clear();
        
        userItems = dbconnection.listDirectory(owner, currentDir);
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
