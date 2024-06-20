package GUI;
import INFO.Users;
import INFO.Users_Info;
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

public class User_Controller implements Initializable {
    
    private Users_Info us;

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
        
        us = new Users_Info();
        ObservableList <Users> list = FXCollections.observableArrayList(us.get_Users_Info());
        
        Users.setCellValueFactory(new PropertyValueFactory <>("Users"));
        Users_CPU.setCellValueFactory(new PropertyValueFactory <>("CPU"));
        Users_Memory.setCellValueFactory(new PropertyValueFactory <>("Memory"));
        Users_Disk.setCellValueFactory(new PropertyValueFactory <>("Disk"));
        
        Users_Table_View.setItems(list);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), event -> {
            Update();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


}