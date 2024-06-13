/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author leunaut
 */
public class Users {
    String Users, CPU, Memory, Disk;
    public Users(String Users, String CPU, String Memory, String Disk) {
        this.Users = Users;
        this.CPU = CPU;
        this.Memory = Memory;
        this.Disk = Disk;
    }

    public String getUsers() {
        return Users;
    }

    public void setUsers(String Users) {
        this.Users = Users;
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
