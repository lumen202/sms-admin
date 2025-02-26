package finalproject.admin.app;

import dev.sol.core.application.FXController;
import finalproject.admin.app.attendance.AttendanceController;
import finalproject.admin.app.attendance.AttendanceLoader;
import finalproject.admin.app.home.HomeController;
import finalproject.admin.app.home.HomeLoader;
import finalproject.admin.app.payroll.PayrollController;
import finalproject.admin.app.payroll.PayrollLoader;
import finalproject.admin.util.ControllerLoader;
import finalproject.admin.util.SceneLoaderUtil;
import finalproject.admin.util.YearData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
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
    @FXML
    private Scene scene;

    // Store the active controller (e.g., PayrollController or AttendanceController)
    private FXController currentController;

    // Generic method that wraps the utility call.
    private <L extends FXLoader & ControllerLoader<C>, C extends FXController> void loadSceneWithYear(
            String fxmlPath, Class<L> loaderClass, Class<C> controllerClass) {
        // Use this class (RootController.class) as the base to load the resource.
        C controller = SceneLoaderUtil.loadSceneWithYear(fxmlPath, getClass(), loaderClass, yearComboBox.getValue(),
                contentPane);
        currentController = controller;
    }

    @FXML
    private void handlesPayrollButton() {
        highlightButton(payrollButton);
        loadSceneWithYear("/finalproject/admin/app/payroll/PAYROLL.fxml", PayrollLoader.class, PayrollController.class);
    }

    @FXML
    private void handleAttendanceButton() {
        highlightButton(attendanceButton);
        loadSceneWithYear("/finalproject/admin/app/attendance/ATTENDANCE.fxml", AttendanceLoader.class,
                AttendanceController.class);
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
        loadSceneWithYear("/finalproject/admin/app/home/HOME.fxml", HomeLoader.class, HomeController.class);
    }

    // private void applyFadeTransition(Parent rootNode) {
    // FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),
    // rootNode);
    // fadeTransition.setFromValue(0.0);
    // fadeTransition.setToValue(1.0);
    // fadeTransition.play();
    // }

    private void highlightButton(Button button) {
        attendanceButton.setStyle("");
        payrollButton.setStyle("");
        backButton.setStyle("");
        button.setStyle("-fx-background-color: #ADD8E6;");
    }

    @Override
    protected void load_bindings() {
        scene = (Scene) getParameter("scene");
        // Initially load Home.
        loadSceneWithYear("/finalproject/admin/app/home/HOME.fxml", HomeLoader.class, HomeController.class);
        payrollButton.setOnAction(event -> handlesPayrollButton());
        attendanceButton.setOnAction(event -> handleAttendanceButton());
        generateKeyMenuItem.setOnAction(event -> handleGenerateKeyMenuItem());
        backButton.setOnMouseClicked(event -> handleBackButton());

        // Listen for changes to the yearComboBox.
        yearComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs, String oldYear, String newYear) {
                System.out.println("RootController: Year changed to " + newYear);
                if (currentController instanceof AttendanceController) {
                    ((AttendanceController) currentController).updateYear(newYear);
                } else if (currentController instanceof PayrollController) {
                    ((PayrollController) currentController).updateYear(newYear);
                }
            }
        });
    }

    @Override
    protected void load_fields() {
        yearComboBox.setItems(YearData.getYears()); // Use YearData instead
        yearComboBox.setValue(YearData.getCurrentAcademicYear()); // Use YearData instead

        // Load Home initially after setting up the year
        loadSceneWithYear("/finalproject/admin/app/home/HOME.fxml", HomeLoader.class, HomeController.class);
    }

    @Override
    protected void load_listeners() {
        backButton.setOnMouseClicked(event -> handleBackButton());
    }
}
