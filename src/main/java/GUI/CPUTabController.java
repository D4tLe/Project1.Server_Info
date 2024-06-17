package GUI;

import INFO.CPU_INFO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import oshi.util.FormatUtil;

public class CPUTabController {

    private CPU_INFO cpu;
    private double interval = 800;
    private double time = 60;

    public CPUTabController() {
        this.cpu = new CPU_INFO();
    }

    @FXML
    private Label cpuName, utilization, speed, processes, threads, handles, uptime, baseSpeed, sockets, cores, logicalProcessors, L1DataCache, L1InstructionCache, L2Cache, L3Cache;

    @FXML
    private GridPane gridPane;

    private class CPUChart extends LineChart {

        private Series series;

        public CPUChart(Axis axis, Axis axis1) {
            super(axis, axis1);
            NumberAxis xAxis = (NumberAxis) this.getXAxis();
            xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
                @Override
                public String toString(Number object) {
                    int time = object.intValue();
                    return time + "s";
                }
            });
            NumberAxis yAxis = (NumberAxis) this.getYAxis();
            yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
                @Override
                public String toString(Number object) {
                    int usage = object.intValue();
                    return usage + "%";
                }
            });
            this.series = new Series<Number, Number>();
            this.setCreateSymbols(false);
            this.getData().add(series);
            this.setAnimated(false);
            this.getXAxis().setTickLabelsVisible(false);
            this.getXAxis().setOpacity(0);
        }

        public Series<Number, Number> getSeries() {
            return this.series;
        }

    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getInterval() {
        return this.interval;
    }

    public void initialize() {
        cpuName.setText(cpu.getName());
        baseSpeed.setText("Base speed: " + cpu.getBaseSpeed());
        sockets.setText("Sockets: " + cpu.getSockets());
        cores.setText("Cores: " + cpu.getCores());
        logicalProcessors.setText("Logical processors: " + cpu.getLogicalProcessors());
        L1DataCache.setText("L1 data cache: " + FormatUtil.formatBytesDecimal(cpu.getCache().get(3).getCacheSize()));
        L1InstructionCache.setText("L1 instruction cache: " + FormatUtil.formatBytesDecimal(cpu.getCache().get(2).getCacheSize()));
        L2Cache.setText("L2 cache: " + FormatUtil.formatBytesDecimal(cpu.getCache().get(1).getCacheSize()));
        L3Cache.setText("L3 cache: " + FormatUtil.formatBytesDecimal(cpu.getCache().get(0).getCacheSize()));

        int[] prefSize = this.calculateGridPaneSize();
        int prefColumn = prefSize[0];
        int prefRow = prefSize[1];

        for (int i = 0; i < prefColumn; i++) {
            for (int j = 0; j < prefRow; j++) {
                CPUChart chart = new CPUChart(new NumberAxis(0, 60, 0), new NumberAxis(0, 100, 100));
                chart.getSeries().setName("CPU" + (i * prefRow + j));
                gridPane.add(chart, i, j);
            }
        }



        /*Timeline timeline = new Timeline(new KeyFrame(Duration.millis(getInterval()), event -> {
            updateSpecs();
            updateChart();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();*/
                
    }

    public int[] calculateGridPaneSize() {
        int[] size = new int[2];
        for (int i = (int) Math.sqrt(cpu.getLogicalProcessors()); i > 0; i--) {
            if (cpu.getLogicalProcessors() % i == 0) {
                size[0] = cpu.getLogicalProcessors() / i;
                size[1] = i;
                return size;
            }
        }
        return size;
    }

    private void updateSpecs() {
        utilization.setText("" + String.format("%.2f%%", cpu.getUtilization() * 100));
        processes.setText("" + cpu.getProcessesCount());
        threads.setText("" + cpu.getThreadCount());
        uptime.setText(cpu.getUpTime());
    }

    private void updateChart() {
        double[] eachProcessorLoad = cpu.getEachProcessorsLoad();
        int count = cpu.getLogicalProcessors();
        time += interval / 1000d;

        for (int i = 0; i < count; i++) {
            CPUChart chart = (CPUChart) gridPane.getChildren().get(i);
            ((NumberAxis) chart.getXAxis()).setUpperBound(time);
            ((NumberAxis) chart.getXAxis()).setLowerBound(time - 60);
            Series<Number, Number> series = chart.getSeries();

            series.getData().add(new Data<>(time, eachProcessorLoad[i] * 100));
             
            /*series.getData().forEach(dataPoint -> {
                dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            });*/

            if (series.getData().size() > 60 * 1000d / getInterval() + 1) {
                series.getData().remove(0);
            }
        }
    }
    
    public void updateTab() {
        updateSpecs();
        updateChart();
    }
}
