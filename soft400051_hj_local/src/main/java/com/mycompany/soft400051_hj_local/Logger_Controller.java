package com.mycompany.soft400051_hj_local;
 
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logger_Controller {
    protected static Logger logger = null;
    public static Logger log_createfile() {
        Date date = Calendar.getInstance().getTime();          
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(date);  
        System.out.println(strDate);

        Handler[] handlers = null;
        FileHandler handler;
        logger = Logger.getLogger("com.mycompany.soft400051_hj_local");
        handlers = logger.getHandlers();
        try {
            boolean append = true;
            if(handlers.length > 0)
            {
                handler = (FileHandler) handlers[0];
            }
            else{
                //handler = new FileHandler("default_log.log",append);
                handler = new FileHandler("C:\\Users\\harsh\\Documents\\NetBeansProjects\\soft40051_hj_group\\soft400051_hj_local\\Logs\\"+strDate+".log",append);       
            }
            //FileHandler handler = new FileHandler("default_log.log",append);
            //if(handlers.length > 0)
            //{
                //logger.addHandler(handlers[0]);
            //}
            //else{
            if(handlers.length == 0){
                logger.addHandler(handler);
            }
            //}
        } catch (IOException | SecurityException ex) {
            Logger.getLogger(Logger_Controller.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return logger;
    }
    
    public static void log_info(String Message)
    {       
        logger = Logger_Controller.log_createfile();
        logger.info(Message);
    }
}