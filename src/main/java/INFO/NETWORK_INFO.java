package INFO;

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

    public String getDisplayName() {
        return network.getDisplayName();
    }
    
    public String getName() {
        return network.getName();
    }

    public String getStatus() {
        return network.getIfOperStatus().name();
    }

    public String getConnectionType() {
        return "" + network.getIfType();
    }

    public String getIpv4() {
        StringBuilder ipv4 = new StringBuilder();
        for (String part: network.getIPv4addr()) {
            ipv4.append(part);
        }
        return ipv4.toString();
    }

    public String getIpv6() {
        StringBuilder ipv6 = new StringBuilder();
        for (String part: network.getIPv6addr()) {
            ipv6.append(part).append("\n");
        }
        return ipv6.toString();
    }

    public long getSendSpeed() {
        try {
            return 1000 * (network.getBytesSent() - prevSentBytes) / (network.getTimeStamp() - prevTimeStamp);
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public long getRecvSpeed() {
        try {
            return 1000 * (network.getBytesRecv() - prevRecvBytes) / (network.getTimeStamp() - prevTimeStamp);
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    public void updateAttributes() {
        this.prevRecvBytes = network.getBytesRecv();
        this.prevSentBytes = network.getBytesSent();
        this.prevTimeStamp = network.getTimeStamp();
        network.updateAttributes();
    }
}
