package test;

import oshi.*;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;

import com.sun.jna.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.tuples.Pair;

import oshi.driver.windows.perfmon.ProcessInformation.HandleCountProperty;

public class CPU {
    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());

    public static void main(String[] args) {

        SystemInfo si = new SystemInfo();
        
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        System.out.println("\nChecking CPU\n");
        CentralProcessor cProcessor = hal.getProcessor();
        ProcessorIdentifier pIden = cProcessor.getProcessorIdentifier();
        System.out.println("CPU Name: " + pIden.getName());
        System.out.println("Base speed: " + FormatUtil.formatHertz(pIden.getVendorFreq()));
        System.out.println(space(4) + "Sockets:            " + (cProcessor.getPhysicalPackageCount()));
        System.out.println(space(4) + "Cores:              " + (cProcessor.getPhysicalProcessorCount()));
        System.out.println(space(4) + "Logical processors: " + (cProcessor.getLogicalProcessorCount()));
        
        System.out.println("Processes: " + (os.getProcessCount()));
        System.out.println("Threads:   " + (os.getThreadCount()));

        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS))
        {
            Pair<List<String>, Map<HandleCountProperty, List<Long>>> hwdPair = oshi.driver.windows.perfmon.ProcessInformation.queryHandles();
            long handleCount = (hwdPair.getB().values().iterator().next()).get(0);
            System.out.println("Handles:   " + handleCount);
        }

        System.out.println("CPU usage in % in the last second: " + String.format("%.2f",cProcessor.getSystemCpuLoad(1000)*100) + "%");
        List<Double> pCpuLoads = new ArrayList<>();
        for (double pCpuLoad: cProcessor.getProcessorCpuLoad(1000))
        {
            pCpuLoads.add(pCpuLoad);
        }
        System.out.println("CPU usage in % of each processor in the last second:");
        for (int i = 0; i < pCpuLoads.size(); i++)
        {
            System.out.println(space(4) + "CPU " + (i) + ": " + String.format("%.2f",pCpuLoads.get(i)*100) + "%");
        }
    }

    public static final String space(int cnt)
    {
        return " ".repeat(cnt);
    }
}