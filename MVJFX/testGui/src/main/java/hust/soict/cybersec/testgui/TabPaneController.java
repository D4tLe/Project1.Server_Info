/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hust.soict.cybersec.testgui;

import hust.soict.cybersec.testgui.*;
import hust.soict.cybersec.testgui.ProcessInfor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.*;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import oshi.software.os.OSProcess.*;

public class TabPaneController implements Initializable {

    @FXML
    private TableColumn <Processing, Integer> Proc_Table_PID;

    @FXML
    private TableColumn<Processing, State> Proc_Table_Status;

    @FXML
    private TableColumn<Processing, Double> Proc_Table_Memory;

    @FXML
    private TableView<Processing> Table_View;

    @FXML
    private TableColumn<Processing, String> Proc_Table_Name;
    
    @FXML
    private TableColumn<Processing, Integer> Proc_Table_Architect;
        
    //@Override
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ProcessInfor proc = new ProcessInfor();
        ObservableList <Processing> list = FXCollections.observableArrayList(proc.getProcessInfo());
        
       Proc_Table_Name.setCellValueFactory(new PropertyValueFactory <Processing, String>("Proc_Table_Name"));
       Proc_Table_Status.setCellValueFactory(new PropertyValueFactory <Processing, State>("Proc_Table_Status"));
       Proc_Table_PID.setCellValueFactory(new PropertyValueFactory <Processing, Integer>("Proc_Table_PID"));
       Proc_Table_Memory.setCellValueFactory(new PropertyValueFactory <Processing, Double>("Proc_Table_Memory"));
       Proc_Table_Architect.setCellValueFactory(new PropertyValueFactory <Processing, Integer>("Proc_Table_Architect"));
       
       Table_View.setItems(list);
    }
    
}

