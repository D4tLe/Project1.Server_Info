package GUI;
import INFO.DISK_INFO;
import INFO.USER_INFO;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Advapi32Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;

public class UsersTableController {
    
    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());
    private static USER_INFO selectedItems;
    private ObservableList<USER_INFO> userList = FXCollections.observableArrayList(new ArrayList<>());

    @FXML
    private TableView<USER_INFO> Users_Table_View;

    @FXML
    private TableColumn<USER_INFO, String> Users_Memory;

    @FXML
    private TableColumn<USER_INFO, String> Users_Disk;

    @FXML
    private TableColumn<USER_INFO, String> Users;

    @FXML
    private TableColumn<USER_INFO, String> Users_CPU;
    
    public void configTable() {
        
        Users.setCellValueFactory(new PropertyValueFactory <>("Users"));
        Users_CPU.setCellValueFactory(new PropertyValueFactory <>("CPU"));
        Users_Memory.setCellValueFactory(new PropertyValueFactory <>("Memory"));
        Users_Disk.setCellValueFactory(new PropertyValueFactory <>("Disk"));
        
    }
    

    public void initialize() {
        configTable();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), event -> {
            selectedItems = Users_Table_View.getSelectionModel().getSelectedItem();

            userList = FXCollections.observableArrayList(this.getUsersInfo());

            SortedList<USER_INFO> sortedData = new SortedList<>(userList);
            sortedData.setComparator(Users_Table_View.getComparator());
            sortedData.comparatorProperty().bind(Users_Table_View.comparatorProperty());
            
            Users_Table_View.getItems().setAll(sortedData);

            if (selectedItems != null) {
                for (USER_INFO user : userList) {
                    if (user.getUsers().equals(selectedItems.getUsers())) {
                        Users_Table_View.getSelectionModel().select(user);
                        selectedItems = user;
                        break;
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public ArrayList<USER_INFO> getUsersInfo() {
        ArrayList<USER_INFO> res = new ArrayList<>();

        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS)) {
            String username;
            double CPU;
            SystemInfo si = new SystemInfo();
            HardwareAbstractionLayer hal = si.getHardware();
            GlobalMemory memory = hal.getMemory();

            username = Advapi32Util.getUserName();
            CentralProcessor processor = hal.getProcessor();

            CPU = processor.getSystemCpuLoad(300) * 100;

            String S_CPU = String.format("%.1f", CPU) + "%";

            double total = 0;
            for (HWDiskStore val : si.getHardware().getDiskStores()) {
                DISK_INFO disk = new DISK_INFO(val);
                total += disk.getDiskReadSpeed();
                total += disk.getDiskWriteSpeed();
            }

            double Disk = total;
            String S_Disk = String.format("%.1f", Disk) + "%";

            double Memory = (1 - ((double) memory.getAvailable() / (double) memory.getTotal())) * 100d;
            String S_Memory = String.format("%.1f", Memory) + "%";

            res.add(new USER_INFO(username, S_CPU, S_Memory, S_Disk));
        } else {
            try {
                ProcessBuilder psBuilder = new ProcessBuilder("ps", "-eo", "user,%cpu,%mem", "--no-headers");
                Process psProcess = psBuilder.start();

                ProcessBuilder awkBuilder = new ProcessBuilder("awk",
                        "{cpu[$1]+=$2; mem[$1]+=$3} END {for(user in cpu) printf \"%-20s %-10.2f %-10.2f\\n\", user, cpu[user], mem[user]}");
                Process awkProcess = awkBuilder.start();

                try (BufferedReader psOutput = new BufferedReader(new InputStreamReader(psProcess.getInputStream())); OutputStreamWriter awkInput = new OutputStreamWriter(awkProcess.getOutputStream())) {
                    String line;
                    while ((line = psOutput.readLine()) != null) {
                        awkInput.write(line + "\n");
                    }
                }

                BufferedReader awkOutput = new BufferedReader(new InputStreamReader(awkProcess.getInputStream()));
                String line;

                while ((line = awkOutput.readLine()) != null) {
                    String[] details = line.trim().split("\\s+");
                    String username = details[0];
                    String cpu = details[1];
                    String memory = details[2];

                    res.add(new USER_INFO(username, cpu, memory));
                }
            } catch (IOException e) {
            }

        }
        return res;
    }
}