package GUI.Pane;

import GUI.CPU_INFO;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;

public class CPURealTimeChartPane extends RealTimeChartPane {

    private CPU_INFO CPU;
    private LineChart CPUUsageChart;
    private ArrayList<Series<Number, Number>> seriesList;

    public CPURealTimeChartPane(CPU_INFO CPU, LineChart CPUUsage) {
        super();
        this.CPU = CPU;
        this.CPUUsageChart = CPUUsage;
    }

    @Override
    public void configChart() {
        seriesList = new ArrayList<>();

        Series<Number, Number> cpu;
        for (int i = 0; i < CPU.getLogicalProcessors(); i++) {
            cpu = new Series();
            cpu.setName("CPU" + (i + 1));
            seriesList.add(cpu);
        }

        CPUUsageChart.getData().setAll(seriesList);
        CPUUsageChart.getXAxis().setLabel("Time (seconds)");
        CPUUsageChart.getYAxis().setLabel("Usage (%)");
        CPUUsageChart.setAnimated(false);
        CPUUsageChart.getStylesheets().add(getClass().getResource("chart.css").toExternalForm());
        CPUUsageChart.setPrefSize(1000, 600);
        this.getChildren().add(CPUUsageChart);
    }

    @Override
    public void updateData() {
        double[] pCpuLoads = CPU.getEachProcessorsLoad();

        int cpuNo = pCpuLoads.length;

        for (int i = 0; i < cpuNo; i++) {
            seriesList.get(i).getData().add(new Data<>(0, pCpuLoads[i] * 100));
        }

        for (int i = 0; i < cpuNo; i++) {
            ObservableList<Data<Number, Number>> dataList = seriesList.get(i).getData();
            
            dataList.forEach(dataPoint -> {
                dataPoint.setXValue(dataPoint.getXValue().doubleValue() + this.getInterval() / 1000d);
                dataPoint.getNode().setVisible(false);
            });
            if (dataList.size() > 60 * 1000d / this.getInterval() + 1) {
                Data garbage = dataList.get(0);
                garbage = null;
                dataList.remove(0);
            }
        }
    }
}
