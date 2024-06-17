package INFO;

import oshi.software.os.OSProcess.*;
/**
 *
 * @author leunaut
 */
public class Processing {
    private String Name, Memory, User, CPU;
    private State Status;
    private int PID, Architect;
    
    public Processing(String Name, int PID, String User, State Status, String Memory, int Architect) {
        this.Name = Name;
        this.PID = PID;
        this.User = User;
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

    public String getMemory() {
        return Memory;
    }

    public void setMemory(String Memory) {
        this.Memory = Memory;
    }

    public String getUser() {
        return User;
    }

    
    public void setUser(String User) {
        this.User = User;
    }
    
}