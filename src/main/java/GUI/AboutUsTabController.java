package GUI;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

public class AboutUsTabController {

    @FXML
    private Hyperlink hyperLink;

    @FXML
    public void initialize() {
        // Any initialization code if needed
    }

    @FXML
    private void hyperLinkAction(ActionEvent e) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI("https://github.com/D4tLe/Project1.Server_Info"));
            } else {
                System.out.println("Desktop or browse action not supported");
            }
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
}
