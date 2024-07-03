package GUI;

import INFO.NETWORK_INFO;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import oshi.hardware.NetworkIF;
import oshi.util.FormatUtil;

public class NetworkTabController {

    private NETWORK_INFO networkInfo;
    private Series<Number, Number> sendSpeedSeries, recvSpeedSeries;
    private double interval;

    @FXML
    private Label type, name, adapter, ipv4, ipv6;
    @FXML
    private LineChart speedChart;
    @FXML
    private Label sendSpeed;
    @FXML
    private Label receiveSpeed;

    public NetworkTabController() {

    }

    public NetworkTabController(NetworkIF network) {
        networkInfo = new NETWORK_INFO(network);
        sendSpeedSeries = new Series<>();
        sendSpeedSeries.setName("Send speed");
        recvSpeedSeries = new Series<>();
        recvSpeedSeries.setName("Receive speed");
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getInterval() {
        return this.interval;
    }

    public void initialize() {
        type.setText(networkInfo.getDisplayName().toLowerCase().contains("ethernet") ? "Ethernet" : "Wifi");
        name.setText(networkInfo.getDisplayName());
        adapter.setText("Adapter name: " + networkInfo.getName());
        ipv4.setText("IPv4: " + networkInfo.getIpv4());
        ipv6.setText("IPv6: " + networkInfo.getIpv6());
        
        speedChart.getData().addAll(sendSpeedSeries, recvSpeedSeries);
        speedChart.getXAxis().setLabel("Time (seconds)");
        speedChart.getYAxis().setLabel("Speed");
        NumberAxis yAxis = (NumberAxis) speedChart.getYAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                long bytes = object.longValue();
                return FormatUtil.formatBytesDecimal(bytes) + "/s";
            }
        });
        speedChart.setAnimated(false);

    }

    public void updateSpecs() {
        sendSpeed.setText(FormatUtil.formatBytesDecimal(networkInfo.getSendSpeed()) + "/s");
        receiveSpeed.setText(FormatUtil.formatBytesDecimal(networkInfo.getRecvSpeed()) + "/s");
    }

    public void updateChart() {
        long send = networkInfo.getSendSpeed();
        long recv = networkInfo.getRecvSpeed();

        sendSpeedSeries.getData().add(new XYChart.Data<>(0, send));
        recvSpeedSeries.getData().add(new XYChart.Data<>(0, recv));

        sendSpeedSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        recvSpeedSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        if (sendSpeedSeries.getData().size() > 60 * 1000d / getInterval() + 1) {
            sendSpeedSeries.getData().remove(0);
            recvSpeedSeries.getData().remove(0);
        }

        networkInfo.updateAttributes();
    }

    public void updateTab() {
        updateSpecs();
        updateChart();
    }
}
