package INFO;

import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;
import java.util.ArrayList;
import java.util.List;
import oshi.software.os.OSService;

public class ServiceInfo {
    private ArrayList <Service> serviceInfo;
    
    public ArrayList <Service> getServiceInfo() {
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