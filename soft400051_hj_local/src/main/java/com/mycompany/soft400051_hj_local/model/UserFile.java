/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.soft400051_hj_local.model;

/**
 *@brief Class to save file details
 * @author Joshua Miner
 */
public class UserFile extends Directory {
    private Integer id;
    private Boolean isOwned;
    private Boolean isWritable;
    private Integer ownerId;
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

    /**
     * @param id
    *@brief Set File id
    * 
    */
    public void setId(Integer id){
        this.id = id;
    }
    
    /**
     * @param ownerId
    *@brief Set Owner id
    * 
    */
    public void setOwnerId(Integer ownerId){
        this.ownerId = ownerId;
    }
    /**
     * @param isOwned
    *@brief Set Boolean isOwned
    * 
    */
    
    public void setIsOwned(Boolean isOwned)
    {  
        this.isOwned = isOwned;
    }
    
    /**
     * @param isWritable
    *@brief Set isWritable
    * 
    */
    
    public void setIsWritable(Boolean isWritable){
        this.isWritable = isWritable;
    }
    
    /**
     * @param chunk1
    *@brief Set Chunk 1
    * 
    */
    public void setChunk1(String chunk1){
        this.chunk1 = chunk1;
    }
    
    /**
     * @param chunk2
    *@brief Set Chunk 2
    * 
    */
    public void setChunk2(String chunk2){
        
        this.chunk2 = chunk2;
    }
    
    /**
     * @param chunk3
    *@brief Set Chunk 3
    * 
    */
    public void setChunk3(String chunk3){
        this.chunk3 = chunk3;
    }
    
    /**
     * @param chunk4
    *@brief Set Chunk 4
    * 
    */
    public void setChunk4(String chunk4){
        this.chunk4 = chunk4;
    }
    
    /**
    *@brief Get File id
    * @return FIle id
    */
    public Integer getId(){
        return this.id;
    }
    
    /**
    *@brief Get Owner id
    * @return Owner id
    */
    public Integer getOwnerId(){
        return this.ownerId;
    }
    
    /**
    *@brief Get isOwned
    * @return Boolean
    */
    public Boolean getIsOwned(){
        return this.isOwned;
    }
    
    /**
    *@brief Get isWritable
    * @return Boolean
    */
    public Boolean getIsWritable(){
        return this.isWritable;
    }
    
    /**
    *@brief Get Chunk 1
    * @return String
    */
    public String getChunk1(){
        return this.chunk1;
    }
    
    /**
    *@brief Get Chunk 2
    * @return 
    */
    public String getChunk2(){
        return this.chunk2;
    }
    
    /**
    *@brief Get Chunk 3
    * @return 
    */
    public String getChunk3(){
        return this.chunk3;
    }
    
    /**
    *@brief Get Chunk 4
    * @return 
    */
    public String getChunk4(){
        return this.chunk4;
    }
    
    /**
    *@brief Override default to String method
    * @return Name and all chunks
    */
    @Override
    public String toString()
    {
        return getName() +" || "+ getChunk1()+" || "+ getChunk2()+" || "+ getChunk3()+" || "+ getChunk4();
    }
}
