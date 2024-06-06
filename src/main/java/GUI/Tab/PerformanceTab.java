package GUI.Tab;

import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class PerformanceTab extends Tab {
    
    private VBox contentBox;
    private double interval;
    
    public PerformanceTab() {
        this.contentBox = new VBox();
        this.contentBox.setAlignment(Pos.CENTER);
        this.setContent(this.contentBox);
    }
    
    public VBox getContentBox() {
        return this.contentBox;
    }
    
    public void setInterval(double interval) {
        this.interval = interval;
    }
    
    public double getInterval() {
        return this.interval;
    }
}
