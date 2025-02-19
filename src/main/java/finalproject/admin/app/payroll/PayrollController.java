package finalproject.admin.app.payroll;

import dev.sol.core.application.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PayrollController extends FXController {

    @FXML
    private Button homeButton;

    private Stage stage;

    @FXML
    private void handleHomeButton() {
        System.out.println("home button is clicked");
        initializeHome();

    }

    private void initializeHome() {
        stage = (Stage) homeButton.getScene().getWindow();
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
        homeButton.setOnAction(event -> handleHomeButton());
    }

}
