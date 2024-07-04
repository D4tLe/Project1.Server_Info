package INFO;

public class USER_INFO {

    String username, CPU, Memory, Disk;

    public USER_INFO(String username, String CPU, String Memory, String Disk) {
        this.username = username;
        this.CPU = CPU;
        this.Memory = Memory;
        this.Disk = Disk;
    }
    
    public USER_INFO(String username, String CPU, String Memory) {
        this.username = username;
        this.CPU = CPU;
        this.Memory = Memory;
    }

    public String getUsers() {
        return username;
    }

    public void setUsers(String Users) {
        this.username = Users;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public String getMemory() {
        return Memory;
    }

    public void setMemory(String Memory) {
        this.Memory = Memory;
    }

    public String getDisk() {
        return Disk;
    }

    public void setDisk(String Disk) {
        this.Disk = Disk;
    }

}
