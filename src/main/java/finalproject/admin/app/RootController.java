package finalproject.admin.app;

import dev.sol.core.application.FXController;
import dev.sol.core.application.loader.FXLoaderFactory;
import finalproject.admin.app.management.ManagementLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class RootController extends FXController {

    @FXML
    private Button studentManagementButton;

    private Scene scene;

    @FXML
    private void handleStudentManagementButton() {
        System.out.println("Student Management Button Clicked");
        initialize_application();
    }

    private void initialize_application() {
        ManagementLoader loader = (ManagementLoader) FXLoaderFactory
                .createInstance(ManagementLoader.class,
                        getClass().getResource("/finalproject/admin/app/management/MANAGEMENT.fxml"))
                .addParameter("scene", scene)
                .addParameter("OWNER", getParameter("OWNER"))
                .initialize();
        if (loader != null) {
            loader.load();
        } else {
            System.err.println("Loader is null");
        }
    }

    @Override
    protected void load_bindings() {
        scene = (Scene) getParameter("SCENE");
        // Implement bindings if necessary
    }

    @Override
    protected void load_fields() {
        // Initialize fields if necessary
    }

    @Override
    protected void load_listeners() {
        studentManagementButton.setOnMouseClicked(event -> handleStudentManagementButton());
    }

}
