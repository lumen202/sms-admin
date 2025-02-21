package finalproject.admin.app.viewstudent.studentform;

import dev.sol.core.application.FXController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StudentFormController extends FXController {
    @FXML
    private Button cancelButton;
    @FXML
    private StackPane contentPane;

    private Stage stage;

    @FXML
    private void handleCancelButton() {
        initializeCancel();

    }

    private void initializeCancel() {

    }

    private void loadSceneWithYear(String fxmlPath, Class<? extends FXController> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent rootNode = loader.load();
            applyFadeTransition(rootNode);
            contentPane.getChildren().setAll(rootNode);
            if (rootNode instanceof Region) {
                Region region = (Region) rootNode;
                region.prefWidthProperty().bind(contentPane.widthProperty());
                region.prefHeightProperty().bind(contentPane.heightProperty());
            }
            FXController controller = loader.getController();
            if (controllerClass.isInstance(controller)) {
                controller.load(); // Ensure load is called after setting the year
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file: " + fxmlPath);
        }
    }

    @Override
    protected void load_bindings() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load_bindings'");
    }

    @Override
    protected void load_fields() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'load_fields'");
    }

    @Override
    protected void load_listeners() {
        cancelButton.setOnMouseClicked(event -> handleCancelButton());
    }

}
