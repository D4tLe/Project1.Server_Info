package INFO;

import oshi.*;
import oshi.hardware.GlobalMemory;

public class MEMORY_INFO {
    private SystemInfo sysInfo;
    private GlobalMemory memory;
    
    public MEMORY_INFO() {
        this.sysInfo = new SystemInfo(); 
        this.memory = sysInfo.getHardware().getMemory();
    }
    
    public long getTotalMemory() {
        return memory.getTotal();
    }
    
    public double getMemoryUsage() {
        return (100d - (100d * memory.getAvailable() / memory.getTotal()));
    }
    
    public double getSwapUsage() {
        return (100d * memory.getVirtualMemory().getSwapUsed() / memory.getVirtualMemory().getSwapTotal());
    }
    
    public long getMemoryInUsed() {
        return memory.getTotal() - memory.getAvailable();
    }
    
    public long getMemoryAvailable() {
        return memory.getAvailable();
    }
    
    public long getSwapUsed() {
        return memory.getVirtualMemory().getSwapUsed();
    }
    
    public long getNonSwapUsed() {
        return memory.getVirtualMemory().getSwapTotal() - memory.getVirtualMemory().getSwapUsed();
    }
}
