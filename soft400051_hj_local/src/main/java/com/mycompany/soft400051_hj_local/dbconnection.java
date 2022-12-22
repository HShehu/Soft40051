package com.mycompany.soft400051_hj_local;

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
        Connection connection = dbconnection.dbconnection();
        try{
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
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(dbconnection.class.getName()).log(Level.SEVERE, null, ex);
            Logger_Controller.log_info("Connection Closing error for Login");
        }
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
    
    public static void main(String[] args) {
                        
    }
}