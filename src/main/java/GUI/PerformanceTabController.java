package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

public class PerformanceTabController {
    
    private ArrayList<Tab> DiskTabs = new ArrayList<>();
    private ArrayList<Tab> NetworkTabs = new ArrayList<>();

    @FXML
    private TabPane tabPane;

    public void initialize() throws IOException {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        
        List<HWDiskStore> diskList = hal.getDiskStores();
        
        for (int i = 0; i < diskList.size(); i++) {
            
            DiskTabController controller = new DiskTabController(diskList.get(i), i + 1);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DiskTab.fxml"));
            loader.setController(controller);
            
            VBox diskTabContent = loader.load();
            
            Tab newTab = new Tab();
            newTab.setContent(diskTabContent);
            newTab.setText("Disk " + (i + 1));
            DiskTabs.add(newTab);
        }
        
        for (Tab diskTab: DiskTabs) {
            diskTab.setClosable(false);
            tabPane.getTabs().add(diskTab);
        }
        
        List<NetworkIF> networkList = hal.getNetworkIFs();
        for (int i = 0; i < networkList.size(); i++) {
            
            NetworkIF network = networkList.get(i);
            
            NetworkTabController controller = new NetworkTabController(network);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NetworkTab.fxml"));
            loader.setController(controller);
            
            VBox networkTabContent = loader.load();
            
            Tab newTab = new Tab();
            newTab.setContent(networkTabContent);
            if (network.getIfOperStatus().name().equals("UP")) {
                if (network.getName().toLowerCase().contains("ethernet")) {
                    newTab.setText("Ethernet");
                } else {
                    newTab.setText("Wi-fi");
                }
                
                NetworkTabs.add(newTab);
            }
        }
        
        for (Tab networkTab: NetworkTabs) {
            networkTab.setClosable(false);
            tabPane.getTabs().add(networkTab);
        }
    }    
    
}
