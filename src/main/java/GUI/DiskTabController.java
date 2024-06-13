
package GUI;

import INFO.DISK_INFO;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.util.Duration;
import oshi.hardware.HWDiskStore;

import oshi.util.FormatUtil;

public class DiskTabController {

    private DISK_INFO diskInfo;
    private int id;
    private Series<Number, Number> activeTimeSeries, readSpeedSeries, writeSpeedSeries;
    private double interval = 1000;

    @FXML
    private LineChart activeTimeChart, speedChart;

    @FXML
    private Label diskOrder, diskName, activeTime, readSpeed, writeSpeed;

    public DiskTabController() {

    }

    public DiskTabController(HWDiskStore disk, int order) {
        diskInfo = new DISK_INFO(disk);
        this.id = order;
        activeTimeSeries = new Series<>();
        activeTimeSeries.setName("Active time");
        readSpeedSeries = new Series<>();
        readSpeedSeries.setName("Read speed");
        writeSpeedSeries = new Series<>();
        writeSpeedSeries.setName("Write speed");
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getInterval() {
        return this.interval;
    }

    public void initialize() {
        diskOrder.setText(String.format("Disk %d", id));
        diskName.setText(diskInfo.getDiskName());

        activeTimeChart.getData().add(this.activeTimeSeries);
        NumberAxis xAxis = (NumberAxis) activeTimeChart.getXAxis();
        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
            @Override
            public String toString(Number object) {
                int time = object.intValue();
                return time + "s";
            }
        });
        NumberAxis yAxis = (NumberAxis) activeTimeChart.getYAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                int usage = object.intValue();
                return usage + "%";
            }
        });
        activeTimeChart.setAnimated(false);

        speedChart.getData().addAll(readSpeedSeries, writeSpeedSeries);
        xAxis = (NumberAxis) speedChart.getXAxis();
        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
            @Override
            public String toString(Number object) {
                int time = object.intValue();
                return time + "s";
            }
        });
        speedChart.getYAxis().setLabel("Speed");
        yAxis = (NumberAxis) speedChart.getYAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                long bytes = object.longValue();
                return FormatUtil.formatBytesDecimal(bytes) + "/s";
            }
        });
        speedChart.setAnimated(false);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(getInterval()), event -> {
            updateSpecs();
            updateChart();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateSpecs() {
        this.activeTime.setText("" + String.format("%.2f%%", diskInfo.getActiveTime()));
        this.readSpeed.setText("" + FormatUtil.formatBytesDecimal(diskInfo.getDiskReadSpeed()) + "/s");
        this.writeSpeed.setText("" + FormatUtil.formatBytesDecimal(diskInfo.getDiskWriteSpeed()) + "/s");
    }

    private void updateChart() {
        double active = diskInfo.getActiveTime();
        double read = diskInfo.getDiskReadSpeed();
        double write = diskInfo.getDiskWriteSpeed();

        activeTimeSeries.getData().add(new XYChart.Data<>(0, active));
        readSpeedSeries.getData().add(new XYChart.Data<>(0, read));
        writeSpeedSeries.getData().add(new XYChart.Data<>(0, write));

        activeTimeSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        readSpeedSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        writeSpeedSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        if (activeTimeSeries.getData().size() > 60 * 1000d / getInterval()) {
            XYChart.Data garbage = activeTimeSeries.getData().get(0);
            garbage = null;
            activeTimeSeries.getData().remove(0);
            garbage = readSpeedSeries.getData().get(0);
            garbage = null;
            readSpeedSeries.getData().remove(0);
            garbage = writeSpeedSeries.getData().get(0);
            garbage = null;
            this.writeSpeedSeries.getData().remove(0);
        }

        diskInfo.updateAttributes();
    }
}
