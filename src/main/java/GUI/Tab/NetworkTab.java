package GUI.Tab;

import GUI.Pane.NetworkRealTimeChartPane;
import GUI.NETWORK_INFO;
import GUI.Pane.RealTimeChartPane;
import java.util.Arrays;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import oshi.util.FormatUtil;

public class NetworkTab extends PerformanceTab {

    private NETWORK_INFO network;
    private RealTimeChartPane NetworkChartPane;
    private VBox dynamicSpecsPane = new VBox();
    private VBox staticsSpecsPane = new VBox();

    public NetworkTab(NETWORK_INFO network) {
        super();
        this.network = network;
    }

    public void config() {
        this.NetworkChartPane = new NetworkRealTimeChartPane(network, new LineChart(new NumberAxis(60, 0, 10), new NumberAxis()));
        this.NetworkChartPane.setInterval(getInterval());
        this.NetworkChartPane.configChart();

        this.dynamicSpecsPane.setSpacing(10);
        this.dynamicSpecsPane.setPrefSize(300, 300);

        network.updateAttributes();
        Label sendSpeed = new Label("Send speed: " + network.getSendSpeed());
        Label recvSpeed = new Label("Receive peed: " + network.getRecvSpeed());

        this.dynamicSpecsPane.getChildren().addAll(sendSpeed, recvSpeed);
        this.dynamicSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        });

        this.staticsSpecsPane.setSpacing(10);
        this.staticsSpecsPane.setPrefSize(300, 300);

        Label adapter = new Label("Adapter name: " + network.getName());
        Label SSID = new Label("SSID: ");
        Label DNSName = new Label("DNS name: ");
        Label connectionType = new Label("Connection type: " + network.getConnectionType());
        Label ipv4 = new Label("IPv4 address: " + Arrays.toString(network.getIpv4()));
        Label ipv6 = new Label("IPv6 address: " + Arrays.toString(network.getIpv6()));

        this.staticsSpecsPane.getChildren().addAll(adapter, SSID, DNSName, connectionType, ipv4, ipv6);
        this.staticsSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", 16));
        });

        HBox box = new HBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(dynamicSpecsPane, staticsSpecsPane);

        this.getContentBox().getChildren().addAll(NetworkChartPane, box);
    }

    public void updateData() {
        Label sendSpeed = (Label) this.dynamicSpecsPane.getChildren().get(0);
        Label recvSpeed = (Label) this.dynamicSpecsPane.getChildren().get(1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(getInterval()), event -> {
            network.updateAttributes();
            sendSpeed.setText("Send speed: " + FormatUtil.formatBytes(network.getSendSpeed()) + "/s");
            recvSpeed.setText("Send speed: " + FormatUtil.formatBytes(network.getRecvSpeed()) + "/s");

            this.NetworkChartPane.updateData();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
