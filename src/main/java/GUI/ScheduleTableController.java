package GUI;

import INFO.Schedule;
import INFO.Schedule_Info;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduleTableController{

    @FXML
    private TableColumn<Schedule, String> Sch_Name;

    @FXML
    private TableColumn<Schedule, String> Sch_Time;

    @FXML
    private TableView<Schedule> Sch_Table;

    @FXML
    private TableColumn<Schedule, String> Sch_Des;

    @FXML
    private TableColumn<Schedule, String> Sch_Pub;

    @FXML
    private TableColumn<Schedule, String> Sch_Path;
    
    public void initialize() {
        Schedule_Info st = new Schedule_Info();
        ObservableList <Schedule> list = FXCollections.observableArrayList(st.getSchedule());    
        Sch_Name.setCellValueFactory(new PropertyValueFactory <>("Name"));
        Sch_Des.setCellValueFactory(new PropertyValueFactory <>("Des"));
        Sch_Pub.setCellValueFactory(new PropertyValueFactory <>("Pub"));
        Sch_Path.setCellValueFactory(new PropertyValueFactory <>("Command"));
        Sch_Time.setCellValueFactory(new PropertyValueFactory <>("Time"));
        
        Sch_Table.setItems(list);
    }

}