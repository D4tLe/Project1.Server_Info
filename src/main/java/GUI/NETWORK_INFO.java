package GUI;

import oshi.hardware.NetworkIF;

public class NETWORK_INFO {

    private NetworkIF network;
    private long prevSentBytes, prevRecvBytes, prevTimeStamp;

    public NETWORK_INFO(NetworkIF network) {
        this.network = network;
        this.prevSentBytes = network.getBytesSent();
        this.prevRecvBytes = network.getBytesRecv();
        this.prevTimeStamp = network.getTimeStamp();
    }

    public String getName() {
        return network.getDisplayName();
    }

    public String getStatus() {
        return network.getIfOperStatus().name();
    }

    public String getConnectionType() {
        return network.getIfAlias();
    }

    public String[] getIpv4() {
        return network.getIPv4addr();
    }

    public String[] getIpv6() {
        return network.getIPv6addr();
    }

    public long getSendSpeed() {
        return 1000 * (network.getBytesSent() - prevSentBytes) / (network.getTimeStamp() - prevTimeStamp);
    }
    
    public long getRecvSpeed() {
        return 1000 * (network.getBytesRecv()- prevRecvBytes) / (network.getTimeStamp() - prevTimeStamp);
    }
    
    public void updateAttributes() {
        this.prevRecvBytes = network.getBytesRecv();
        this.prevSentBytes = network.getBytesSent();
        this.prevTimeStamp = network.getTimeStamp();
        network.updateAttributes();
    }
}
