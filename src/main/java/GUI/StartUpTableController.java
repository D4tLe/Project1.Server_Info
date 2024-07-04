package GUI;

import INFO.STARTUP_INFO;
import com.sun.jna.Platform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

public class StartUpTableController {

    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());
    private static STARTUP_INFO selectedItems;
    private ObservableList<STARTUP_INFO> startupList = FXCollections.observableArrayList(new ArrayList<>());
    
    @FXML
    private TableColumn<STARTUP_INFO, String> StartUp_Des;
    
    @FXML
    private TableColumn<STARTUP_INFO, String> StartUp_Pub;

    @FXML
    private TableColumn<STARTUP_INFO, String> StartUp_Name;
    
    @FXML
    private TableColumn<STARTUP_INFO, String> StartUp_Cmd;
    
    @FXML
    private TableView <STARTUP_INFO> StartUp_Table_View;
    
    public void configTable() {  
        StartUp_Name.setCellValueFactory(new PropertyValueFactory <>("Name"));
        StartUp_Des.setCellValueFactory(new PropertyValueFactory <>("Des"));
        StartUp_Pub.setCellValueFactory(new PropertyValueFactory <>("Pub"));
        StartUp_Cmd.setCellValueFactory(new PropertyValueFactory <>("Command"));
    }
    
    public void initialize() {
        configTable();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(3000), event -> {
            selectedItems = StartUp_Table_View.getSelectionModel().getSelectedItem();

            startupList = FXCollections.observableArrayList(this.getStartUpInfo());

            SortedList<STARTUP_INFO> sortedData = new SortedList<>(startupList);
            sortedData.setComparator(StartUp_Table_View.getComparator());
            sortedData.comparatorProperty().bind(StartUp_Table_View.comparatorProperty());
            
            StartUp_Table_View.getItems().setAll(sortedData);

            if (selectedItems != null) {
                for (STARTUP_INFO startup : startupList) {
                    if (startup.getName().equals(selectedItems.getName())) {
                        StartUp_Table_View.getSelectionModel().select(startup);
                        selectedItems = startup;
                        break;
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    
    private ArrayList<STARTUP_INFO> getStartUpInfo() {
        
        ArrayList<STARTUP_INFO> res = new ArrayList<>();

        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS)) {

            String command = "D:\\Autoruns\\autorunsc";

            try {
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line, Name = "", Pub = "", Des = "", Cmd = "";
                line = reader.readLine();
                int cnt = 1;
                while (line != null) {
                    if (checkLine(line) && line != null) {
                        line = reader.readLine();
                        cnt = 0;
                        while (!checkLine(line) && line != null) {
                            String tmp = "";
                            for (int i = 0; i < 14; ++i) {
                                if (line == null) {
                                    break;
                                }
                                if (cnt == 0) {
                                    if (i % 2 != 0) {
                                        tmp = line.trim();
                                    } else {
                                        tmp = "";
                                    }
                                } else {
                                    if (i % 2 == 0) {
                                        tmp = line.trim();
                                    } else {
                                        tmp = "";
                                    }
                                }
                                line = reader.readLine();
                                if (cnt == 0) {
                                    if (i == 1) {
                                        Name = tmp;
                                    } else if (i == 5) {
                                        Des = tmp;
                                    } else if (i == 7) {
                                        Pub = tmp;
                                    }
                                    else if (i == 11) {
                                        Cmd = tmp;
                                    }
                                } else {
                                    if (i == 0) {
                                        Name = tmp;
                                    } else if (i == 4) {
                                        Des = tmp;
                                    } else if (i == 6) {
                                        Pub = tmp;
                                    }
                                    else if (i == 10) {
                                        Cmd = tmp;
                                    }
                                }
                            }
                            res.add(new STARTUP_INFO(Name, Des, Pub, Cmd));
                            if (line == null) {
                                break;
                            }
                            while (line.trim() == "") {
                                line = reader.readLine();
                                cnt = 1;
                                if (line == null) {
                                    break;
                                }
                            }
                        }
                    } else {
                        line = reader.readLine();
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            parseAutostartDirectory(new File("/etc/xdg/autostart"), res);

            String userHome = System.getProperty("user.home");
            parseAutostartDirectory(new File(userHome + "/.config/autostart"), res);
        }
        return res;
    }
    
    private static boolean checkLine(String line) {
        if (line == null) {
            return false;
        }
        String tmp = line.trim();
        if (tmp.length() == 0) {
            return false;
        }
        if (tmp.charAt(0) == 'H') {
            return true;
        }
        return false;
    }

    private static void parseAutostartDirectory(File dir, ArrayList<STARTUP_INFO> list) {
        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles((d, name) -> name.endsWith(".desktop"));
            if (files != null) {
                for (File file : files) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String name = null;
                        String command = null;
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Name=")) {
                                name = line.substring(5);
                            } else if (line.startsWith("Exec=")) {
                                command = line.substring(5);
                            }
                        }
                        if (name != null && command != null) {
                            list.add(new STARTUP_INFO(name, command));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}