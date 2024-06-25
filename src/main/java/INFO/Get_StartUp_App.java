/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package INFO;

import INFO.StartUp;
import com.sun.jna.platform.win32.Advapi32Util;
import static com.sun.jna.platform.win32.WinReg.HKEY_CURRENT_USER;
import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author leunaut
 */
public class Get_StartUp_App {
     private static ArrayList <StartUp> res = new ArrayList<>();
/*    private static Set <String> Set_StartUp = new HashSet <String> ();
    
    public static final String STARTUP_REGISTRY_1_E = "Software\\Microsoft\\Windows\\CurrentVersion\\Run\\";
    public static final String STARTUP_REGISTRY_2_E = "SOFTWARE\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\Run";
    
    public static final String STARTUP_REGISTRY_1_D = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer\\StartupApproved\\Run";
    
    //User = 0 -> Current. User = 1 -> Local
    private static ArrayList <String> getStartUp(String folder, int user) {
        Map <String, Object> appStartUp;
        if (user == 0) appStartUp = Advapi32Util.registryGetValues(HKEY_CURRENT_USER, folder);
        else appStartUp = Advapi32Util.registryGetValues(HKEY_LOCAL_MACHINE, folder);
        
        ArrayList <String> res = new ArrayList<>();
        for (String value: appStartUp.keySet()) {
            res.add(value);
        }
        return res;
    }
    
    private static void addApp(String folder, int user_val, String status) {
        ArrayList <String> App = getStartUp(folder, user_val);
        for (String value : App) {
            StartUp tmp = new StartUp(value, status);
            res.add(tmp);
        }
    }*/
    
     private static boolean checkLine(String line) {
        if (line == null) return false;
        String tmp = line.trim();
        if (tmp.length() == 0) return false;
        if (tmp.charAt(0) == 'H') return true;
        return false;
    }
     
    public ArrayList <StartUp> getStartUpInfo() {
     //public static void main(String[] args) {

    String command = "D:\\Autoruns\\autorunsc";
 
    try {
        Process process = Runtime.getRuntime().exec(command);
 
        BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()));
        String line, Name = "", Pub = "", Des = "";
        line = reader.readLine();
        int cnt = 1;
        while (line != null) {
            if (checkLine(line) && line != null) {
                line = reader.readLine();
                //System.out.println(cnt);
                cnt = 0;
                while (!checkLine(line) && line != null) {        
                    String tmp = "";
                    for (int i = 0; i < 14; ++i) {
                        if (line == null) break;
                        if (cnt == 0) {
                            if (i % 2 != 0) tmp = line.trim();
                            else tmp = "";
                        }
                        else {
                            if (i % 2 == 0) tmp = line.trim();
                            else tmp = "";
                        }
                        //System.out.println(line + "" + Integer.toString(i));
                        //System.out.println(line);
                        line = reader.readLine();
                        if (cnt == 0) {
                            if (i == 1) Name = tmp;
                            else if (i == 5) Des = tmp;
                            else if (i == 7) Pub = tmp;
                        }
                        else {
                            if (i == 0) Name = tmp;
                            else if (i == 4) Des = tmp;
                            else if (i == 6) Pub = tmp;
                        }
                    }
                    //System.out.println(Name + " " + Des + " " + Pub);
                    res.add(new StartUp(Name, Des, Pub));
                    if (line == null) break;
                    while (line.trim() == "") {
                        line = reader.readLine();
                        cnt = 1;
                        if (line == null) break;
                    }
                }
            }
            else line = reader.readLine();
        }
 
        reader.close();
 
    } catch (IOException e) {
        e.printStackTrace();
    }
    return res;
    } 
}
