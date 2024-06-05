/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hust.soict.cybersec.testgui;

/**
 *
 * @author leunaut
 */
import oshi.SystemInfo;
import oshi.software.os.OSProcess.*;
import oshi.software.os.OperatingSystem;
import hust.soict.cybersec.testgui.*;
import java.util.ArrayList;
import java.util.List;
import oshi.software.os.OSService;

public class ServiceInfo {
    private static ArrayList <Service> serviceInfo;
    
    public static ArrayList <Service> getServiceInfo() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        List<OSService> osServices = os.getServices();
        
        serviceInfo = new ArrayList<>();
        for (OSService osService: osServices) {
            String Name, Status;
            Name = osService.getName();
            Status = osService.getState().toString();
            int PID = osService.getProcessID();
            Service tmp = new Service(Name, Status, PID);
            serviceInfo.add(tmp);
        }
        return serviceInfo;
    }
}
