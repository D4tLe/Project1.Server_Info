module hust.soict.cybersec.testgui {
    requires javafx.controls;
    requires javafx.fxml;

    opens hust.soict.cybersec.testgui to javafx.fxml;
    exports hust.soict.cybersec.testgui;
    requires com.github.oshi;
}
