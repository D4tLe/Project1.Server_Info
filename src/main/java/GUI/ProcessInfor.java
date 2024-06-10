package GUI;

import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OSProcess.State;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessFiltering;
import oshi.util.FormatUtil;

public class ProcessInfor {
    private static List <OSProcess> processes;
    private static ArrayList <Processing> procInfo; 
    
    private static String convertMB(long num) {
        double p = (double) num / (1024 * 1024);
        String res = String.format("%.2f", p);
        res = res + " MB";
        return res;
    }

    public static ArrayList <Processing> getProcessInfo() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        processes = os.getProcesses(ProcessFiltering.ALL_PROCESSES, OperatingSystem.ProcessSorting.CPU_DESC, os.getProcessCount());

        procInfo = new ArrayList<>();
        for (OSProcess proc : processes) {
            String Name = proc.getName();
            int ID = proc.getProcessID();
            State status = proc.getState();
            long total = si.getHardware().getMemory().getTotal();
            Double Mem = (double) proc.getResidentSetSize() / (1024 * 1024);
            int Archi = proc.getBitness();
            Processing tmp = new Processing(Name, ID, status, Mem, Archi);
            procInfo.add(tmp);
        }
        return procInfo;
    }
}