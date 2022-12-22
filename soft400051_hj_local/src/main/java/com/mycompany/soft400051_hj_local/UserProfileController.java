/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local;

import java.io.File;
import javafx.stage.FileChooser;

/**
 *
 * @author ntu-user
 */
public class UserProfileController {
    
    public void UploadFile(){
    FileChooser selectFile = new FileChooser();
    File selectedFile = selectFile.showOpenDialog(null);
    
    if(selectedFile == null)
    {
        
    }
    
    
}
    
}
