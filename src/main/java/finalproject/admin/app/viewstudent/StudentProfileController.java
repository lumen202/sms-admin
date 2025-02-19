package finalproject.admin.app.viewstudent;

import dev.sol.core.application.FXController;
import dev.sol.core.application.loader.FXLoaderFactory;
import finalproject.admin.app.viewstudent.studentform.StudentFormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StudentProfileController extends FXController {

    @FXML
    private Button editButton;

    @FXML
    private Button backButton;

    private Stage stage;

    @FXML
    private void handleEditButton() {
        System.out.println("edit button is clicked");
        initializeEdit();

    }

    @FXML
    private void handleBackButton() {
            stage = (Stage) backButton.getScene().getWindow();
            stage.close();

    }

    private void initializeEdit() {
        StudentFormLoader loader = (StudentFormLoader) FXLoaderFactory
                .createInstance(StudentFormLoader.class,
                        getClass().getResource(
                                "/finalproject/admin/app/viewstudent/studentform/STUDENT_FORM.fxml"))
                .initialize();
        loader.load();

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
        editButton.setOnAction(event -> handleEditButton());
        backButton.setOnMouseClicked(event ->handleBackButton());
    }

}
