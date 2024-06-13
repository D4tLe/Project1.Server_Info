/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hust.soict.cybersec.mavenjfx;

import oshi.SystemInfo;
import oshi.software.os.OSProcess.*;
import oshi.software.os.OperatingSystem;
/**
 *
 * @author leunaut
 */
public class Processing {
    String Name;
    State Status;
    int PID, Architect;
    double Memory;
    public Processing(String Name, int PID, State Status, double Memory, int Architect) {
        this.Name = Name;
        this.PID = PID;
        this.Status = Status;
        this.Memory = Memory;
        this.Architect = Architect;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public State getStatus() {
        return Status;
    }

    public void setStatus(State Status) {
        this.Status = Status;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getArchitect() {
        return Architect;
    }

    public void setArchitect(int Architect) {
        this.Architect = Architect;
    }

    public double getMemory() {
        return Memory;
    }

    public void setMemory(double Memory) {
        this.Memory = Memory;
    }
    
}
