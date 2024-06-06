package GUI.Pane;

import GUI.DISK_INFO;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.VBox;

public class DiskRealTimeChartPane extends RealTimeChartPane {

    private LineChart ActiveTimeChart, TransferRateChart;
    private Series<Number, Number> ActiveTimeSeries, ReadSpeedSeries, WriteSpeedSeries;
    private DISK_INFO Disk;

    public DiskRealTimeChartPane(DISK_INFO Disk, LineChart... chartList) {
        super();
        this.ActiveTimeChart = chartList[0];
        this.TransferRateChart = chartList[1];
        this.Disk = Disk;
    }

    @Override
    public void configChart() {

        ActiveTimeSeries = new Series<>();
        ActiveTimeSeries.setName("Active time");
        ReadSpeedSeries = new Series<>();
        ReadSpeedSeries.setName("Read speed");
        WriteSpeedSeries = new Series<>();
        WriteSpeedSeries.setName("Write speed");

        ActiveTimeChart.getData().add(ActiveTimeSeries);
        ActiveTimeChart.getXAxis().setLabel("Time (seconds)");
        ActiveTimeChart.getYAxis().setLabel("Usage (%)");
        ActiveTimeChart.setAnimated(false);
        ActiveTimeChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        ActiveTimeChart.setPrefSize(1000, 400);
        
        TransferRateChart.getData().addAll(ReadSpeedSeries, WriteSpeedSeries);
        TransferRateChart.getXAxis().setLabel("Time (seconds)");
        TransferRateChart.getYAxis().setLabel("Speed");
        NumberAxis yAxis = (NumberAxis) TransferRateChart.getYAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
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
        TransferRateChart.setAnimated(false);
        TransferRateChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        TransferRateChart.setPrefSize(1000, 200);
        
        VBox box = new VBox();
        box.getChildren().addAll(ActiveTimeChart, TransferRateChart);
        this.getChildren().addAll(box);
    }

    @Override
    public void updateData() {
        double activeTime = Disk.getActiveTime();
        double readSpeed = Disk.getDiskReadSpeed();
        double writeSpeed = Disk.getDiskWriteSpeed();

        ActiveTimeSeries.getData().add(new Data<>(0, activeTime));
        ReadSpeedSeries.getData().add(new Data<>(0, readSpeed));
        WriteSpeedSeries.getData().add(new Data<>(0, writeSpeed));

        ActiveTimeSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        ReadSpeedSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        WriteSpeedSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        if (ActiveTimeSeries.getData().size() > 60 * 1000d / getInterval()) {
            Data garbage = ActiveTimeSeries.getData().get(0);
            garbage = null;
            ActiveTimeSeries.getData().remove(0);
            garbage = ReadSpeedSeries.getData().get(0);
            garbage = null;
            ReadSpeedSeries.getData().remove(0);
            garbage = WriteSpeedSeries.getData().get(0);
            garbage = null;
            this.WriteSpeedSeries.getData().remove(0);
        }
    }
}
