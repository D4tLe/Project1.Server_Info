package GUI.Tab;

import java.lang.reflect.*;
import java.util.Arrays;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import oshi.software.os.OSProcess.State;

public class ProcessTab extends Tab {
    private TableView<OSProcess> processTable = new TableView<>();
    
    public ProcessTab() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        
        ObservableList<OSProcess> process = FXCollections.observableArrayList(os.getProcesses());
        
        String[] columnNames = new String[] {"Name", "PID", "Path", "UserID", "GroupID", "Status", "Parent ID", "Thread", "Priority",
                                                "Virtual Size", "Resident Size", "Kernel Time", "User Time", "Start Time", "Up Time",
                                                "Bytes read", "Bytes Write", "Minor Fault", "Major Fault", "Context Switches"};
        
        Class c = process.get(0).getClass();
        
        Field[] fields = c.getFields();
        System.out.println(Arrays.toString(fields));
        
        TableColumn<OSProcess, String> processNames = new TableColumn<OSProcess, String>("Name");
        TableColumn<OSProcess, Integer> processID = new TableColumn<OSProcess, Integer>("PID");
        TableColumn<OSProcess, String> processPath = new TableColumn<OSProcess, String>("Path");
        TableColumn<OSProcess, String> processUserID = new TableColumn<OSProcess, String>("UserID");
        TableColumn<OSProcess, String> processGroupID = new TableColumn<OSProcess, String>("GroupID");
        TableColumn<OSProcess, State> processState = new TableColumn<OSProcess, State>("Status");
        TableColumn<OSProcess, Integer> processParentID = new TableColumn<OSProcess, Integer>("Parent ID");
        
        processID.setCellValueFactory(new PropertyValueFactory<OSProcess, Integer>("processID"));
        
        processTable.getColumns().addAll(processNames, processID);
        processTable.setItems(process);
        this.setContent(processTable);
        this.setText("Processes");
    }
}
