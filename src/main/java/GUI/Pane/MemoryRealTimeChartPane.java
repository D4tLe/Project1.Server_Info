package GUI.Pane;

import GUI.MEMORY_INFO;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class MemoryRealTimeChartPane extends RealTimeChartPane {

    private MEMORY_INFO memory;
    private LineChart MemoryUsageChart;
    private Series<Number, Number> RAMSeries, SwapSeries;

    public MemoryRealTimeChartPane(MEMORY_INFO memory, LineChart MemoryUsageChart) {
        super();
        this.memory = memory;
        this.MemoryUsageChart = MemoryUsageChart;
    }

    @Override
    public void configChart() {
        RAMSeries = new Series<>();
        RAMSeries.setName("RAM");
        SwapSeries = new Series<>();
        SwapSeries.setName("Swap");

        MemoryUsageChart.getData().addAll(RAMSeries, SwapSeries);
        MemoryUsageChart.getXAxis().setLabel("Time (seconds)");
        MemoryUsageChart.getYAxis().setLabel("Usage (%)");
        MemoryUsageChart.setAnimated(false);
        MemoryUsageChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        MemoryUsageChart.setPrefSize(1000, 600);

        this.getChildren().add(MemoryUsageChart);
    }

    @Override
    public void updateData() {
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
            Data garbage = RAMSeries.getData().get(0);
            garbage = null;
            RAMSeries.getData().remove(0);
            garbage = SwapSeries.getData().get(0);
            garbage = null;
            SwapSeries.getData().remove(0);
            
        }
    }
}
