package com.mycompany.soft400051_hj_local;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
   
    public static void main(String[] args) {
        launch();
    }
    
    protected static Scene scene_login;
    
    @Override
    public void start(Stage stage) throws Exception {
        //scene = new Scene(loadFXML("primary"), 640, 480);
        //scene = new Scene(loadFXML("test"));
        
        Parent root = FXMLLoader.load(getClass().getResource("Button.fxml"));
        scene_login = new Scene(root);
        //stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene_login);
        stage.show();
    }

//    static void setRoot(String fxml) throws IOException {
//        scene1.setRoot(loadFXML(fxml));
//    }
//
//    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//        return fxmlLoader.load();
//    }

    

}