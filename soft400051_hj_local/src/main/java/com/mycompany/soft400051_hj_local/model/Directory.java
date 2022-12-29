/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

/**
 *
 * @author ntu-user
 */
public class Directory {
    private String name;
    private String path;
    private Boolean isFolder;
    
    Directory(String name,String path , Boolean isFolder){
        this.name = name;
        this.path = path;
        this.isFolder = isFolder;
    }
    
    Directory(){}
    
    public void setName(String name){
        this.name = name;
    }
    public void setPath(String path){
        this.path = path;
    }
    
    
    public String getName(){
        return this.name;
    }
    public String getPath(){
        return this.path;
    }
    public Boolean isFolderPath(){
        return this.isFolder;
    }

    
}
