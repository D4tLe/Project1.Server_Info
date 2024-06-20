package GUI;

import INFO.MEMORY_INFO;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import oshi.util.FormatUtil;

public class MemoryTabController {

    private MEMORY_INFO memory = new MEMORY_INFO();
    private Series<Number, Number> RAMSeries, SwapSeries;
    private double interval = 1000;

    @FXML
    private Label totalMemory, inUsed, available, page, nonPage;

    @FXML
    private LineChart memoryChart;

    public MemoryTabController() {
        this.RAMSeries = new Series<>();
        this.RAMSeries.setName("RAM");
        this.SwapSeries = new Series<>();
        this.SwapSeries.setName("Swap");
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    public double getInterval() {
        return this.interval;
    }

    @FXML
    public void initialize() {
        totalMemory.setText(FormatUtil.formatBytesDecimal(memory.getTotalMemory()));

        NumberAxis xAxis = (NumberAxis) memoryChart.getXAxis();
        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
            @Override
            public String toString(Number object) {
                int time = object.intValue();
                return time + "s";
            }
        });
        NumberAxis yAxis = (NumberAxis) memoryChart.getYAxis();
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                int usage = object.intValue();
                return usage + "%";
            }
        });
        memoryChart.setAnimated(false);
        memoryChart.getData().addAll(RAMSeries, SwapSeries);
    }

    private void updateSpecs() {
        inUsed.setText(FormatUtil.formatBytesDecimal(memory.getMemoryInUsed()));
        available.setText(FormatUtil.formatBytesDecimal(memory.getMemoryAvailable()));
        page.setText(FormatUtil.formatBytesDecimal(memory.getSwapUsed()));
        nonPage.setText(FormatUtil.formatBytesDecimal(memory.getNonSwapUsed()));
    }

    private void updateChart() {
        double memoryUsedPercent = memory.getMemoryUsage();
        double swapUsedPercent = memory.getSwapUsage();
        RAMSeries.getData().add(new Data<>(0, memoryUsedPercent));
        SwapSeries.getData().add(new Data<>(0, swapUsedPercent));

        RAMSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        SwapSeries.getData().forEach(dataPoint -> {
            dataPoint.setXValue(dataPoint.getXValue().doubleValue() + getInterval() / 1000d);
            dataPoint.getNode().setVisible(false);
        });

        if (RAMSeries.getData().size() > 60 * 1000d / getInterval() + 1) {
            RAMSeries.getData().remove(0);
            SwapSeries.getData().remove(0);
        }
    }
    
    public void updateTab() {
        updateSpecs();
        updateChart();
    }
}
