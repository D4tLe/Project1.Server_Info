package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import oshi.software.os.OSProcess;

public class StartUpController implements Initializable {

    @FXML
    private TableColumn<StartUp, String> StartUp_Des;
    
    @FXML
    private TableColumn<StartUp, String> StartUp_Pub;

    @FXML
    private TableColumn<StartUp, String> StartUp_Name;
    
    @FXML
    private TableColumn<StartUp, String> StartUp_Cmd;
    
    @FXML
    private TableView <StartUp> StartUp_Table_View;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Get_StartUp_App st = new Get_StartUp_App();
        ObservableList <StartUp> list = FXCollections.observableArrayList(st.getStartUpInfo());    
        StartUp_Name.setCellValueFactory(new PropertyValueFactory <StartUp, String>("Name"));
        StartUp_Des.setCellValueFactory(new PropertyValueFactory <StartUp, String>("Des"));
        StartUp_Pub.setCellValueFactory(new PropertyValueFactory <StartUp, String>("Pub"));
        StartUp_Cmd.setCellValueFactory(new PropertyValueFactory <StartUp, String>("Command"));
        
        StartUp_Table_View.setItems(list);
    }

}