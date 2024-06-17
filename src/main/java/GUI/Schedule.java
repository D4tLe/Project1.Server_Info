/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author leunaut
 */
public class Schedule {
    private String Name, Des, Pub, Command, Time;
    public Schedule(String Name, String Des, String Pub, String Command, String Time) {
        this.Name = Name;
        this.Des = Des;
        this.Pub = Pub;
        this.Command = Command;
        this.Time = Time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String Des) {
        this.Des = Des;
    }

    public String getPub() {
        return Pub;
    }

    public void setPub(String Pub) {
        this.Pub = Pub;
    }

    public String getCommand() {
        return Command;
    }

    public void setCommand(String Command) {
        this.Command = Command;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
    
}
