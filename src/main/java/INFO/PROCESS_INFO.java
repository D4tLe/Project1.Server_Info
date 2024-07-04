package INFO;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OSProcess.State;

public class PROCESS_INFO {
    public static int LOGICAL_PROCESSOR_COUNT = new SystemInfo().getHardware().getProcessor().getLogicalProcessorCount();
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
        this.CPUUsage = Double.parseDouble(String.format("%.1f", 100d * osProc.getProcessCpuLoadBetweenTicks(osProc) / LOGICAL_PROCESSOR_COUNT));
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
}