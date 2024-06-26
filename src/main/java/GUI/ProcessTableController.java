package GUI;

import INFO.PROCESS_INFO;
import com.sun.javafx.scene.control.TableColumnComparatorBase.TableColumnComparator;
import com.sun.jna.Platform;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.collections.*;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OSProcess.State;
import oshi.software.os.OperatingSystem;

public class ProcessTableController implements Initializable {

    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());

    @FXML
    private TableColumn<PROCESS_INFO, Integer> Proc_Table_PID;

    @FXML
    private TableColumn<PROCESS_INFO, State> Proc_Table_Status;

    @FXML
    private TableColumn<PROCESS_INFO, Double> Proc_Table_Memory;

    @FXML
    private TableView<PROCESS_INFO> Table_View;

    @FXML
    private TableColumn<PROCESS_INFO, String> Proc_Table_Name;

    @FXML
    private TableColumn<PROCESS_INFO, String> Proc_Table_Path;

    @FXML
    private TableColumn<PROCESS_INFO, Double> Proc_Table_CPU;

    @FXML
    private TableColumn<PROCESS_INFO, Integer> Proc_Table_Architect;

    @FXML
    private Button endTaskBtn;

    @FXML
    private TextField filterTf;

    private static PROCESS_INFO selectedItems;
    //private ObservableList<PROCESS_INFO> procList = FXCollections.observableArrayList(new ArrayList<>());
    public static ArrayList<Integer> pidList = new ArrayList<>();
    
    

    public void configTable() {

        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        ObservableList<PROCESS_INFO> procList = FXCollections.observableArrayList(new ArrayList<>());
        
        for (OSProcess proc : os.getProcesses()) {
            procList.add(new PROCESS_INFO(proc));
            //pidList.add(proc.getProcessID());
        }

        Proc_Table_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Proc_Table_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        Proc_Table_PID.setCellValueFactory(new PropertyValueFactory<>("PID"));
        Proc_Table_Memory.setCellValueFactory(new PropertyValueFactory<>("memoryUsage"));
        Proc_Table_Architect.setCellValueFactory(new PropertyValueFactory<>("architect"));
        Proc_Table_CPU.setCellValueFactory(new PropertyValueFactory<>("CPUUsage"));
        Proc_Table_Path.setCellValueFactory(new PropertyValueFactory<>("path"));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configTable();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            selectedItems = Table_View.getSelectionModel().getSelectedItem();
            if (selectedItems != null) {
                System.out.println(selectedItems.getName());
            }

            SystemInfo si = new SystemInfo();
            OperatingSystem os = si.getOperatingSystem();
            ObservableList<PROCESS_INFO> procList = FXCollections.observableArrayList(new ArrayList<>());
            
            for (OSProcess proc : os.getProcesses()) {
                {
                    procList.add(new PROCESS_INFO(proc));
                    //pidList.add(proc.getProcessID());
                }
            }
            
            SortedList<PROCESS_INFO> sortedData = new SortedList<>(procList, def);
            Table_View.getItems().setAll(procList);
            /*Table_View.getItems().forEach(proc -> proc.updateAttributes());
            Table_View.refresh();*/

            if (selectedItems != null) {
            for (PROCESS_INFO proc : procList)
                if (proc.getPID() == selectedItems.getPID()) {
                    Table_View.getSelectionModel().select(proc);
                    selectedItems = proc;
                    break;
                }
            }
            
            TableColumnComparator c = (TableColumnComparator) Table_View.getComparator();
            //if (c != null) System.out.println(c.toString());

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        endTaskBtn.setOnAction(new EventHandler() {

            @Override
            public void handle(Event e) {
                PROCESS_INFO selectedItem = Table_View.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    try {
                        String killCommand;
                        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS)) {
                            killCommand = "Taskkill /F /PID " + selectedItem.getPID();
                        } else {
                            killCommand = "kill -9 " + selectedItem.getPID();
                        }

                        Runtime.getRuntime().exec(killCommand);

                        //procList.remove(selectedItem);
                        //pidList.remove(Integer.valueOf(selectedItem.getPID()));
                    } catch (IOException ex) {
                        Logger.getLogger(ProcessTableController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        /*FilteredList<PROCESS_INFO> filteredData = new FilteredList<>(procList, p -> true);

        filterTf.setPromptText("Filter");

        /*filterTf.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(proc -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseValue = newValue.toLowerCase();

                return (proc.getName().toLowerCase().contains(lowerCaseValue) || Integer.toString(proc.getPID()).contains(lowerCaseValue)
                        || proc.getPath().toLowerCase().contains(lowerCaseValue));
            });
        });

        SortedList<PROCESS_INFO> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(Table_View.comparatorProperty());

        Table_View.setItems(sortedData);*/
    }
}
