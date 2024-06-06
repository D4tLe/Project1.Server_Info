package GUI;

import GUI.Tab.CPUTab;
import GUI.CPU_INFO;
import GUI.CPU_INFO;
import GUI.DISK_INFO;
import GUI.DISK_INFO;
import GUI.Tab.DiskTab;
import GUI.Tab.MemoryTab;
import GUI.NETWORK_INFO;
import GUI.Tab.NetworkTab;
import GUI.MEMORY_INFO;
import GUI.MEMORY_INFO;
import GUI.NETWORK_INFO;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSService;
import oshi.software.os.OperatingSystem;

public class MainController {
    public MainController() {
        
    }
    
    @FXML
    private TabPane performanceTabPane;
    
    @FXML
    public void initialize() {
        CPU_INFO CPU = new CPU_INFO();
        
        CPUTab cpuTab = new CPUTab(CPU);
        cpuTab.setText("CPU");
        cpuTab.setInterval(800);
        cpuTab.config();
        cpuTab.updateData();
        
        performanceTabPane.getTabs().add(cpuTab);
        
        MEMORY_INFO memory = new MEMORY_INFO();
        
        MemoryTab memoryTab = new MemoryTab(memory);
        memoryTab.setText("Memory");
        memoryTab.setInterval(800);
        memoryTab.config();
        memoryTab.updateData();
        
        performanceTabPane.getTabs().add(memoryTab);
        
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        
        List<HWDiskStore> distList = hal.getDiskStores();
        
        for (int i = 0; i < distList.size(); i++) {
            
            DISK_INFO disk = new DISK_INFO(distList.get(i));
            
            DiskTab diskTab = new DiskTab(disk);
            diskTab.setText("DISK " + i);
            diskTab.setInterval(800);
            diskTab.config();
            diskTab.updateData();
            
            performanceTabPane.getTabs().add(diskTab);
        }
        
        List<NetworkIF> networkList = hal.getNetworkIFs();
        for (int i = 0; i < networkList.size(); i++) {
            NETWORK_INFO network = new NETWORK_INFO(networkList.get(i));
            NetworkTab networkTab = new NetworkTab(network);
            if (network.getStatus().equals("UP")) {
                if (network.getName().toLowerCase().contains("ethernet")) {
                    networkTab.setText("Ethernet " + i);
                } else {
                    networkTab.setText("Wi-fi");
                }
                
                networkTab.setInterval(800);
                networkTab.config();
                networkTab.updateData();
                performanceTabPane.getTabs().add(networkTab);
            }
            
            
        }
        
        OperatingSystem os = si.getOperatingSystem();
        List<OSService> osServices = os.getServices();
        System.out.println(osServices.size());
    }
}
