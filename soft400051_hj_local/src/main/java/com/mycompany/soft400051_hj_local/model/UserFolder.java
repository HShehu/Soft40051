/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

/**
 *
 * @author ntu-user
 */
public class UserFolder extends Directory{
   
    
    public UserFolder(String name, String path) {
        super(name, path);
    }
    
    public UserFolder(String name) {
        super(name);
    }
     @Override
    public String toString()
    {
        return getName();
    }
}
