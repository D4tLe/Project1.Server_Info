package GUI;

import GUI.Service;
import GUI.Service;
import GUI.ServiceInfo;
import GUI.ServiceInfo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import javafx.fxml.Initializable;
import oshi.software.os.OSProcess.*;

public class ServiceTableController implements Initializable {

    @FXML
    private TableColumn<Service, String> Service_Status;

    @FXML
    private TableColumn<Service, String> Service_Name;

    @FXML
    private TableView<Service> Service_Table_View;

    @FXML
    private TableColumn<Service, Integer> Service_PID;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceInfo svInfo = new ServiceInfo();
        ObservableList <Service> list = FXCollections.observableArrayList(svInfo.getServiceInfo());
        Service_Name.setCellValueFactory(new PropertyValueFactory <Service, String>("Name"));
        Service_Status.setCellValueFactory(new PropertyValueFactory <Service, String>("Status"));
        Service_PID.setCellValueFactory(new PropertyValueFactory <Service, Integer>("PID"));
        
        Service_Table_View.setItems(list);
    }

}