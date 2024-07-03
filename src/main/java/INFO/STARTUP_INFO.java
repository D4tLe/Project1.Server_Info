
package INFO;

public class STARTUP_INFO {
    private String Name, Des, Pub, Command;
    public STARTUP_INFO(String Name, String Des, String Pub) {
        this.Name = Name;
        this.Des = Des;
        this.Pub = Pub;
    }
    
    public STARTUP_INFO(String Name, String Command) {
        this.Name = Name;
        this.Command = Command;
    }
    
    public String getCommand() {
        return Command;
    }

    public void setCommand(String Command) {
        this.Command = Command;
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
    
}