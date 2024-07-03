package test.API;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oshi.*;
import oshi.hardware.HardwareAbstractionLayer;

import oshi.util.FormatUtil;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;

public class Disk {
    private static final Logger logger = LoggerFactory.getLogger(Disk.class);
    public static void main(String[] args) {

        SystemInfo si = new SystemInfo();
        
        HardwareAbstractionLayer hal = si.getHardware();
        List<HWDiskStore> hwDiskStoreList = hal.getDiskStores();
        for (HWDiskStore disk: hwDiskStoreList)
        {
            System.out.println("Disk name: " + disk.getName());
            System.out.println("Model: " + disk.getModel());
            System.out.println("Size: " + FormatUtil.formatBytes(disk.getSize()));
            long prevReadBytes = disk.getReadBytes();
            long prevWriteBytes = disk.getWriteBytes();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                Thread.currentThread().interrupt();
                logger.error("Error occurred: ", e1);
            }
            disk.updateAttributes();
            System.out.println("Read speed in the last second: " + FormatUtil.formatBytes(disk.getReadBytes() - prevReadBytes));
            System.out.println("Write speed in the last second: " + FormatUtil.formatBytes(disk.getWriteBytes() - prevWriteBytes));
            List<HWPartition> hwPartitionList = disk.getPartitions();
            for (HWPartition partition: hwPartitionList)
            {
                System.out.println("");
                System.out.println(space(4) + "Directory: " + partition.getMountPoint());
                System.out.println(space(4) + "Size: " + FormatUtil.formatBytes(partition.getSize()));
                System.out.println(space(4) + "Type: " + partition.getType().replace(":", ""));
            }
            System.out.println("");
        }
    }

    public final static String space(int cnt)
    {
        return " ".repeat(cnt);
    }
}