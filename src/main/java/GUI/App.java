package GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        // Set the scene with the loaded root
        Scene scene = new Scene(root);

        // Set the title and the scene to the stage
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenWidth = (double) screenSize.getWidth();
        double screenHeight = (double) screenSize.getHeight();

        System.out.println("Screen width: " + screenWidth);
        System.out.println("Screen height: " + screenHeight);
        
        primaryStage.setHeight(screenHeight * 2/3);
        primaryStage.setWidth(screenWidth * 17/23);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
