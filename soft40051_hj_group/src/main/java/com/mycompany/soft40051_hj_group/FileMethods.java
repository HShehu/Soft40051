/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft40051_hj_group;

import java.io.IOException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ntu-user
 */
public class FileMethods {
    public static void CreateFile()
    {
        try(InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader userIn = new BufferedReader(in);){
            
            System.out.printf("""
                              Write what you want into the file
                              """);
            
            String fileWords = userIn.readLine();
           
            String fileName = "User";
            File userFile = new File("./"+fileName.concat(".txt"));
            if(!(userFile.createNewFile()))
            {
                System.out.println("File " +fileName+ " already exists");
            }
            System.out.println("File " +fileName+ " created");
            
            try(FileWriter fileWriter = new FileWriter(userFile); )
            {
                fileWriter.write(fileWords);
            }
            

        } catch (IOException ex) {
            Logger.getLogger(FileMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
}
