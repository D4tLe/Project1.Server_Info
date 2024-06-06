package GUI;

import java.util.ArrayList;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OSProcess.State;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

public class ProcessInfor {
    private static List <OSProcess> processes;
    private static ArrayList <Processing> procInfo;

    public static ArrayList <Processing> getProcessInfo() {
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        processes = os.getProcesses();

        procInfo = new ArrayList<>();
        for (OSProcess proc : processes) {
            String Name = proc.getName();
            int ID = proc.getProcessID();
            State status = proc.getState();
            long total = si.getHardware().getMemory().getTotal();
            String Mem = FormatUtil.formatBytes(proc.getResidentSetSize());
            int Archi = proc.getBitness();
            Processing tmp = new Processing(Name, ID, status, Mem, Archi);
            procInfo.add(tmp);
        }
        return procInfo;
    }
}