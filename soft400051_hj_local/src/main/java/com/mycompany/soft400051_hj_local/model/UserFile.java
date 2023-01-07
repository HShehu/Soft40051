/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

import javafx.beans.value.ObservableBooleanValue;

/**
 *
 * @author ntu-user
 */
public class UserFile extends Directory {
    private Integer id;
    private Boolean isOwned;
    private String chunk1;
    private String chunk2;
    private String chunk3;
    private String chunk4;
    
    public UserFile(String name,String path,Boolean isFolder,String chunk1,String chunk2,String chunk3,String chunk4){
        super(name,path);
        this.chunk1 = chunk1;
        this.chunk2 = chunk2;
        this.chunk3 = chunk3;
        this.chunk4 = chunk4;
    }
    public UserFile(String name,String path){
        super(name,path);
    }
    public UserFile(){}

    public void setId(Integer id){
        this.id = id;
    }
    public void setIsOwned(Boolean isOwned)
    {  
        this.isOwned = isOwned;
    }
    public void setChunk1(String chunk1){
        this.chunk1 = chunk1;
    }
    public void setChunk2(String chunk2){
        
        this.chunk2 = chunk2;
    }
    public void setChunk3(String chunk3){
        this.chunk3 = chunk3;
    }
    public void setChunk4(String chunk4){
        this.chunk4 = chunk4;
    }
    
    public Integer getId(){
        return this.id;
    }
    public Boolean getIsOwned(){
        return this.isOwned;
    }
    public String getChunk1(){
        return this.chunk1;
    }
    public String getChunk2(){
        return this.chunk2;
    }
    public String getChunk3(){
        return this.chunk3;
    }
    public String getChunk4(){
        return this.chunk4;
    }
    
    @Override
    public String toString()
    {
        return getName() +" || "+ getChunk1()+" || "+ getChunk2()+" || "+ getChunk3()+" || "+ getChunk4();
    }
}
