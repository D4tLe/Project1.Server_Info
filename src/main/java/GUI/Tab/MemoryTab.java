package GUI.Tab;

import GUI.Pane.MemoryRealTimeChartPane;
import GUI.MEMORY_INFO;
import GUI.Pane.RealTimeChartPane;
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

public class MemoryTab extends PerformanceTab {
    private MEMORY_INFO memory;
    private RealTimeChartPane memoryChartPane;
    private VBox dynamicSpecsPane = new VBox();
    private VBox staticsSpecsPane = new VBox();
    
    public MemoryTab(MEMORY_INFO memory) {
        super();
        this.memory = memory;
    }
    
    public void config() {
        memoryChartPane = new MemoryRealTimeChartPane(this.memory, new LineChart(new NumberAxis(60, 0, 10), new NumberAxis(0, 100, 10)));
        this.memoryChartPane.setInterval(getInterval());
        this.memoryChartPane.configChart();
        
        this.dynamicSpecsPane.setSpacing(10);
        this.dynamicSpecsPane.setPrefSize(300, 300);
        
        Label memoryUsage = new Label("In use: " + memory.getMemoryInUsed());
        Label available = new Label("Available: " + memory.getMemoryAvailable());
        Label swapUsage = new Label("Swap used: " + memory.getSwapUsed());
        Label nonSwapUsage = new Label("Non swap used: " + memory.getNonSwapUsed());
        
        this.dynamicSpecsPane.getChildren().addAll(memoryUsage, available, swapUsage, nonSwapUsage);
        this.dynamicSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        });
        
        this.staticsSpecsPane.setSpacing(10);
        this.staticsSpecsPane.setPrefSize(300, 300);
        
        Label speed = new Label("Speed: ");
        Label slotUsed = new Label("Slot used: ");
        Label formFactor = new Label("Form factor: ");
        Label hardwareRev = new Label("Hardware reverse: ");
        
        this.staticsSpecsPane.getChildren().addAll(speed, slotUsed, formFactor, hardwareRev);
        this.staticsSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", 16));
        });
        
        HBox box = new HBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(dynamicSpecsPane, staticsSpecsPane);
        
        this.getContentBox().getChildren().addAll(memoryChartPane, box);
    }
    
    public void updateData() {
        Label memoryUsage = (Label) this.dynamicSpecsPane.getChildren().get(0);
        Label available = (Label) this.dynamicSpecsPane.getChildren().get(1);
        Label swapUsage = (Label) this.dynamicSpecsPane.getChildren().get(2);
        Label nonSwapUsage = (Label) this.dynamicSpecsPane.getChildren().get(3);
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(getInterval()), event -> {
            memoryUsage.setText("In use: " + FormatUtil.formatBytes(memory.getMemoryInUsed()));
            available.setText("Available: " + FormatUtil.formatBytes(memory.getMemoryAvailable()));
            swapUsage.setText("Swap used: " + FormatUtil.formatBytes(memory.getSwapUsed()));
            nonSwapUsage.setText("Non swap used: " + FormatUtil.formatBytes(memory.getNonSwapUsed()));

            this.memoryChartPane.updateData();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
