/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.DISK;

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
import oshi.hardware.HWDiskStore;

public class DISK_INFO {
    private SystemInfo sysInfo;
    private OperatingSystem OS;
    private HWDiskStore DISK;
    private long prevReadBytes;
    private long prevWriteBytes;
    
    public void refreshSpeed() {
        this.prevReadBytes = DISK.getReadBytes();
        this.prevWriteBytes = DISK.getWriteBytes();
    }
    
    public String getDiskName() {
        return DISK.getModel();
    }
    
    public  String getDiskSize() {
        return FormatUtil.formatBytes(DISK.getSize());
    }
    
    public String getDiskReadSpeed() {
        refreshSpeed();
        return FormatUtil.formatBytes(DISK.getReadBytes() - prevReadBytes);
    }
    
    public String getDiskWriteSpeed() {
        refreshSpeed();
        return FormatUtil.formatBytes(DISK.getWriteBytes() - prevWriteBytes);
    }
}
