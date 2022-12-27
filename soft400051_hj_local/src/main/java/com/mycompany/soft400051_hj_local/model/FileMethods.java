/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.mycompany.soft400051_hj_local.dbconnection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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
public abstract class FileMethods {
    
    protected final String owner;
    
    protected FileMethods(String email){
        this.owner = email;
    }
    
    
        public static void CreateFile(){};
        
        public  void ChunkFile(File ogFile)
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
    
        public  void UploadFile(){
        FileChooser selectFile = new FileChooser();
        File uploadFile = selectFile.showOpenDialog(null);
      
        if(uploadFile == null)
        {
            return;
        }
        ChunkFile(uploadFile); 
    }
    
}
