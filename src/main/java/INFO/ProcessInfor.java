package INFO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OSProcess.State;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessFiltering;

public class ProcessInfor {
    
    private SystemInfo si;
    private OperatingSystem os;
    private List <OSProcess> processes;
    private ArrayList <Processing> procInfo;
    private ArrayList<Processing> procInfoFiltered;

    public ProcessInfor() {
        this.si = new SystemInfo();
        this.os = si.getOperatingSystem();
    }
    
    public class DEScomparator implements Comparator<OSProcess> {
        @Override
        public int compare(OSProcess os1, OSProcess os2) {
            if (os1.getResidentSetSize() == os2.getResidentSetSize()) {
                return 0;
            }
            else if (os1.getResidentSetSize() < os2.getResidentSetSize()) {
                return 1;
            }
            else return -1;
        }
    }

    public ArrayList<Processing> getProcessInfo() {
        processes = os.getProcesses(ProcessFiltering.ALL_PROCESSES, OperatingSystem.ProcessSorting.CPU_DESC, os.getProcessCount());
        Collections.sort(processes, new DEScomparator());

        procInfo = new ArrayList<>();
        for (OSProcess proc : processes) {
            String Name = proc.getName();
            int ID = proc.getProcessID();
            String User = proc.getUser();
            State status = proc.getState();
            Double Mem = (double) proc.getResidentSetSize() / (1024 * 1024);
            String S_Mem = String.format("%.2f", Mem);
            int Archi = proc.getBitness();
            Processing tmp = new Processing(Name, ID, User, status, S_Mem, Archi);
            procInfo.add(tmp);
        }
        return procInfo;
    }
    
    public ArrayList<Processing> getProcessInfoFiltered(String str) {
        processes = os.getProcesses();
        procInfoFiltered = new ArrayList<>();
        for (OSProcess proc : processes) {
            String Name = proc.getName();
            int ID = proc.getProcessID();
            String User = proc.getUser();
            State status = proc.getState();
            Double Mem = (double) proc.getResidentSetSize() / (1024 * 1024);
            String S_Mem = String.format("%.2f", Mem);
            int Archi = proc.getBitness();
            if (Name.contains(str) || ("" + ID).contains(str) || User.contains(str)) {
                Processing tmp = new Processing(Name, ID, User, status, S_Mem, Archi);
                procInfoFiltered.add(tmp);
            }
        }
        return procInfoFiltered;
    }
}