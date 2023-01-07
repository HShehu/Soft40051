package com.mycompany.soft400051_hj_local;

import com.mycompany.soft400051_hj_local.model.FileMethods.Operation;
import com.mycompany.soft400051_hj_local.model.UserFile;
import com.mycompany.soft400051_hj_local.model.UserFolder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javafx.scene.control.Alert;

/**
* @brief Creating a class dbconnection to control Database Connection and Query
* @brief All Queries related to User and File are mentioned in this file
* @brief Maintaing Flags and String to Control Flow 
*/

public class dbconnection {
    
    protected static String url_connection = null;
    protected static String table_name = null;
    
    public static Connection dbconnection(){
        System.out.println("Intializing Db connection parameter");
        Connection connection = null;
        try{            
            //Creating Connection to Database 
            connection = DriverManager.getConnection("jdbc:sqlite:hj.db");
            System.out.println("Connection to SQLite Database has been established...");  
        } 
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    
    public static List<String> listUsers(String Owner){
        String strStatement = """
                              SELECT email
                              FROM users
                              WHERE NOT email = ?
                              """;
        
        List<String> users = new ArrayList<>();
        try(Connection connection = dbconnection.dbconnection();){
            PreparedStatement sqlSelectUsers = connection.prepareStatement(strStatement);
            sqlSelectUsers.setString(1, Owner);
            ResultSet userResults = sqlSelectUsers.executeQuery();
            
            while(userResults.next())
            {
                users.add(userResults.getString("email"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
    public static List<UserFile> permaDeleteFile(){ 
        
        initDeletedTable();
        
        String selStatement = """
                            SELECT * 
                            FROM Deleted
                            WHERE (JULIANDAY('NOW') - DELETED_AT) >= 30  
                              """;
        
        List<UserFile> fileList = new ArrayList<>();
        try(Connection connection = dbconnection.dbconnection();){
            
            PreparedStatement sqlSelectFiles = connection.prepareStatement(selStatement);
            ResultSet userFiles = sqlSelectFiles.executeQuery();
           
            
            
            while(userFiles.next())
            {   
                UserFile userFile = new UserFile();
                
                    userFile.setName(userFiles.getString("FILENAME"));
                    userFile.setPath(userFiles.getString("FILEPATH"));
                    userFile.setChunk1(userFiles.getString("CHUNK1"));
                    userFile.setChunk2(userFiles.getString("CHUNK2"));
                    userFile.setChunk3(userFiles.getString("CHUNK3"));
                    userFile.setChunk4(userFiles.getString("CHUNK4"));
                
                fileList.add(userFile);
            }
        }catch(SQLException err){
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, err);
        }
        
        System.out.println("Gotten Deleted Items Done");
        return fileList;
        

    }
    public static void finalDeleteFile(){
        String delStatement = """
                            DELETE 
                            FROM Deleted
                            WHERE (JULIANDAY('NOW') - DELETED_AT) >= 30
                              """;
        
        try(Connection connection = dbconnection.dbconnection();){
            Statement statement = connection.createStatement();
            statement.executeUpdate(delStatement);
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public static Boolean checkFileExists(String fileName,String filePath,String Owner){
        Boolean exists = false;
            String selStatement = """
                                  SELECT *
                                  FROM Files
                                  WHERE FILENAME = ? AND FILEPATH = ? AND OWNER = ?
                                  """;
            
        try(Connection connection = dbconnection.dbconnection();){
            
            
            int ownerId = getOwnerId(Owner);
            
            PreparedStatement sqlSelectFiles = connection.prepareStatement(selStatement);
         
            sqlSelectFiles.setString(1, fileName);
            sqlSelectFiles.setString(2, filePath);
            sqlSelectFiles.setInt(3, ownerId);
 
            ResultSet userFiles = sqlSelectFiles.executeQuery();
            
            if(userFiles.next()){
                exists = true;
                
                Alert existsAlert = new Alert(Alert.AlertType.WARNING);
                existsAlert.contentTextProperty().setValue("File Name: " + fileName +" already exists\n At Path: " + filePath + "\nPlease change file name or location");
                existsAlert.showAndWait();
            }
            
        }catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
    }         
    public static Integer getOwnerId(String Owner){
         String selectOwnerId = """
                               SELECT id
                               FROM users
                               WHERE email = ?
                               """;
         int ownerId = 0;
                 
         try(Connection connection = dbconnection.dbconnection();){
            Logger_Controller.log_info("Function getOwnerId Started");
            
            PreparedStatement selectId = connection.prepareStatement(selectOwnerId);
            selectId.setString(1, Owner);
            
            ownerId = selectId.executeQuery().getInt("id");
            
         } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
         return ownerId;
    }
//    public static String getEmail(Integer userId)
//    {
//        String strStatement = """
//                              SELECT email
//                              FROM users
//                              WHERE id = ?
//                              """;
//        
//        String email = "";
//        
//        try(Connection connection = dbconnection.dbconnection();){
//            Logger_Controller.log_info("Function getEmail Started");
//            
//            PreparedStatement selectId = connection.prepareStatement(strStatement);
//            selectId.setInt(1, userId);
//            
//            email = selectId.executeQuery().getString("email");
//            
//         } catch (SQLException ex) {
//            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//         return email;
//    }
    public static void deleteFile(String Owner, UserFile userFile){
        
        String delStatement = """
                              DELETE
                              FROM Files
                              WHERE OWNER = ? AND FILEPATH = ? AND FILENAME = ? 
                              """;
        
        Boolean flag = insertDeletedTable(Owner,userFile); 
        
        if(flag == true){
            try(Connection connection = dbconnection.dbconnection();){
                PreparedStatement sqlDeleteFile = connection.prepareStatement(delStatement);
                sqlDeleteFile.setInt(1, getOwnerId(Owner));
                sqlDeleteFile.setString(2, userFile.getPath());
                sqlDeleteFile.setString(3, userFile.getName());
                sqlDeleteFile.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static Boolean restoreFile(String Owner,UserFile userFile)
    {
        Boolean restored = false;
        
        String delStatement = """
                      DELETE
                      FROM Deleted
                      WHERE FILEID = ?
                      """;
        
        List<String> chunks = Arrays.asList(
        userFile.getChunk1(),
        userFile.getChunk2(),
        userFile.getChunk3(),
        userFile.getChunk4()
        );
        
        if(filesInsert(userFile.getName(),Owner,userFile.getPath(),chunks) == true){
            restored = true;
            try(Connection connection = dbconnection.dbconnection();){
                PreparedStatement sqlDeleteFile = connection.prepareStatement(delStatement);
                sqlDeleteFile.setInt(1, userFile.getId());
                sqlDeleteFile.executeUpdate();
            }catch (SQLException ex) {
                Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return restored;
    }
    public static Boolean insertDeletedTable(String Owner, UserFile userFile){
        Boolean flag = false;
        
        String insertFileTable = """
                                 INSERT INTO Deleted(
                                    FILENAME,
                                    OWNER,
                                    FILEPATH,
                                    CHUNK1,
                                    CHUNK2,
                                    CHUNK3,
                                    CHUNK4,
                                    DELETED_AT
                                 )VALUES(?,?,?,?,?,?,?,JULIANDAY('NOW'))"""; 

                              
        initDeletedTable();
        try(Connection connection = dbconnection.dbconnection();){
            Logger_Controller.log_info("Function filesDeleted Started");
          
            int ownerId = getOwnerId(Owner);
            
            PreparedStatement insertFile = connection.prepareStatement(insertFileTable);
            insertFile.setString(1, userFile.getName());
            insertFile.setInt(2, ownerId);
            insertFile.setString(3, userFile.getPath());
            insertFile.setString(4, userFile.getChunk1());
            insertFile.setString(5, userFile.getChunk2());
            insertFile.setString(6, userFile.getChunk3());
            insertFile.setString(7, userFile.getChunk4());
            insertFile.executeUpdate();
            flag = true;
            Logger_Controller.log_info("Insert Query Execution Success for Deleted");
        }   catch (SQLException ex) {
                Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
                Logger_Controller.log_info("SQLException for Insert into Deleted Query");
            }
        
        return flag;
    }
    public static void initFilesTable(){
        String createFileTable = """
                                 CREATE TABLE IF NOT EXISTS Files(
                                    FILENAME string NOT NULL,
                                    FILEPATH string DEFAULT 'Home' NOT NULL,
                                    OWNER integer NOT NULL,
                                    CHUNK1 string ,
                                    CHUNK2 string ,
                                    CHUNK3 string ,
                                    CHUNK4 string ,
                                    SHAREDWITH integer,
                                    ISWRITABLE boolean DEFAULT false NOT NULL,
                                    CONSTRAINT FILEID PRIMARY KEY (FILENAME,FILEPATH,OWNER),
                                    FOREIGN KEY (OWNER) REFERENCES users(id),
                                    FOREIGN KEY (SHAREDWITH) REFERENCES users(id)
                                 )
                                 """;
        
        String createFolderTable = """
                                 CREATE TABLE IF NOT EXISTS Folders(
                                    FOLDERNAME string NOT NULL,
                                    FOLDERPATH string DEFAULT 'Home' NOT NULL,
                                    OWNER integer NOT NULL,
                                    CONSTRAINT FOLDERID PRIMARY KEY (FOLDERNAME,FOLDERPATH,OWNER),
                                    FOREIGN KEY (OWNER) REFERENCES users(id)
                                 )
                                 """;
        
        
        try(Connection connection = dbconnection.dbconnection();){
            Statement statement = connection.createStatement();
            statement.executeUpdate(createFileTable);
            
            statement.executeUpdate(createFolderTable);
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void initDeletedTable(){
        String createFileTable = """
                                CREATE TABLE IF NOT EXISTS Deleted(
                                    FILEID integer PRIMARY KEY AUTOINCREMENT,
                                    FILENAME string NOT NULL,
                                    FILEPATH string DEFAULT './' NOT NULL,
                                    ISFOLDER boolean DEFAULT false NOT NULL,
                                    OWNER integer NOT NULL,
                                    CHUNK1 string ,
                                    CHUNK2 string ,
                                    CHUNK3 string ,
                                    CHUNK4 string ,
                                    SHAREDWITH integer,
                                    DELETED_AT DATETIME,
                                    FOREIGN KEY (OWNER) REFERENCES users(id),
                                    FOREIGN KEY (SHAREDWITH) REFERENCES users(id)
                                )
                                 """;
        
        
        try(Connection connection = dbconnection.dbconnection();){
            Statement statement = connection.createStatement();
            statement.executeUpdate(createFileTable);
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<UserFile> listDirectory(String owner, String filePath){
        
        String strStatement = """
                              SELECT *
                              FROM Files
                              WHERE OWNER = ? AND FILEPATH = ? AND SHAREDWITH IS NULL
                              """;
        
        String delStatement = """
                              SELECT *
                              FROM Deleted
                              WHERE OWNER = ?
                              """;
        
        String shdStatement = """
                              SELECT *
                              FROM Files
                              WHERE SHAREDWITH = ? OR (OWNER = ? AND SHAREDWITH is NOT NUll)
                              """;
        
        
        
        List<UserFile> fileList = new ArrayList<>();
        try(Connection connection = dbconnection.dbconnection();){
            
            
            int ownerId = getOwnerId(owner);
            
            PreparedStatement sqlSelectFiles = null;
         
            switch (filePath) {
                case "Deleted" -> sqlSelectFiles = connection.prepareStatement(delStatement);
                case "Shared" -> {
                    sqlSelectFiles = connection.prepareStatement(shdStatement);
                    sqlSelectFiles.setInt(2, ownerId);
                }
                default -> {
                    sqlSelectFiles = connection.prepareStatement(strStatement);
                    sqlSelectFiles.setString(2, filePath);
                }
            }
            
            sqlSelectFiles.setInt(1, ownerId);
            ResultSet userFiles = sqlSelectFiles.executeQuery();
           
            
            
            while(userFiles.next())
            {   
                UserFile userFile = new UserFile();
               
                if(filePath.equals("Deleted")){
                    userFile.setId(userFiles.getInt("FILEID"));
                }
                if(filePath.equals("Shared"))
                {
                    userFile.setIsOwned(Boolean.FALSE);
                    if(ownerId == userFiles.getInt("OWNER"))
                    {
                        userFile.setIsOwned(Boolean.TRUE);
                    }
                    
                }
                userFile.setName(userFiles.getString("FILENAME"));
                userFile.setPath(userFiles.getString("FILEPATH"));
                userFile.setChunk1(userFiles.getString("CHUNK1"));                
                userFile.setChunk2(userFiles.getString("CHUNK2"));
                userFile.setChunk3(userFiles.getString("CHUNK3"));
                userFile.setChunk4(userFiles.getString("CHUNK4"));
                
                fileList.add(userFile);
            }
        }catch(SQLException err){
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, err);
        }
        
        System.out.println("Gotten User Items Done");
        return fileList;
    }
    public static List<UserFolder> listFolders(String Owner,String folderPath){
       String strStatement = """
                              SELECT *
                              FROM Folders
                              WHERE OWNER = ? AND FOLDERPATH = ?
                              """;
        
        List<UserFolder> folderList = new ArrayList<>();
        try(Connection connection = dbconnection.dbconnection();){
            
            
            int ownerId = getOwnerId(Owner);
            
            PreparedStatement sqlSelectFiles = connection.prepareStatement(strStatement);
            
            sqlSelectFiles.setString(2, folderPath);
            sqlSelectFiles.setInt(1, ownerId);
            
            ResultSet userFolders = sqlSelectFiles.executeQuery();
           
            while(userFolders.next())
            {   
                UserFolder userFolder = new UserFolder(userFolders.getString("FOLDERNAME"),userFolders.getString("FOLDERPATH"));
                folderList.add(userFolder);
            }
        }catch(SQLException err){
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, err);
        }
        
        System.out.println("Gotten User Folders Done");
        
        return folderList; 
    }
 
    
    public static void shareFile(UserFile userFile, Map<String,Boolean> newValue , String Owner)
    {
        //check if File is already shared ask to overwrite?
        String strStatement = """
                              SELECT SHAREDWITH
                              FROM Files
                              WHERE FILENAME = ? AND FILEPATH = ? AND OWNER = ?
                              """;
        
        String updStatement = """
                              UPDATE Files
                              SET SHAREDWITH = ?
                              WHERE FILENAME = ? AND FILEPATH = ? AND OWNER = ?
                              """;
        try(Connection connection = dbconnection.dbconnection();){
           
            PreparedStatement Shared = connection.prepareStatement(strStatement);
           
            Shared.setString(1,userFile.getName());
            Shared.setString(2,userFile.getPath());
            Shared.setInt(3,getOwnerId(Owner));
            ResultSet sharedResult = Shared.executeQuery();
            
            if(sharedResult.getInt("SHAREDWITH") != 0){
                Alert existsAlert = new Alert(Alert.AlertType.ERROR);
                existsAlert.contentTextProperty().setValue("File Name: " + userFile.getName() +" is already shared\nWith User: " +  newValue.keySet().toArray()[0].toString());
                existsAlert.show();
                return;
            }
            
            PreparedStatement updShared = connection.prepareStatement(updStatement);
            
            updShared.setInt(1, getOwnerId(newValue.keySet().toArray()[0].toString()) );
            updShared.setString(2,userFile.getName());
            updShared.setString(3,userFile.getPath());
            updShared.setInt(4,getOwnerId(Owner));
            updShared.executeUpdate();
            
         } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public static void filesUpdate(Operation operation,String newValue, UserFile userFile,String Owner){
        
         String op = "";
         boolean check = true;
         
         
         switch(operation){
             case MOVE -> { 
                 op = "FILEPATH";
                 check = checkFileExists(userFile.getName(),newValue,Owner);
            }
                 
             case RENAME -> {
                 op = "FILENAME";
                 check = checkFileExists(newValue,userFile.getPath(),Owner);
            }
         }
         
        String strStatement = String.format( """
                              UPDATE Files
                              SET %s = ?
                              WHERE FILENAME = ? AND FILEPATH = ? AND OWNER = ?
                              """,op);

        
        if( check == false){
            
         try(Connection connection = dbconnection.dbconnection();){
           
            int ownerId = getOwnerId(Owner);
           
            PreparedStatement updateFiles = connection.prepareStatement(strStatement);
           
            updateFiles.setString(1,newValue);
            updateFiles.setString(2,userFile.getName());
            updateFiles.setString(3,userFile.getPath());
            updateFiles.setInt(4,ownerId);
            updateFiles.executeUpdate();
            
         } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public static Boolean filesInsert(String fileName,String Owner,String filePath,List<String> chunks)
    {
        Boolean flag = false;
        
       
        String insertFileTable = """
                                 INSERT INTO Files(
                                    FILENAME,
                                    FILEPATH,
                                    OWNER,
                                    CHUNK1,
                                    CHUNK2,
                                    CHUNK3,
                                    CHUNK4
                                 )VALUES(?,?,?,?,?,?,?)"""; 

        if(checkFileExists(fileName,filePath,Owner) == false)
        {
            try(Connection connection = dbconnection.dbconnection();){
            Logger_Controller.log_info("Function filesInsert Started");
          
            int ownerId = getOwnerId(Owner);
            
            PreparedStatement insertFile = connection.prepareStatement(insertFileTable);
            insertFile.setString(1, fileName);
            insertFile.setString(2, filePath);
            insertFile.setInt(3, ownerId);
            insertFile.setString(4, chunks.get(0));
            insertFile.setString(5, chunks.get(1));
            insertFile.setString(6, chunks.get(2));
            insertFile.setString(7, chunks.get(3));
            insertFile.executeUpdate();
//            statement.executeUpdate(insertFileTable);
            //System.out.println("User Registered Successfully "+Name+" : "+Email);
            flag = true;
            Logger_Controller.log_info("Insert Query Execution Success for File");
        }   catch (SQLException ex) {
                Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
                Logger_Controller.log_info("SQLException for Registration Query");
            }
        }
  
        return flag;
    }
    // Data insertion for registration
    public static boolean data_insert(String Name, String password_hashed, String Email){
        Boolean flag = false;
        Connection connection = dbconnection.dbconnection();
        try{
            Logger_Controller.log_info("Function data_insert Started");
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table if not exists users (id integer primary key autoincrement, name string, password string,email string, loginflag integer DEFAULT 0, login_datetime text)");
            System.out.println(password_hashed);
            statement.executeUpdate("insert into users(name, password,email) VALUES('"+Name+"','"+password_hashed+"','"+Email+"')");
            //System.out.println("User Registered Successfully "+Name+" : "+Email);
            flag = true;
            Logger_Controller.log_info("Insert Query Execution Success for Registration");
        }   catch (SQLException ex) {
                Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
                Logger_Controller.log_info("SQLException for Registration Query");
            }
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("Connection Closing Error for Registration");
        }
        return flag;
    }
    
    //to check for login
    public static String data_login(String Name, String Password){
        String flag = "false";
        
        try(Connection connection = dbconnection.dbconnection();){
            Logger_Controller.log_info("Function data_login Started");
            Statement statement = connection.createStatement();
            ResultSet count = statement.executeQuery("Select count(*) from users where email like('"+Name+"') and password like('"+Password+"')");
            count.next();
            int count_row = count.getInt(1);
            System.out.println(count_row);
            if(count_row == 1)
            {
                ResultSet value = statement.executeQuery("Select * from users where email like('"+Name+"') and password like('"+Password+"')");
                value.next();
                int u_id = value.getInt("id");
                String name = value.getString("name");
                int login_flag = value.getInt("loginflag");
                
                if(login_flag == 0){
                    flag = "true";
                    System.out.println("User Found " +Name);
                    statement.executeUpdate("update users SET loginflag = 1, login_datetime = datetime('now', 'localtime') where id like ('"+u_id+"') ");
                    Logger_Controller.log_info("User Details Found - Flag set Timestamp Set "+Name);
                }
                else{
                    flag = "already logged in";
                    System.out.println("User Already Logged In " +Name);
                    Logger_Controller.log_info("User Already Logged in DB Checked "+Name);
                }                
            }
        }catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("SQLException from Login Query");
        }
//        try {
//            connection.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
//            Logger_Controller.log_info("Connection Closing error for Login");
//        }
        return flag;
    }
    
    //to check data during registration
    public static boolean data_exist(String Name,String Email){
        Boolean flag = false;
        Connection connection = dbconnection.dbconnection();
        try{
            Logger_Controller.log_info("Function data_exist Started");
            Statement statement = connection.createStatement();
            ResultSet count;
            int count_row = 0;
            try{
                count = statement.executeQuery("Select count(*) from users where email like('"+Email+"') ");
                count.next();
                count_row = count.getInt(1);
                System.out.println(count_row);
            }
            catch(SQLException e){
                System.out.println("User Table does not exists since its 1st  registration");
                Logger_Controller.log_info("User table Does not Exist since its the 1st Registrsation");
            }
            
            if(count_row>0)
            {
                flag = true;
                System.out.println("User Already Exist" +Email);
                Logger_Controller.log_info("User Already Exist from DB - data_exist");
            }
        }catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("SQLException Error for data_exist");
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("Connection Close Error for data_exist");
        }
        return flag;
    }
    
    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    
    public static String getSecurePassword(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String toHexString(byte[] hash)
    {
            BigInteger number = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(number.toString(16));
            while (hexString.length() < 64)
            {
                hexString.insert(0, '0');
            }
            return hexString.toString();
    }
    
    //to delete user
    public static String data_del(String Name, String Password, int del_status){
        String flag = "false";
        Connection connection = dbconnection.dbconnection();
        try{
            Logger_Controller.log_info("Function data_del Started");
            Statement statement = connection.createStatement();
            ResultSet count = statement.executeQuery("Select count(*) from users where email like('"+Name+"') and password like('"+Password+"')");
            count.next();
            int count_row = count.getInt(1);
            System.out.println(count_row);
            if(count_row == 1)
            {
                ResultSet value = statement.executeQuery("Select * from users where email like('"+Name+"') and password like('"+Password+"')");
                value.next();
                int u_id = value.getInt("id");
                String name = value.getString("name");
                int login_flag = value.getInt("loginflag");
                
                if(login_flag == 0){
                    flag = "true";
                    System.out.println("User Found " +Name);
                    Logger_Controller.log_info("User Account Found - For Delete");
                    if(del_status == 1){
                        statement.executeUpdate("Delete from users where id like ('"+u_id+"')");
                        Logger_Controller.log_info("User Account Deleted "+Name);
                    }
                }
            }
        }catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("SQLException Error for Accout Deletion");
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("Connection Close Error for Account Deletion");
        }
        return flag;
    }
    
    //to Logout
    public static String user_logout(String Name){
        String logout_flag = "false";
            Connection connection = dbconnection.dbconnection();
        try{
            Logger_Controller.log_info("Function user_logout Started");
            Statement statement = connection.createStatement();
            ResultSet count = statement.executeQuery("Select count(*) from users where email like('"+Name+"')");
            count.next();
            int count_row = count.getInt(1);
            System.out.println("Row Count Found "+count_row);
            if(count_row == 1)
            {
                ResultSet value = statement.executeQuery("Select * from users where email like('"+Name+"')");
                value.next();
                int u_id = value.getInt("id");
                int login_flag = value.getInt("loginflag");
                
                if(login_flag == 1){
                    logout_flag = "true";
                    System.out.println("User Found and Logging Out " +Name);
                    statement.executeUpdate("update users SET loginflag = 0, login_datetime = ' ' where id like ('"+u_id+"') ");
                    Logger_Controller.log_info("User Found and Unset Login and Timestamp");
                }
                else{
                    logout_flag = "already logged in";
                    System.out.println("User Already Logged In " +Name);
                    Logger_Controller.log_info("User ALready Logged In - Logout Checking");
                }                
            }
        }catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("SQLException Error for Logout ");
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("Connection Closing Error for Logout");
        }
        return logout_flag;
    }
    

}
