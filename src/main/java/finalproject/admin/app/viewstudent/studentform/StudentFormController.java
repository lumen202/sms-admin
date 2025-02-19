package finalproject.admin.app.viewstudent.studentform;

import dev.sol.core.application.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StudentFormController extends FXController{
    @FXML private Button cancelButton;

    private Stage stage;

    @FXML private void handleCancelButton(){
        initializeCancel();

    }

    private void initializeCancel(){

        stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

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
