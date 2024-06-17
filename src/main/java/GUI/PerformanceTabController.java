package GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

public class PerformanceTabController {

    private ArrayList<Tab> DiskTabs = new ArrayList<>();
    private ArrayList<Tab> NetworkTabs = new ArrayList<>();
    private ArrayList<DiskTabController> diskControllers = new ArrayList<>();
    private ArrayList<NetworkTabController> networkControllers = new ArrayList<>();

    @FXML
    private TabPane tabPane;

    public void initialize() throws IOException {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        CPUTabController CPUController = new CPUTabController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CPUTab.fxml"));
        loader.setController(CPUController);
        VBox CPUTabContent = loader.load();

        Tab newTab = new Tab();
        newTab.setContent(CPUTabContent);
        newTab.setText("CPU");
        newTab.setClosable(false);
        tabPane.getTabs().add(newTab);

        MemoryTabController memoryController = new MemoryTabController();
        loader = new FXMLLoader(getClass().getResource("MemoryTab.fxml"));
        loader.setController(memoryController);
        VBox memoryTabContent = loader.load();

        newTab = new Tab();
        newTab.setContent(memoryTabContent);
        newTab.setText("Memory");
        newTab.setClosable(false);
        tabPane.getTabs().add(newTab);

        List<HWDiskStore> diskList = hal.getDiskStores();

        for (int i = 0; i < diskList.size(); i++) {

            DiskTabController diskController = new DiskTabController(diskList.get(i), i + 1);
            diskControllers.add(diskController);

            loader = new FXMLLoader(getClass().getResource("DiskTab.fxml"));
            loader.setController(diskController);

            VBox diskTabContent = loader.load();

            newTab = new Tab();
            newTab.setContent(diskTabContent);
            newTab.setText("Disk " + (i + 1));
            DiskTabs.add(newTab);
        }

        for (Tab diskTab : DiskTabs) {
            diskTab.setClosable(false);
            tabPane.getTabs().add(diskTab);
        }

        List<NetworkIF> networkList = hal.getNetworkIFs();
        for (int i = 0; i < networkList.size(); i++) {

            NetworkIF network = networkList.get(i);

            NetworkTabController networkController = new NetworkTabController(network);
            networkControllers.add(networkController);

            loader = new FXMLLoader(getClass().getResource("NetworkTab.fxml"));
            loader.setController(networkController);

            VBox networkTabContent = loader.load();

            newTab = new Tab();
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

        for (Tab networkTab : NetworkTabs) {
            networkTab.setClosable(false);
            tabPane.getTabs().add(networkTab);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            CPUController.updateTab();
            memoryController.updateTab();
            
            for (DiskTabController C : diskControllers) {
                C.updateTab();
            }
            for (NetworkTabController C : networkControllers) {
                C.updateTab();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

}
