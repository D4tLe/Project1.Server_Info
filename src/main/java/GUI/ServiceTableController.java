package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;

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
        ObservableList<Service> list = FXCollections.observableArrayList(svInfo.getServiceInfo());
        Service_Name.setCellValueFactory(new PropertyValueFactory<Service, String>("Name"));
        Service_Status.setCellValueFactory(new PropertyValueFactory<Service, String>("Status"));
        Service_PID.setCellValueFactory(new PropertyValueFactory<Service, Integer>("PID"));

        Service_Table_View.setItems(list);
    }

}
