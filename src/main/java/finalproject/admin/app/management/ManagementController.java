package finalproject.admin.app.management;

import dev.sol.core.application.FXController;
import dev.sol.core.application.loader.FXLoaderFactory;
import finalproject.admin.app.management.viewstudent.ViewStudentLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ManagementController extends FXController {

    @FXML
    private Label studentManagementLabel;
    @FXML
    private Button backButton;

    Scene scence;
    Stage stage;

    @FXML
    private void handleBackButton() {
        initializeRoot();
        System.out.println("Back Button Clicked");
    }

    @FXML
    private void handleViewStudentButton() {
        System.out.println("View Student Button Clicked");
        initializeViewStudent();

    }

    private void initializeViewStudent() {

        ViewStudentLoader loader = (ViewStudentLoader) FXLoaderFactory
                .createInstance(ViewStudentLoader.class,
                        getClass().getResource("/finalproject/admin/app/management/viewstudent/VIEW_STUDENT.fxml"))
                .initialize();
        loader.load();

    }

    private void initializeRoot() {
        closeCurrentStage();
    }

    @Override
    protected void load_bindings() {

    }

    private void closeCurrentStage() {
        stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @Override
    protected void load_fields() {
        // Initialize fields if necessary
    }

    @Override
    protected void load_listeners() {
        // No need to manually set the event handler
    }

}
