package finalproject.admin.app;

import dev.sol.core.application.FXController;
import dev.sol.core.application.loader.FXLoaderFactory;
import finalproject.admin.app.attendance.AttendanceLoader;
import finalproject.admin.app.payroll.PayrollLoader;
import finalproject.admin.app.viewstudent.StudentProfileLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class RootController extends FXController {

    @FXML
    private Button studentProfileButton;
    @FXML
    private MenuItem attendanceButton;
    @FXML
    private MenuItem payrollMenuItem;
    @FXML
    MenuItem attendanceMenuItem;

    @FXML

    private Scene scene;

    @FXML
    private void handleStudentProfileButton() {
        System.out.println("Student profile Button Clicked");
        initialize_studentProfile();
    }

    @FXML
    private void handlesPayrollButton() {
        System.out.println("Payroll Button is Clicked");
        initialize_payroll();

    }

    @FXML
    private void handleAttendanceButton() {
        System.out.println("attendance button is clicked");
        initialize_attendance();

    }

    private void initialize_attendance() {
        AttendanceLoader loader = (AttendanceLoader) FXLoaderFactory
                .createInstance(AttendanceLoader.class,
                        getClass().getResource("/finalproject/admin/app/attendance/ATTENDANCE.fxml"))
                .addParameter("scene", scene)
                .addParameter("OWNER", getParameter("OWNER"))
                .initialize();
        loader.load();

    }

    private void initialize_payroll() {
        PayrollLoader loader = (PayrollLoader) FXLoaderFactory
                .createInstance(PayrollLoader.class,
                        getClass().getResource("/finalproject/admin/app/payroll/PAYROLL.fxml"))
                .addParameter("scene", scene)
                .addParameter("OWNER", getParameter("OWNER"))
                .initialize();
        loader.load();
    }

    private void initialize_studentProfile() {
        StudentProfileLoader loader = (StudentProfileLoader) FXLoaderFactory
                .createInstance(StudentProfileLoader.class,
                        getClass().getResource("/finalproject/admin/app/viewstudent/STUDENT_PROFILE.fxml"))
                .addParameter("scene", scene)
                .addParameter("OWNER", getParameter("OWNER"))
                .initialize();
        loader.load();

    }

    @Override
    protected void load_bindings() {
        scene = (Scene) getParameter("scene");

        attendanceMenuItem.setOnAction(event -> handleStudentProfileButton());
        payrollMenuItem.setOnAction(event -> handlesPayrollButton());
        attendanceMenuItem.setOnAction(event -> handleAttendanceButton());
    }

    @Override
    protected void load_fields() {
        // Initialize fields if necessary
    }

    @Override
    protected void load_listeners() {
        studentProfileButton.setOnMouseClicked(event -> handleStudentProfileButton());

    }

}
