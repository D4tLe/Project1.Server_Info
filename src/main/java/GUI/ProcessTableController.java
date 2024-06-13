package GUI;

import INFO.ProcessInfor;
import INFO.Processing;
import INFO.Processing;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.collections.*;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import oshi.software.os.OSProcess.*;

public class ProcessTableController implements Initializable {

    @FXML
    private TableColumn<Processing, Integer> Proc_Table_PID;

    @FXML
    private TableColumn<Processing, State> Proc_Table_Status;

    @FXML
    private TableColumn<Processing, String> Proc_Table_Memory;

    @FXML
    private TableView<Processing> Table_View;

    @FXML
    private TableColumn<Processing, String> Proc_Table_Name;

    @FXML
    private TableColumn<Processing, Integer> Proc_Table_Architect;

    //@Override
    public void UpdateTable() {
       ProcessInfor proc = new ProcessInfor();
       ObservableList <Processing> list = FXCollections.observableArrayList(proc.getProcessInfo());

       Proc_Table_Name.setCellValueFactory(new PropertyValueFactory <Processing, String>("Name"));
       Proc_Table_Status.setCellValueFactory(new PropertyValueFactory <Processing, State>("Status"));
       Proc_Table_PID.setCellValueFactory(new PropertyValueFactory <Processing, Integer>("PID"));
       Proc_Table_Memory.setCellValueFactory(new PropertyValueFactory <Processing, String>("Memory"));
       Proc_Table_Architect.setCellValueFactory(new PropertyValueFactory <Processing, Integer>("Architect"));

       Table_View.setItems(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            UpdateTable();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
