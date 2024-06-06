package GUI.Pane;

import GUI.NETWORK_INFO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.NumberAxis.DefaultFormatter;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.util.Duration;
import oshi.hardware.NetworkIF;

public class NetworkRealTimeChartPane extends RealTimeChartPane {

    private NETWORK_INFO Ethernet;
    private LineChart EthernetChart;
    private Series<Number, Number> SendSeries, ReceiveSeries;

    public NetworkRealTimeChartPane() {

    }

    public NetworkRealTimeChartPane(NETWORK_INFO Ethernet, LineChart EthernetChart) {
        super();
        this.EthernetChart = EthernetChart;
        this.Ethernet = Ethernet;
    }

    @Override
    public void configChart() {
        SendSeries = new Series<>();
        SendSeries.setName("Send");
        ReceiveSeries = new Series<>();
        ReceiveSeries.setName("Receive");

        EthernetChart.getData().addAll(SendSeries, ReceiveSeries);
        EthernetChart.getXAxis().setLabel("Time (seconds)");
        EthernetChart.getYAxis().setLabel("Speed");
        NumberAxis yAxis = (NumberAxis) EthernetChart.getYAxis();
        yAxis.setTickLabelFormatter(new DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                double bytes = object.doubleValue();
                if (bytes >= 1000 * 1000) {
                    return String.format("%.2f MiB/s", bytes / 1000000);
                }
                
                if (bytes >= 1000) {
                    return String.format("%.2f KiB/s", bytes / 1000);
                }
                
                return String.format("%.2f B/s", bytes);
                
            }
        });
        EthernetChart.setAnimated(false);
        EthernetChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        EthernetChart.setPrefSize(1000, 600);
        this.getChildren().add(EthernetChart);
    }

    public void updateData() {
        long sendSpeed = this.Ethernet.getSendSpeed();
        long recvSpeed = this.Ethernet.getRecvSpeed();

        SendSeries.getData().add(new Data<>(0, sendSpeed));
        ReceiveSeries.getData().add(new Data<>(0, recvSpeed));

        SendSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        ReceiveSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        if (SendSeries.getData().size() > 60 * 1000d / getInterval() + 1) {
            Data garbage = SendSeries.getData().get(0);
            garbage = null;
            SendSeries.getData().remove(0);
            garbage = ReceiveSeries.getData().get(0);
            garbage = null;
            ReceiveSeries.getData().remove(0);
        }
    }
}
