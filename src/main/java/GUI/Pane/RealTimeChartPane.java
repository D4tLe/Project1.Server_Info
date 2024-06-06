package GUI.Pane;

import javafx.scene.layout.Pane;

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
