/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author leunaut
 */
public class Schedule_Info {
    private static ArrayList <Schedule> res = new ArrayList<>();
    
    private static int checkLine(String line) {
        if (line == null) return 0;
        String tmp = line.trim();
        if (tmp.length() == 0) return 0;
        if (tmp.charAt(0) == '\\') return 1;
        if (tmp.charAt(0) == '[') return 2;
        return 0;
    }
     
    public ArrayList <Schedule> getSchedule() {
    //public static void main(String[] args) {

    String command = "D:\\Autoruns\\autorunsc -a t";
 
    try {
        Process process = Runtime.getRuntime().exec(command);
 
        BufferedReader reader = new BufferedReader(
        new InputStreamReader(process.getInputStream()));
        String line, Name = "", Pub = "", Des = "", Command = "", Time = "";
        
        line = reader.readLine();
        while (line != null) {
            while (checkLine(line) == 0) line = reader.readLine();
            int tmp = checkLine(line);
            if (line == null) break;
            Name = line;
            ArrayList <String> list = new ArrayList<> ();
            list.add(line);
            line = reader.readLine();
            if (line == null) break;
            while (checkLine(line) == 0) {
                list.add(line);
                line = reader.readLine();
                if (line == null) break;
            }
            //System.out.println(String.format("%d", list.size()));
            /*if (list.size() == 14 || list.size() == 12) {
                for (String s : list) 
                    System.out.println(s);
            }*/
            if (tmp == 1) {
                if (list.size() == 14) {
                    Name = list.get(0);
                    Des = list.get(4);
                    Pub = list.get(6);
                    Command = list.get(10);
                    Time = list.get(12);
                }
                else if (list.size() == 12) {
                    Time = list.get(10);
                    Command = list.get(8);
                    Pub = list.get(6);
                    Des = list.get(4);
                    Name = list.get(0);
                }
                res.add(new Schedule(Name, Des, Pub, Command, Time));
                System.out.println(Name);
                System.out.println(Des);
                System.out.println(Pub);
                System.out.println(Command);
                System.out.println(Time);
            }
        }
 
        reader.close();
 
    } catch (IOException e) {
        e.printStackTrace();
    }
    return res;
    }
}