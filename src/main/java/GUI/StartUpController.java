package GUI;

import INFO.Get_StartUp_App;
import INFO.StartUp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        StartUp_Name.setCellValueFactory(new PropertyValueFactory <>("Name"));
        StartUp_Des.setCellValueFactory(new PropertyValueFactory <>("Des"));
        StartUp_Pub.setCellValueFactory(new PropertyValueFactory <>("Pub"));
        StartUp_Cmd.setCellValueFactory(new PropertyValueFactory <>("Command"));
        
        StartUp_Table_View.setItems(list);
    }

}