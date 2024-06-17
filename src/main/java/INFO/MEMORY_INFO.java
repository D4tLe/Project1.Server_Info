package INFO;

import oshi.*;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OperatingSystem;

public class MEMORY_INFO {
    private SystemInfo sysInfo;
    private OperatingSystem OS;
    private GlobalMemory RAM;
    
    public MEMORY_INFO() {
        this.sysInfo = new SystemInfo(); 
        this.RAM = sysInfo.getHardware().getMemory();
    }
    
    public long getTotalMemory() {
        return RAM.getTotal();
    }
    
    public double getMemoryUsage() {
        return (100d - (100d * RAM.getAvailable() / RAM.getTotal()));
    }
    
    public double getSwapUsage() {
        return (100d * RAM.getVirtualMemory().getSwapUsed() / RAM.getVirtualMemory().getSwapTotal());
    }
    
    public long getMemoryInUsed() {
        return RAM.getTotal() - RAM.getAvailable();
    }
    
    public long getMemoryAvailable() {
        return RAM.getAvailable();
    }
    
    public long getSwapUsed() {
        return RAM.getVirtualMemory().getSwapUsed();
    }
    
    public long getNonSwapUsed() {
        return RAM.getVirtualMemory().getSwapTotal() - RAM.getVirtualMemory().getSwapUsed();
    }
}
