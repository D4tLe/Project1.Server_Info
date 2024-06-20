/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package INFO;

import com.sun.jna.Platform;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import oshi.PlatformEnum;


/**
 *
 * @author leunaut
 */
public class Get_StartUp_App {

    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());
    private static ArrayList<StartUp> res = new ArrayList<>();

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

    public ArrayList<StartUp> getStartUpInfo() {

        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS)) {

            String command = "D:\\Autoruns\\autorunsc";

            try {
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line, Name = "", Pub = "", Des = "", Command = "";
                line = reader.readLine();
                int cnt = 1;
                while (line != null) {
                    if (checkLine(line)) {
                        line = reader.readLine();
                        cnt = 0;
                        while (!checkLine(line)) {
                            String tmp;
                            for (int i = 0; i < 14; ++i) {
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
                                    switch (i) {
                                        case 1:
                                            Name = tmp;
                                            break;
                                        case 5:
                                            Des = tmp;
                                            break;
                                        case 7:
                                            Pub = tmp;
                                            break;
                                        case 11:
                                            Command = tmp;
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    switch (i) {
                                        case 0:
                                            Name = tmp;
                                            break;
                                        case 4:
                                            Des = tmp;
                                            break;
                                        case 6:
                                            Pub = tmp;
                                            break;
                                        case 2:
                                            Command = tmp;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                            res.add(new StartUp(Name, Des, Pub, Command));
                            if (line == null) {
                                break;
                            }
                            while (line.trim().isEmpty()) {
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
            parseAutostartDirectory(new File("/etc/xdg/autostart"));

            String userHome = System.getProperty("user.home");
            parseAutostartDirectory(new File(userHome + "/.config/autostart"));
        }
        return res;
    }

    private static void parseAutostartDirectory(File dir) {
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
                            res.add(new StartUp(name, command));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
