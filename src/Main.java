/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hust.soict.cybersec.mavenjfx;

/**
 *
 * @author admin
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.stage.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
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
