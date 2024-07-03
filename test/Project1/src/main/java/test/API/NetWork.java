package test.API;

import oshi.*;

import com.sun.jna.Platform;

import java.util.List;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.NetworkIF.IfOperStatus;
import oshi.software.os.OperatingSystem;

public class NetWork { // NOSONAR squid:S5786

    private static final PlatformEnum CURRENT_PLATFORM = PlatformEnum.getValue(Platform.getOSType());

    /**
     * The main method, demonstrating use of classes.
     *
     * @param args the arguments (unused)
     */
    public static void main(String[] args) {

        SystemInfo si = new SystemInfo();

        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        System.out.println(CURRENT_PLATFORM.name());

        System.out.println("\nChecking Network interfaces...");

        List<NetworkIF> networkIFs = hal.getNetworkIFs();

        for (NetworkIF net: networkIFs)
        {   
            System.out.println("Interface index: " + net.getIndex());
            System.out.println(space(2) + "Interface name: " + net.getName());
            System.out.println(space(4) + "Interface display name: "+ net.getDisplayName());
            System.out.println(space(4) + "Interface alias: " + net.getIfAlias());
            System.out.println(space(8) + "Interface operation status: " + net.getIfOperStatus().name() + " (" + operStatusDesc(net.getIfOperStatus()) +")");
            if (CURRENT_PLATFORM.name().equals("WINDOWS"))
                System.out.println(space(8) + "NDIS Interface Type: " + net.getIfType());
            if (CURRENT_PLATFORM.name().equals("LINUX"))
                System.out.println(space(8) + "ARP Protocol: " + net.getIfType());
            System.out.println(space(4) + "Mac Address: " + net.getMacaddr());
            System.out.println(space(4) + "IPv4 Address: ");
            for (String ipv4: net.getIPv4addr())
            {
                System.out.println(space(8) + ipv4);
            }
            System.out.println(space(4) + "IPv6 Address: ");
            for (String ipv6: net.getIPv6addr())
            {
                System.out.println(space(8) + ipv6);
            }
            System.out.println(space(4) + "The interface Maximum Transmission Unit (MTU): " + net.getMTU());
            System.out.println(space(4) + "Traffic:");
                System.out.println(space(8) + "Received " + net.getPacketsRecv() + " packets/" + net.getBytesRecv() + " bytes (" + net.getInErrors() + " errors, " + net.getInDrops() + " drops) ");
                System.out.println(space(8) + "Transmitted " + net.getPacketsSent() + " packets/" + net.getBytesSent() + " bytes (" + net.getOutErrors() + " errors, " + net.getCollisions() + " collisions) ");
            System.out.println("");
        }

        System.out.println("Checking Network parameters...");
        
        System.out.println(space(4) + "Host name: " + os.getNetworkParams().getHostName());
        System.out.println(space(4) + "Domain name: " + os.getNetworkParams().getDomainName());
        System.out.println(space(4) + "DNS servers: ");
        for (String server : os.getNetworkParams().getDnsServers())
        {   
            System.out.println(space(8) + server);
        }
        System.out.println(space(4) + "IPv4 Gateway: " + os.getNetworkParams().getIpv4DefaultGateway());
        System.out.println(space(4) + "IPv6 Gateway: " + os.getNetworkParams().getIpv6DefaultGateway());

    }

    public final static String space(int cnt)
    {
        return " ".repeat(cnt);
    }

    public static final String DORMANT_STR = "The interface is not up, but is in a pending state, waiting for some external event";
    public static final String DOWN = "Down and not operational";
    public static final String LOWER_LAYER_DOWN = "Down due to state of lower-layer interface(s)";
    public static final String NOT_PRESENT = "Some component is missing";
    public static final String TESTING = "In some test mode";
    public static final String UNKNOWN = "The interface status is unknown";
    public static final String UP = "Up and operational";

    public final static String operStatusDesc(IfOperStatus ifOperStatus)
    {
        switch (ifOperStatus.name()) {
            case "UP":
                return UP;
            case "DOWN":
                return DOWN;
            case "DORMANT_STR":
                return DORMANT_STR;
            case "LOWER_LAYER_DOWN":
                return LOWER_LAYER_DOWN;            
            case "NOT_PRESENT":
                return NOT_PRESENT;
            case "TESTING":
                return TESTING;
            case "UNKNOWN":
                return UNKNOWN;
            default:
                throw new UnsupportedOperationException("Operation status not supported: " + ifOperStatus.name());
        }
    }
}