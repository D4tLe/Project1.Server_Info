package GUI;

import INFO.ProcessInfor;
import INFO.Processing;
import com.sun.jna.Platform;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.collections.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import oshi.PlatformEnum;
import oshi.software.os.OSProcess.*;

public class ProcessTableController implements Initializable {
    
    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());
    
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
    
    @FXML
    private Button endTaskBtn;
    
    @FXML
    private TextField filterTf;
    
    private static Processing selectedItems;

    //@Override
    public void UpdateTable() {
        ProcessInfor proc = new ProcessInfor();
        ObservableList<Processing> list = FXCollections.observableArrayList(proc.getProcessInfo());
        
        Proc_Table_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Proc_Table_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        Proc_Table_PID.setCellValueFactory(new PropertyValueFactory<>("PID"));
        Proc_Table_Memory.setCellValueFactory(new PropertyValueFactory<>("Memory"));
        Proc_Table_Architect.setCellValueFactory(new PropertyValueFactory<>("Architect"));
        
        Table_View.setItems(list);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UpdateTable();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), event -> {
            selectedItems = Table_View.getSelectionModel().getSelectedItem();
            if (selectedItems != null) {
                System.out.println(selectedItems.getName());
            }
            
            ProcessInfor proc = new ProcessInfor();
            ObservableList<Processing> list;
            if (filterTf.getText().equals("")) {
                list = FXCollections.observableArrayList(proc.getProcessInfo());
            } else {
                list = FXCollections.observableArrayList(proc.getProcessInfoFiltered(filterTf.getText()));
            }
            
            Table_View.getItems().setAll(list);
            
            if (list.contains(selectedItems)) {
                Table_View.getSelectionModel().select(selectedItems);
                Table_View.scrollTo(selectedItems);
            }
            
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        endTaskBtn.setOnAction(new EventHandler() {
            
            @Override
            public void handle(Event e) {
                Processing selectedItem = Table_View.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    try {
                        String killCommand;
                        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS)) {
                            killCommand = "Taskkill /F /PID " + selectedItem.getPID();
                        } else {
                            killCommand = "kill -9 " + selectedItem.getPID();
                        }
                        
                        Runtime.getRuntime().exec(killCommand);
                        
                        Table_View.getItems().remove(selectedItem);
                    } catch (IOException ex) {
                        Logger.getLogger(ProcessTableController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        filterTf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            showFilterProcesses(newValue);
        });
    }
    
    private void showFilterProcesses(String newValue) {
        ProcessInfor proc = new ProcessInfor();
        
        if (newValue.equals("")) {
            Table_View.setItems(FXCollections.observableArrayList(proc.getProcessInfo()));
        } else {
            Table_View.setItems(FXCollections.observableArrayList(proc.getProcessInfoFiltered(newValue)));
        }
    }
    
}
