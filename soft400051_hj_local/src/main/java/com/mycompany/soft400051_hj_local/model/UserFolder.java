/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

/**
    * @brief Class to save details of Folders
    * 
    * @author Joshua Miner
    */
public class UserFolder extends Directory{
   
    /**
     * @param path
    * @brief Constructor with name and path
    * 
    * @param  name String
    */
    public UserFolder(String name, String path) {
        super(name, path);
    }
    
    /**
    * @brief Constructor with just the name
    * 
    * @param  name String
    */
    
    public UserFolder(String name) {
        super(name);
    }
    
    /**
    * @brief Override to String to get name of UserFolder
    * 
    * 
    */
     @Override
    public String toString()
    {
        return getName();
    }
}
