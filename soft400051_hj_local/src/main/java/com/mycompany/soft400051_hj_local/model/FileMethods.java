/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.mycompany.soft400051_hj_local.dbconnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
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
    
    private final String owner;
    private String currentDir = "./";
    
    public static enum Operation{
        MOVE,
        RENAME
    }
    
    protected FileMethods(String email){
        this.owner = email;
    }
    
    public void setCurDir(String currentDir)
    {
        this.currentDir = currentDir;
    }
    
    public String getCurDir()
    {
        return this.currentDir;
    }
    public String getOwner()
    {
        return this.owner;
    }
    
    
        public void CreateFile(String fileName,String fileContent){
        try {
            
            File userFile = new File(fileName);
            userFile.createNewFile();
            userFile.deleteOnExit();
            
            try(FileWriter fileWriter = new FileWriter(userFile); )
            {
                fileWriter.write(fileContent);
            }
            ChunkFile(userFile);
            
        } catch (IOException ex) {
            Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        };
        
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
            chunks.forEach((chunk)->{
            
              chunk.delete();
                
            });
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
                System.out.println("Working");

                sftp.disconnect();
                jschSession.disconnect();
            } catch (JSchException | SftpException ex) {
                Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
            }
            counter++;
        }
        
        dbconnection.filesInsert(fileName, owner,this.currentDir, newChunks);
        
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
        
        public void CopyFile(UserFile srcFile)
        {
            File copyFile = new File("copy"+srcFile.getName());
            grabFile(srcFile).renameTo(copyFile);
            ChunkFile(copyFile);
            copyFile.delete();
        }
        
        public File grabFile(UserFile userFile)
        {
            List<String> chunks = Arrays.asList(
                    userFile.getChunk1(),
                    userFile.getChunk2(),
                    userFile.getChunk3(),
                    userFile.getChunk4()
            );
            List<File> reFiles = new ArrayList<>();
            
            for(String chunk : chunks){
                String container = chunk.split(":")[0];
                String fileName = chunk.split(":")[1];

                System.out.println(container + "      " + fileName);
                    try {
                    JSch jsch = new JSch();
                    jsch.setKnownHosts("~/.ssh/known_hosts");
                    jsch.addIdentity("~/.ssh/id_rsa");
                    Session jschSession = jsch.getSession("root",container);
                    jschSession.connect();
                    ChannelSftp sftp = (ChannelSftp)jschSession.openChannel("sftp");
                    sftp.connect();

                    sftp.get(fileName,"./");
                    sftp.exit();
                    System.out.println("Working");

                    sftp.disconnect();
                    jschSession.disconnect();
                } catch (JSchException | SftpException ex) {
                    Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
                }
                File nFile = new File(fileName);
                reFiles.add(nFile);
            }
            
            byte[] byteArray = assembleFile(reFiles);
            
            File grabFile = new File(userFile.getName());
            
            try (OutputStream os = new FileOutputStream(grabFile);){
                os.write(byteArray);
            }catch (Exception e) {
            }
            return grabFile;
        }
        
        public byte[] assembleFile(List<File> chunks){
            
            String str = new String();
            
            for(File chunk : chunks)
            {
                try {
                    str += Files.readString(chunk.toPath());
                    chunk.delete();

                } catch (IOException ex) {
                    Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            int length = str.length() - 1;

            String[] StrArray = str.substring(1, length).split(",");

            byte[] byteArray = new byte[StrArray.length];

            for(int i = 0 ;i<StrArray.length ;i++)
            {
                byteArray[i] =  Byte.parseByte(StrArray[i].trim()); 
            }
           
//            String contents = new String(byteArray);
//
            return byteArray; 
        }
        
        public void  RenameFile(String newName, UserFile userFile){
            dbconnection.filesUpdate(Operation.RENAME,newName,userFile,owner);
        }
        public void  MoveFile(String dstPath, UserFile userFile){
            dbconnection.filesUpdate(Operation.MOVE,dstPath,userFile,owner);
        }
        public void DeleteFile(UserFile userFile)
        {
           List<String> chunks = Arrays.asList(
                    userFile.getChunk1(),
                    userFile.getChunk2(),
                    userFile.getChunk3(),
                    userFile.getChunk4()
            );
            
           try{
            for(String chunk : chunks){
                String container = chunk.split(":")[0];
                String fileName = chunk.split(":")[1];

                System.out.println(container + "########" + fileName);
                    try {
                    JSch jsch = new JSch();
                    jsch.setKnownHosts("~/.ssh/known_hosts");
                    jsch.addIdentity("~/.ssh/id_rsa");
                    Session jschSession = jsch.getSession("root",container);
                    jschSession.connect();
                    ChannelSftp sftp = (ChannelSftp)jschSession.openChannel("sftp");
                    sftp.connect();
                    
                    try{
                        sftp.get(fileName, "./");
                        sftp.rm(fileName);
                        sftp.put("./"+fileName,"deleted");
                        File tmp = new File("./"+fileName);
                        tmp.delete();
                    }catch(SftpException ex){
                        Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, "Deleted Folder Does not Exist\n Creating it.....", ex);
                        sftp.mkdir("deleted");
                        sftp.rename("./"+fileName, "deleted");
                    }
                    
                    
                    sftp.exit();
                    System.out.println("Working");

                    sftp.disconnect();
                    jschSession.disconnect();
                } catch (JSchException | SftpException ex) {
                    Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            dbconnection.DeleteFile(owner, userFile);
           }catch(Exception err){
               
           }
        }
        
       
    
}
