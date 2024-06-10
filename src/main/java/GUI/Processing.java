package GUI;

import oshi.software.os.OSProcess.*;
public class Processing {
    String Name;
    State Status;
    int PID, Architect;
    Double Memory;
    public Processing(String Name, int PID, State Status, Double Memory, int Architect) {
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

    public Double getMemory() {
        return Memory;
    }

    public void setMemory(Double Memory) {
        this.Memory = Memory;
    }
    
}