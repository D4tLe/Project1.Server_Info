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
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

public class User_Controller implements Initializable {
    
    private static Users_Info us = new Users_Info();
    private static SystemInfo si = new SystemInfo();
    private static HardwareAbstractionLayer hal = si.getHardware();
    private static CentralProcessor processor = hal.getProcessor();
    private static long[] ticks = processor.getSystemCpuLoadTicks();
    private static long[] prevTicks = processor.getSystemCpuLoadTicks();

    @FXML
    private TableView<Users> Users_Table_View;

    @FXML
    private TableColumn<Users, String> Users_Memory;

    @FXML
    private TableColumn<Users, String> Users_Disk;

    @FXML
    private TableColumn<Users, String> Users;

    @FXML
    private TableColumn<Users, String> Users_CPU;
    
    public void Update() {
        ObservableList <Users> list = FXCollections.observableArrayList(us.get_Users_Info());
        
        Users.setCellValueFactory(new PropertyValueFactory <Users, String>("Users"));
        Users_CPU.setCellValueFactory(new PropertyValueFactory <Users, String>("CPU"));
        Users_Memory.setCellValueFactory(new PropertyValueFactory <Users, String>("Memory"));
        Users_Disk.setCellValueFactory(new PropertyValueFactory <Users, String>("Disk"));
        
        Users_Table_View.setItems(list);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            Update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


}
