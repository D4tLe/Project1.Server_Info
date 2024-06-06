package GUI;

import oshi.*;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;

import com.sun.jna.Platform;

import java.util.*;

import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.tuples.Pair;

import oshi.driver.windows.perfmon.ProcessInformation.HandleCountProperty;
import oshi.hardware.CentralProcessor.ProcessorCache;
import oshi.hardware.GlobalMemory;

public class MEMORY_INFO {
    private SystemInfo sysInfo;
    private OperatingSystem OS;
    private GlobalMemory RAM;
    
    public MEMORY_INFO() {
        this.sysInfo = new SystemInfo(); 
        this.RAM = sysInfo.getHardware().getMemory();
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