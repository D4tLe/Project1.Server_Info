package GUI;

import INFO.SERVICE_INFO;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.*;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.software.os.OSService;
import oshi.software.os.OperatingSystem;

public class ServiceTableController{

        private static SERVICE_INFO selectedItems;
    private ObservableList<SERVICE_INFO> serviceList = FXCollections.observableArrayList(new ArrayList<>());
    
    @FXML
    private TableColumn<SERVICE_INFO, String> Service_Status;

    @FXML
    private TableColumn<SERVICE_INFO, String> Service_Name;

    @FXML
    private TableView<SERVICE_INFO> Service_Table_View;

    @FXML
    private TableColumn<SERVICE_INFO, Integer> Service_PID;

    public void configTable() {
        Service_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Service_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        Service_PID.setCellValueFactory(new PropertyValueFactory<>("PID"));
    }
    
    public void initialize() {
        configTable();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), event -> {
            selectedItems = Service_Table_View.getSelectionModel().getSelectedItem();

            serviceList = FXCollections.observableArrayList(this.getServiceInfo());

            SortedList<SERVICE_INFO> sortedData = new SortedList<>(serviceList);
            sortedData.setComparator(Service_Table_View.getComparator());
            sortedData.comparatorProperty().bind(Service_Table_View.comparatorProperty());
            
            Service_Table_View.getItems().setAll(sortedData);

            if (selectedItems != null) {
                for (SERVICE_INFO service : serviceList) {
                    if (service.getName().equals(selectedItems.getName())) {
                        Service_Table_View.getSelectionModel().select(service);
                        selectedItems = service;
                        break;
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    
    public ArrayList <SERVICE_INFO> getServiceInfo() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        List<OSService> osServices = os.getServices();
        
        ArrayList<SERVICE_INFO> serviceInfo = new ArrayList<>();
        for (OSService osService: osServices) {
            String Name, Status;
            Name = osService.getName();
            Status = osService.getState().toString();
            int PID = osService.getProcessID();
            SERVICE_INFO tmp = new SERVICE_INFO(Name, Status, PID);
            serviceInfo.add(tmp);
        }
        return serviceInfo;
    }
}
