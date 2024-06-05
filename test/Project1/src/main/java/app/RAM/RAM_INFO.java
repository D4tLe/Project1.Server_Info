/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.RAM;

/**
 *
 * @author leunaut
 */

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

public class RAM_INFO {
    private SystemInfo sysInfo;
    private OperatingSystem OS;
    private GlobalMemory RAM;
    
    public RAM_INFO() {
        this.sysInfo = new SystemInfo(); 
        this.RAM = sysInfo.getHardware().getMemory();
    }
    
    public double getMemoryUsage() {
        return (100d - (100d * RAM.getAvailable() / RAM.getTotal())); // MB
    }
    
    public double getSwapUsage() {
        return (100d * RAM.getVirtualMemory().getSwapUsed() / RAM.getVirtualMemory().getSwapTotal());
    }
    
    public String getMemoryInUsed() {
        return "" + (RAM.getTotal() - RAM.getAvailable()) / (1024 * 1024);
    }
    
    public String getMemoryAvailable() {
        return "" + (RAM.getAvailable() / (1024 * 1024));
    }
    
    public String getSwapUsed() {
        return "" + RAM.getVirtualMemory().getSwapUsed() / (1024 * 1024);
    }
    
    public String getNonSwapUsed() {
        return "" + (RAM.getVirtualMemory().getSwapTotal() - RAM.getVirtualMemory().getSwapUsed()) / (1024 * 1024);
    }
}
