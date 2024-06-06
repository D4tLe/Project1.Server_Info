package GUI.Pane;


import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class RealTimeChartPane extends Pane {

    private double interval;

    public RealTimeChartPane() {
    }
    
    public void setInterval(double interval) {
        this.interval = interval;
    }
    
    public double getInterval() {
        return this.interval;
    }

    public void configChart() {
    }
    
    public void updateData() {
        
    }
}
