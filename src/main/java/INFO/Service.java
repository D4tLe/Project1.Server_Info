package INFO;

public class Service {
    private String Name, Status;
    private int PID;
    public Service(String Name, String Status, int PID) {
        this.Name = Name;
        this.Status = Status;
        this.PID = PID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }
    
    
}