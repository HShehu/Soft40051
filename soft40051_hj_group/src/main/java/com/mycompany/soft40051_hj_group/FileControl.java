/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft40051_hj_group;

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
          
        Scanner userIn = new Scanner(System.in);
            Integer select = -1;
   
            while(select != 0)
            {
                System.out.printf("""
                                  Hello! Welcome to the File Control Segment
                                  1 - Create File
                                  2 - Copy File
                                  3 - Upload File
                                  4 - Delete File
                                  5 - Rename File
                                  """);

                select = userIn.nextInt();
                
                switch(select){
                    case 0:
                        break;
                    case 1:
                        FileMethods.CreateFile();
                        break;
                    case 2:
                    break;
                    case 3:
                    break;
                    case 4:
                    break;
                    case 5:
                    break;
                    default:
                        System.out.println("No Option Like " + select + " Available\n");
                        break;
                }
            }
        }
        
        
                

    }
}
