package INFO;

import INFO.DISK_INFO;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Advapi32Util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import oshi.PlatformEnum;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;

public class Users_Info {

    public static double CPU = 0;
    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());

    public ArrayList<Users> get_Users_Info() {
        ArrayList<Users> res = new ArrayList<>();

        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS)) {
            String username;
            SystemInfo si = new SystemInfo();
            HardwareAbstractionLayer hal = si.getHardware();
            GlobalMemory memory = hal.getMemory();

            username = Advapi32Util.getUserName();
            CentralProcessor processor = hal.getProcessor();

            CPU = processor.getSystemCpuLoad(300) * 100;

            String S_CPU = String.format("%.1f", CPU) + "%";

            double total = 0;
            for (HWDiskStore val : si.getHardware().getDiskStores()) {
                DISK_INFO disk = new DISK_INFO(val);
                total += disk.getDiskReadSpeed();
                total += disk.getDiskWriteSpeed();
            }

            double Disk = total;
            String S_Disk = String.format("%.1f", Disk) + "%";

            double Memory = (1 - ((double) memory.getAvailable() / (double) memory.getTotal())) * 100d;
            String S_Memory = String.format("%.1f", Memory) + "%";

            res.add(new Users(username, S_CPU, S_Memory, S_Disk));
        } else {
            try {
                ProcessBuilder psBuilder = new ProcessBuilder("ps", "-eo", "user,%cpu,%mem", "--no-headers");
                Process psProcess = psBuilder.start();

                ProcessBuilder awkBuilder = new ProcessBuilder("awk",
                        "{cpu[$1]+=$2; mem[$1]+=$3} END {for(user in cpu) printf \"%-20s %-10.2f %-10.2f\\n\", user, cpu[user], mem[user]}");
                Process awkProcess = awkBuilder.start();

                try (BufferedReader psOutput = new BufferedReader(new InputStreamReader(psProcess.getInputStream())); OutputStreamWriter awkInput = new OutputStreamWriter(awkProcess.getOutputStream())) {
                    String line;
                    while ((line = psOutput.readLine()) != null) {
                        awkInput.write(line + "\n");
                    }
                }

                BufferedReader awkOutput = new BufferedReader(new InputStreamReader(awkProcess.getInputStream()));
                String line;

                while ((line = awkOutput.readLine()) != null) {
                    String[] details = line.trim().split("\\s+");
                    String username = details[0];
                    String cpu = details[1];
                    String memory = details[2];

                    res.add(new Users(username, cpu, memory));
                }
            } catch (IOException e) {
            }

        }
        return res;
    }
}