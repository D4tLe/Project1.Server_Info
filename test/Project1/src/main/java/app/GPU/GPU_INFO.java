/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.GPU;

/**
 *
 * @author leunaut
 */
import oshi.hardware.*;
import oshi.SystemInfo;

public class GPU_INFO {
    private SystemInfo sysInfo;
    private GraphicsCard card;
    
    public String getName() {
        return card.getName();
    }
    
    
}
