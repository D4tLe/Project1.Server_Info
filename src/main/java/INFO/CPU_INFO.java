package INFO;

import oshi.*;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;

import java.util.*;

import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;

import oshi.hardware.CentralProcessor.ProcessorCache;

public class CPU_INFO {
    private CentralProcessor CPU;
    private SystemInfo sysInfo;
    private ProcessorIdentifier pIden;
    private OperatingSystem OS;
    
    public CPU_INFO() {
        this.sysInfo = new SystemInfo();
        HardwareAbstractionLayer hal = this.sysInfo.getHardware();
        this.CPU = hal.getProcessor();
        this.pIden = this.CPU.getProcessorIdentifier();
        this.OS = this.sysInfo.getOperatingSystem();
    }
    
    public String getName() {
        return pIden.getName();
    }
    
    public long getCurrentFreq() {
        long totalFreq = 0;
        
        for (long freq: CPU.getCurrentFreq()) {
            totalFreq += freq;
        }
        
        return totalFreq / CPU.getLogicalProcessorCount();
    }
    
    public String getUpTime() {
        return FormatUtil.formatElapsedSecs(OS.getSystemUptime());
    }
    
    public String getBaseSpeed() {
        return FormatUtil.formatHertz(pIden.getVendorFreq());
    }
    
    public int getSockets() {
        return CPU.getPhysicalPackageCount();
    }
    
    public int getCores() {
        return CPU.getPhysicalProcessorCount();
    }
    
    public int getLogicalProcessors() {
        return CPU.getLogicalProcessorCount();
    }
    
    public int getProcessesCount() {
        return OS.getProcessCount();
    }
    
    public int getThreadCount() {
        return OS.getThreadCount();
    }
    
    public List<ProcessorCache> getCache() {
        return CPU.getProcessorCaches();
    }
    
    public double getUtilization() {
        return CPU.getSystemCpuLoad(300);
    }
    
    public double[] getEachProcessorsLoad() {
        double[] pCpuLoads = CPU.getProcessorCpuLoad(300);
        return pCpuLoads;
    }
}