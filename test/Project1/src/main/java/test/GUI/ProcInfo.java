package test.GUI;

import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

/**
 *
 * @author not-z3r0
 */
public class ProcInfo {
    private static List<OSProcess> processes;
    private static ArrayList<Object[]> procInfo;
    
    public static ArrayList<Object[]> getProcessInfo() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        processes = os.getProcesses();
        
        procInfo = new ArrayList<>();
        for (OSProcess proc: processes) {
            Object[] row = new Object[5];
            row[0] = proc.getName();
            row[1] = proc.getProcessID();
            row[2] = proc.getState();
            long totalMem = si.getHardware().getMemory().getTotal();
            row[3] = 100d * proc.getResidentSetSize() / totalMem;
            row[4] = proc.getBitness();
            procInfo.add(row);
        }
        
        return procInfo;
    }
}