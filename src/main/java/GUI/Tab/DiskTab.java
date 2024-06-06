package GUI.Tab;

import GUI.DISK_INFO;
import GUI.Pane.DiskRealTimeChartPane;
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

public class DiskTab extends PerformanceTab {

    private DISK_INFO disk;
    private RealTimeChartPane DiskChartPane;
    private VBox dynamicSpecsPane = new VBox();
    private VBox staticsSpecsPane = new VBox();

    public DiskTab(DISK_INFO disk) {
        super();
        this.disk = disk;
    }

    public void config() {
        this.DiskChartPane = new DiskRealTimeChartPane(disk, new LineChart(new NumberAxis(60, 0, 10), new NumberAxis(0, 100, 10)), new LineChart(new NumberAxis(60, 0, 10), new NumberAxis()));
        this.DiskChartPane.setInterval(getInterval());
        this.DiskChartPane.configChart();

        this.dynamicSpecsPane.setSpacing(10);
        this.dynamicSpecsPane.setPrefSize(300, 300);

        disk.updateAttributes();
        Label activeTime = new Label("Active time: " + String.format("%.2f%%", disk.getActiveTime()));
        Label readSpeed = new Label("Read speed: " + disk.getDiskReadSpeed());
        Label writeSpeed = new Label("Write speed: " + disk.getDiskWriteSpeed());

        this.dynamicSpecsPane.getChildren().addAll(activeTime, readSpeed, writeSpeed);
        this.dynamicSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        });

        this.staticsSpecsPane.setSpacing(10);
        this.staticsSpecsPane.setPrefSize(300, 300);

        Label capacity = new Label("Capacity: " + disk.getDiskSize());
        Label formatted = new Label("Formatted:");
        Label systemDisk = new Label("System Disk: ");
        Label pageFile = new Label("Page file: ");
        Label type = new Label("Type: ");

        this.staticsSpecsPane.getChildren().addAll(capacity, formatted, systemDisk, pageFile, type);
        this.staticsSpecsPane.getChildren().forEach(node -> {
            Label lbl = (Label) node;
            lbl.setFont(Font.font("Verdana", 16));
        });

        HBox box = new HBox();
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(dynamicSpecsPane, staticsSpecsPane);

        this.getContentBox().getChildren().addAll(DiskChartPane, box);
    }

    public void updateData() {
        Label activeTime = (Label) this.dynamicSpecsPane.getChildren().get(0);
        Label readSpeed = (Label) this.dynamicSpecsPane.getChildren().get(1);
        Label writeSpeed = (Label) this.dynamicSpecsPane.getChildren().get(2);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(getInterval()), event -> {
            disk.updateAttributes();
            activeTime.setText("Active time: " + String.format("%.2f%%", disk.getActiveTime()));
            readSpeed.setText("Read speed: " + FormatUtil.formatBytes(disk.getDiskReadSpeed()) + "/s");
            writeSpeed.setText("Write speed: " + FormatUtil.formatBytes(disk.getDiskWriteSpeed()) + "/s");
            this.DiskChartPane.updateData();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
