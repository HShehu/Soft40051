/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft40051_hj_group;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author ntu-user
 */
public class FileControl {
        
//        File Management:
//- create new file: allowing the user to add new content and press a key to save
//- upload files from the container where the client app is running.
//- delete files
//- rename files
//- move files
//- copy files
    
public static void main(String[] args) {
               
        String fileContent = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. "
                + "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
                + "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
                + "It has survived not only five centuries, but also the leap into electronic typesetting, "
                + "remaining essentially unchanged. It was popularised in the 1960s with the release of "
                + "Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing "
                + "software like Aldus PageMaker including versions of Lorem Ipsum.";
        
        String fileName = "UserFile";
        FileMethods.CreateFile(fileContent, fileName);      
        File tmp = new File("./UserFile.txt");
        FileMethods.ChunkFile(tmp);
}
}