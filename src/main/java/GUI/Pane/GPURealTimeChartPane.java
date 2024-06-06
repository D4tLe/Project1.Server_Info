package GUI.Pane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.util.Duration;
import oshi.hardware.GraphicsCard;

public class GPURealTimeChartPane extends RealTimeChartPane {
    private GraphicsCard GPU;
    private LineChart Chart3D, CopyChart, VideoDecodeChart, VideoEncodeChart, DedicatedMemoryChart, SharedMemoryChart;
    private Series<Number, Number> Series3D, CopySeries, VideoEncodeSeries, VideoDecodeSeries, DedicatedMemorySeries, SharedMemorySeries;
    
    public GPURealTimeChartPane(GraphicsCard GPU, LineChart... chartList) {
        super();
        this.Chart3D = chartList[0];
        this.CopyChart = chartList[1];
        this.VideoEncodeChart = chartList[2];
        this.VideoDecodeChart = chartList[3];
        this.DedicatedMemoryChart = chartList[4];
        this.SharedMemoryChart = chartList[5];
    }
    
    public void configChart() {
        Series3D = new Series<>();
        Series3D.setName("3D");
        CopySeries = new Series<>();
        CopySeries.setName("Copy");
        VideoEncodeSeries = new Series<>();
        VideoEncodeSeries.setName("Video Encode");
        VideoDecodeSeries = new Series<>();
        VideoDecodeSeries.setName("Video Decode");
        DedicatedMemorySeries = new Series<>();
        DedicatedMemorySeries.setName("Dedicated Memory Usage");
        SharedMemorySeries = new Series<>();
        SharedMemorySeries.setName("Shared Memory Usage");
        
        this.Chart3D.getData().add(Series3D);
        this.CopyChart.getData().add(CopySeries);
        this.VideoEncodeChart.getData().add(VideoEncodeSeries);
        this.VideoDecodeChart.getData().add(VideoDecodeSeries);
        this.DedicatedMemoryChart.getData().add(DedicatedMemorySeries);
        this.SharedMemoryChart.getData().add(SharedMemorySeries);
        
        this.getChildren().addAll(Chart3D, CopyChart, VideoEncodeChart, VideoDecodeChart, DedicatedMemoryChart, SharedMemoryChart);
        
        double interval = getInterval();
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(interval), event -> {
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
    }
}
