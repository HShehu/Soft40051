/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft40051_hj_group;

import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ntu-user
 */
public class FileMethods {
    public static void CreateFile(String content, String name)
    {
        
        try{
            File userFile = new File("./"+name.concat(".txt"));
            if(!(userFile.createNewFile()))
            {
                System.out.println("File " +name+ " already exists");
            }
            System.out.println("File " +name+ " created");
            
            try(FileWriter fileWriter = new FileWriter(userFile); )
            {
                fileWriter.write(content);
            }
            

        } catch (IOException ex) {
            Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
//        
//       
    }
    
    public static void ChunkFile(File ogFile)
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
        }
        catch(IOException ioerr){
            
        }
    }
    
    public static void AssembleFile(List<File> chunks)
    {
        String str = new String();
        for(File chunk : chunks)
        {
            try {
                str += Files.readString(chunk.toPath());
                
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
        
        String contents = new String(byteArray);
        
        System.out.println(contents);
        
    }
    
    public static void SendFile(List<File> chunks)
    {
        try(Jsch sshConn = new Jsch();)
        {
            
        }
    }
}