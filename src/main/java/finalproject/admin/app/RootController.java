package finalproject.admin.app;

import dev.finalproject.App;
import dev.finalproject.models.Student;
import dev.sol.core.application.FXController;
import finalproject.admin.app.attendance.AttendanceController;
import finalproject.admin.app.home.HomeController;
import finalproject.admin.app.home.HomeLoader;
import finalproject.admin.app.payroll.PayrollController;
import finalproject.admin.util.YearData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ComboBox;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.security.Key;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import dev.sol.core.application.loader.FXLoaderFactory;
import dev.sol.core.application.loader.FXLoader;

public class RootController extends FXController {

    @FXML
    private Button attendanceButton;
    @FXML
    private Button payrollButton;
    @FXML
    private MenuItem generateKeyMenuItem;
    @FXML
    private StackPane contentPane;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<String> yearComboBox;

    private ObservableList<Student> studentMasterList;
    @FXML
    private Scene scene;

    @FXML
    private void handlesPayrollButton() {
        highlightButton(payrollButton);
        loadSceneWithYear("/finalproject/admin/app/payroll/PAYROLL.fxml", PayrollController.class);
    }

    @FXML
    private void handleAttendanceButton() {
        highlightButton(attendanceButton);
        loadSceneWithYear("/finalproject/admin/app/attendance/ATTENDANCE.fxml", AttendanceController.class);
    }

    @FXML
    private void handleGenerateKeyMenuItem() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
            byte[] encodedKey = secretKey.getEncoded();
            String encryptedKey = Base64.getEncoder().encodeToString(encodedKey);
            System.out.println("Generated Encrypted Key: " + encryptedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackButton() {
        highlightButton(backButton);
        loadSceneWithYear("/finalproject/admin/app/home/HOME.fxml",HomeController.class);
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

    private void applyFadeTransition(Parent rootNode) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), rootNode);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    private void highlightButton(Button button) {
        // Reset styles for all buttons
        attendanceButton.setStyle("");
        payrollButton.setStyle("");
        backButton.setStyle("");

        // Highlight the selected button
        button.setStyle("-fx-background-color: #ADD8E6;"); // Light blue background
    }

    @Override
    protected void load_bindings() {
        studentMasterList = App.COLLECTIONS_REGISTRY.getList("STUDENT");
        scene = (Scene) getParameter("scene");
        loadSceneWithYear("/finalproject/admin/app/home/HOME.fxml", HomeController.class);

        payrollButton.setOnAction(event -> handlesPayrollButton());
        attendanceButton.setOnAction(event -> handleAttendanceButton());
        generateKeyMenuItem.setOnAction(event -> handleGenerateKeyMenuItem());
        backButton.setOnMouseClicked(event -> handleBackButton());
    }

    @Override
    protected void load_fields() {
        yearComboBox.setItems(YearData.getYears());
        yearComboBox.setValue(String.valueOf(java.time.Year.now().getValue()));
    }

    @Override
    protected void load_listeners() {
        backButton.setOnMouseClicked(event -> handleBackButton());
    }
}
