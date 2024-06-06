package GUI.Tab;

import GUI.Pane.CPURealTimeChartPane;
import GUI.CPU_INFO;
import GUI.Pane.RealTimeChartPane;
import GUI.Tab.PerformanceTab;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CPUTab extends PerformanceTab {

    private CPU_INFO CPU;
    private RealTimeChartPane CPUChartPane;
    private VBox dynamicSpecsPane = new VBox();
    private VBox staticsSpecsPane = new VBox();

    public CPUTab(CPU_INFO CPU) {
        super();
        this.CPU = CPU;
    }

    public void config() {
        this.CPUChartPane = new CPURealTimeChartPane(CPU, new LineChart(new NumberAxis(60, 0, 10), new NumberAxis(0, 100, 10)));
        this.CPUChartPane.setInterval(getInterval());
        this.CPUChartPane.configChart();

        this.dynamicSpecsPane.setSpacing(10);
        this.dynamicSpecsPane.setPrefSize(300, 300);

        Label utilization = new Label("Utilization: " + CPU.getUtilization());
        Label speed = new Label("Speed: " + CPU.getBaseSpeed());
        Label processes = new Label("Processes: " + CPU.getProcessesCount());
        Label thread = new Label("Thread: " + CPU.getThreadCount());
        Label handles = new Label("Handles: ");
        Label upTime = new Label("Up time: ");
        
        this.dynamicSpecsPane.getChildren().addAll(utilization, speed, processes, thread, handles, upTime);
        this.dynamicSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        });
        
        this.staticsSpecsPane.setSpacing(10);
        this.staticsSpecsPane.setPrefSize(300, 300);
        
        Label baseSpeed = new Label("Base speed: " + CPU.getBaseSpeed());
        Label sockets = new Label("Sockets: " + CPU.getSockets());
        Label cores = new Label("Cores: " + CPU.getCores());
        Label logicalProcessors = new Label("Logical processors: " + CPU.getLogicalProcessors());
        Label virtualization = new Label("Virtualization: " + CPU.getUpTime());
        Label L1Cache = new Label("L3 cache: " + CPU.getCache().get(0).getCacheSize());
        Label L2Cache = new Label("L2 cache: " + CPU.getCache().get(1).getCacheSize());
        Label L3Cache = new Label("L1 cache: " + CPU.getCache().get(2).getCacheSize());
        
        this.staticsSpecsPane.getChildren().addAll(baseSpeed, sockets, cores, logicalProcessors, virtualization, L1Cache, L2Cache, L3Cache);
        this.staticsSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", 16));
        });
        
        HBox box = new HBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(dynamicSpecsPane, staticsSpecsPane);
        
        this.getContentBox().getChildren().addAll(CPUChartPane, box);
    }
    
    public void updateData() {
        Label utilization = (Label) this.dynamicSpecsPane.getChildren().get(0);
        Label speed = (Label) this.dynamicSpecsPane.getChildren().get(1);
        Label processes = (Label) this.dynamicSpecsPane.getChildren().get(2);
        Label thread = (Label) this.dynamicSpecsPane.getChildren().get(3);
        Label handles = (Label) this.dynamicSpecsPane.getChildren().get(4);
        Label upTime = (Label) this.dynamicSpecsPane.getChildren().get(5);
        
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(getInterval()), event -> {
            utilization.setText("Utilization: " + CPU.getUtilization());
            speed.setText("Speed: " + CPU.getBaseSpeed());
            processes.setText("Processes: " + CPU.getProcessesCount());
            thread.setText("Thread: " + CPU.getThreadCount());
            handles.setText("Handles: ");
            upTime.setText("Up time: " + CPU.getUpTime());

            this.CPUChartPane.updateData();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
