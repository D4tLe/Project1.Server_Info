/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.NetWork;

import oshi.*;

import com.sun.jna.Platform;

import java.util.List;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.NetworkIF.IfOperStatus;
import oshi.software.os.OperatingSystem;
/**
 *
 * @author leunaut
 */
public class NETWORK_INFO {
    private NetworkIF net;
    
    public String getName() {
        return net.getDisplayName();
    }
    
    public String getStatus() {
        return net.getIfOperStatus().name();  // UP or DOWN
    }
    
    public String getConnectionType() {
        return net.getIfAlias();
    }
    
    public String[] getIpv4() {
        return net.getIPv4addr();
    }
    
    public String[] getIpv6() {
        return net.getIPv6addr();
    }
    
    public long getPacketSend() {
        return net.getPacketsSent();
    }
    
    public long getPacketReceive() {
        return net.getPacketsRecv();
    }
    
    public long getByteSend() {
        return net.getBytesSent();
    }
    
    public long getByteReceive() {
        return net.getBytesRecv();
    }
}
