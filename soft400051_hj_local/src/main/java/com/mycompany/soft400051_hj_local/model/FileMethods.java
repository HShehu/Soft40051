/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
}
