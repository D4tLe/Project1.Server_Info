package INFO;

import oshi.util.FormatUtil;
import oshi.hardware.HWDiskStore;

public class DISK_INFO {

    private HWDiskStore DISK;
    private long prevReadBytes;
    private long prevWriteBytes;
    private long prevTransferTime;
    private long prevTimeStamp;

    public DISK_INFO(HWDiskStore DISK) {
        this.DISK = DISK;
        this.prevReadBytes = DISK.getReadBytes();
        this.prevWriteBytes = DISK.getWriteBytes();
        this.prevTransferTime = DISK.getTransferTime();
        this.prevTimeStamp = DISK.getTimeStamp();
    }

    public String getDiskName() {
        return DISK.getModel();
    }

    public String getDiskSize() {
        return FormatUtil.formatBytes(DISK.getSize());
    }

    public long getDiskReadSpeed() {
        try {
            return 1000 * (DISK.getReadBytes() - prevReadBytes) / (DISK.getTransferTime() - prevTransferTime);
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public long getDiskWriteSpeed() {
        try {
            return 1000 * (DISK.getWriteBytes() - prevWriteBytes) / (DISK.getTransferTime() - prevTransferTime);
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public double getActiveTime() {
        try {
            return 100d * (DISK.getTransferTime() - prevTransferTime) / (DISK.getTimeStamp() - prevTimeStamp);
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public void updateAttributes() {
        this.prevReadBytes = DISK.getReadBytes();
        this.prevWriteBytes = DISK.getWriteBytes();
        this.prevTransferTime = DISK.getTransferTime();
        this.prevTimeStamp = DISK.getTimeStamp();
        DISK.updateAttributes();
    }
}
