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
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

/**
 *
 * @author ntu-user
 */
public class UserProfileController implements FileMethods{
    
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
            
            SendFile(chunks);
        }
        catch(IOException ioerr){
            
        }
    }
    
      public static void SendFile(List<File> chunks)
    {
        List<String> containers = Arrays.asList("172.18.0.3","172.18.0.4","172.18.0.5","172.18.0.6");
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

                System.out.println("Working");

                sftp.disconnect();
                jschSession.disconnect();
            } catch (JSchException | SftpException ex) {
                Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
            counter++;
        }
    } 
    public void UploadFile(){
        FileChooser selectFile = new FileChooser();
        File uploadFile = selectFile.showOpenDialog(null);
      
        if(uploadFile == null)
        {
            return;
        }
        ChunkFile(uploadFile);      
    }


    
}
