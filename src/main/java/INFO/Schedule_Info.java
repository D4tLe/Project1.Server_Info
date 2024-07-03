package INFO;

import com.sun.jna.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import oshi.PlatformEnum;

public class Schedule_Info {

    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());
    private static ArrayList<Schedule> res = new ArrayList<>();

    private static int checkLine(String line) {
        if (line == null) {
            return 0;
        }
        String tmp = line.trim();
        if (tmp.length() == 0) {
            return 0;
        }
        if (tmp.charAt(0) == '\\') {
            return 1;
        }
        if (tmp.charAt(0) == '[') {
            return 2;
        }
        return 0;
    }

    public ArrayList<Schedule> getSchedule() {

        if (CURRENT_PLATFORM.equals(PlatformEnum.WINDOWS)) {
            String command = "D:\\Autoruns\\autorunsc -a t";

            try {
                Process process = Runtime.getRuntime().exec(command);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String line, Name = "", Pub = "", Des = "", Command = "", Time = "";

                line = reader.readLine();
                while (line != null) {
                    while (checkLine(line) == 0) {
                        line = reader.readLine();
                    }
                    int tmp = checkLine(line);
                    if (line == null) {
                        break;
                    }
                    Name = line;
                    ArrayList<String> list = new ArrayList<>();
                    list.add(line);
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    while (checkLine(line) == 0) {
                        list.add(line);
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                    }
                    if (tmp == 1) {
                        if (list.size() == 14) {
                            Name = list.get(0);
                            Des = list.get(4);
                            Pub = list.get(6);
                            Command = list.get(10);
                            Time = list.get(12);
                        } else if (list.size() == 12) {
                            Time = list.get(10);
                            Command = list.get(8);
                            Pub = list.get(6);
                            Des = list.get(4);
                            Name = list.get(0);
                        }
                      
                        res.add(new Schedule(Name, Des, Pub, Command, Time));
                    }
                }

                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Process process = Runtime.getRuntime().exec("crontab -l");
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().startsWith("#") && !line.trim().isEmpty()) {
                        String[] parts = line.split("\\s+", 6);
                        if (parts.length == 6) {
                            Schedule job = new Schedule();
                            job.setTime(parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3] + " " + parts[4]);
                            job.setCommand(parts[5]);
                            res.add(job);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return res;
    }
}
