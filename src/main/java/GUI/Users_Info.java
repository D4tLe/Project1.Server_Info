/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import com.sun.jna.platform.win32.Advapi32Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 *
 * @author leunaut
 */
public class Users_Info {
    public static double CPU = 0;
    
    public ArrayList <Users> get_Users_Info() {
        ArrayList <Users> res = new ArrayList<> ();
        String Users = "";
        
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        OSProcess myProc = os.getProcess(os.getProcessId());
        OSProcess p = os.getProcess(os.getProcessId());
        GlobalMemory memory = hal.getMemory();
 
        Users = Advapi32Util.getUserName();
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
        
        res.add(new Users(Users, S_CPU, S_Memory, S_Disk));
        return res;
    }
}
