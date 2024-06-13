package test.API;

import oshi.*;

import java.util.List;

import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.software.os.OSProcess;

public class ProcessInfoTest {

    public static void main(String[] args) {
        System.out.println("Checking Processes");
        
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        List <OSProcess> pList = os.getProcesses();
        for (OSProcess process: pList)
        {
            System.out.println("Process ID: " + process.getProcessID());
            System.out.println("Process name: "+ process.getName());
            if (process.getParentProcessID() != 0)
                System.out.println("PPID: "+ process.getParentProcessID());
            System.out.println("Status: " + process.getState().toString());
            if (!process.getUser().equals("unknown"))
                System.out.println("Username: " + process.getUser());
            if (!process.getGroup().equals("None") && !process.getGroup().equals("unknown"))
                System.out.println("Group: " + process.getGroup());
            System.out.println("Cumulative: " + String.format("%.1f", 100d * process.getProcessCpuLoadCumulative()) + "%");
            System.out.println("VSZ: " + FormatUtil.formatBytes(process.getVirtualSize()));
            System.out.println("RSS: " + FormatUtil.formatBytes(process.getResidentSetSize()));
            long totalMem = si.getHardware().getMemory().getTotal();
            System.out.println("Memory(%): " + String.format("%.1f", 100d * process.getResidentSetSize() / totalMem));
            System.out.println("Threads: " + process.getThreadCount());
            if (!process.getCommandLine().isEmpty())
                System.out.println("Command Line: " + process.getCommandLine());
            if (!process.getPath().isEmpty())
                System.out.println("Filesystem path: " + process.getPath());
            System.out.println("Archietexture: " + (process.getBitness()) + " bits");
            System.out.println("");
        }
    }
}
