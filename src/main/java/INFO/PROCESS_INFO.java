package INFO;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import oshi.driver.windows.registry.ProcessPerformanceData;
import oshi.software.os.OSProcess;
import oshi.software.os.OSProcess.State;
import GUI.ProcessTableController;

public class PROCESS_INFO {
    private OSProcess osProc;
    private String name, path;
    private State status;
    private int PID, architect;
    private double CPUUsage;
    private double memoryUsage;
    
    public PROCESS_INFO() {
        
    }
    
    public PROCESS_INFO(OSProcess osProc) {
        this.osProc = osProc;
        this.name = osProc.getName();
        this.path = osProc.getPath();
        this.status = osProc.getState();
        this.PID = osProc.getProcessID();
        this.architect = osProc.getBitness();
        this.CPUUsage = Double.parseDouble(String.format("%.1f", 100d * osProc.getProcessCpuLoadBetweenTicks(osProc)));
        this.memoryUsage = Double.parseDouble(String.format("%.1f", (double) osProc.getResidentSetSize() / (1024 * 1024)));
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getPID() {
        return this.PID;
    }
    
    public State getStatus() {
        return this.status;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public double getCPUUsage() {
        return this.CPUUsage;
    }
    
    public double getMemoryUsage() {
        return this.memoryUsage;
    }
    
    public int getArchitect() {
        return this.architect;
    }
    
    public void updateAttributes() {
        Map<Integer, ProcessPerformanceData.PerfCounterBlock> pcb = ProcessPerformanceData.buildProcessMapFromRegistry(null);
        if (pcb.get(osProc.getProcessID()) != null) {
            osProc.updateAttributes();
            this.CPUUsage = Double.parseDouble(String.format("%.1f", 100d * osProc.getProcessCpuLoadBetweenTicks(osProc)));
            this.memoryUsage = Double.parseDouble(String.format("%.1f", (double) osProc.getResidentSetSize() / (1024 * 1024)));     
        }
        else ProcessTableController.pidList.remove(Integer.valueOf(osProc.getProcessID()));
        /*try {
            osProc.updateAttributes();
        } catch (Exception e) {
            
            System.out.println(osProc.getName() + " " + osProc.getState() + " " + e);
        }
        this.status = osProc.getState();
        
        DEBUG
        System.out.println(osProc.getName());
        Set<Integer> pids = Collections.singleton(osProc.getProcessID());
        for (int i : pids)
            System.out.println(i);
        Map<Integer, ProcessPerformanceData.PerfCounterBlock> pcb = ProcessPerformanceData.buildProcessMapFromRegistry(null);
        if (pcb.get(osProc.getProcessID()) == null) System.out.println("YES NULL");
        System.out.println("----------------------");
        //DEBUG*/
        
        
    }
}