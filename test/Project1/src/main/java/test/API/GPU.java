/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test.API;


import oshi.hardware.*;
import oshi.SystemInfo;
/**
 *
 * @author leunaut
 */
public class GPU {
    public static void main(String[] args) {
        SystemInfo info = new SystemInfo();
        for (GraphicsCard card : info.getHardware().getGraphicsCards()) {
            System.out.println("GPU's name: " + card.getName());
            System.out.println("GPU's vendor: " + card.getVendor());
            System.out.println("GPU's VRAM: " + card.getVRam());
            System.out.println("GPU's ID number " + card.getDeviceId());
            System.out.println("GPU's version information " + card.getVersionInfo());
        }
    }
}
