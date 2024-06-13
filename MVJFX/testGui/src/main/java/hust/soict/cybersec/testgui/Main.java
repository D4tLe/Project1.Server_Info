/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hust.soict.cybersec.testgui;

/**
 *
 * @author admin
 */

import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Service.fxml"));
        //Group root = new Group();
        Scene scene = new Scene(root);
        stage.setTitle("Server Management");
        
        stage.setScene(scene);
        stage.show();   
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
