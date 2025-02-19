package finalproject.admin.app.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Map;

import dev.sol.core.application.FXController;
import dev.sol.core.application.loader.FXLoaderFactory;
import finalproject.admin.app.viewstudent.StudentProfileLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class AttendanceController extends FXController {

    @FXML
    private Button studentManagementLabel;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<String> monthComboBox;
    @FXML
    private ComboBox<String> yearComboBox;
    @FXML
    private ComboBox<String> weekComboBox;
    @FXML
    private TableView<Map<String, String>> attendanceTable;
    @FXML
    private TableColumn<Map<String, String>, String> studentColumn;
    @FXML
    private TableColumn<Map<String, String>, String> timeRollColumn; // Parent column

    private Stage stage;
    private ObservableList<Map<String, String>> attendanceData;

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
        StudentProfileLoader loader = (StudentProfileLoader) FXLoaderFactory
                .createInstance(StudentProfileLoader.class,
                        getClass().getResource("/finalproject/admin/app/management/viewstudent/STUDENT_PROFILE.fxml"))
                .initialize();
        loader.load();
    }

    private void initializeRoot() {
        closeCurrentStage();
    }

    @Override
    protected void load_bindings() {

        // Set default selections
        weekComboBox.setValue("Week " + getCurrentWeekOfMonth());

        // Listen for changes in selection
        monthComboBox.setOnAction(event -> updateTableColumns());
        yearComboBox.setOnAction(event -> updateTableColumns());
        weekComboBox.setOnAction(event -> updateTableColumns());

        // Initialize table
        attendanceData = FXCollections.observableArrayList();
        attendanceTable.setItems(attendanceData);
        attendanceTable.setEditable(true);
        updateTableColumns();
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

    public void initialize() {
        // Populate month and year ComboBoxes
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        for (int year = 2020; year <= 2030; year++) {
            yearComboBox.getItems().add(String.valueOf(year));
        }

        // Populate week ComboBox (1 to 4)
        for (int week = 1; week <= 4; week++) {
            weekComboBox.getItems().add("Week " + week);
        }

    }

    private int getCurrentWeekOfMonth() {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekOfMonth = now.get(weekFields.weekOfMonth());
        LocalDate firstDayOfMonth = now.withDayOfMonth(1);
        int firstWeekOfMonth = firstDayOfMonth.get(weekFields.weekOfMonth());

        return weekOfMonth - firstWeekOfMonth;
    }

    private void updateTableColumns() {
        // Get selected month, year, and week
        String selectedMonth = monthComboBox.getValue();
        int selectedYear = Integer.parseInt(yearComboBox.getValue());
        int selectedWeek = Integer.parseInt(weekComboBox.getValue().replace("Week ", ""));

        // Convert month name to number
        int monthNumber = monthComboBox.getItems().indexOf(selectedMonth) + 1;

        // Get the first day of the selected week
        LocalDate firstDayOfWeek = LocalDate.of(selectedYear, monthNumber, 1)
                .with(WeekFields.of(Locale.getDefault()).getFirstDayOfWeek())
                .plusWeeks(selectedWeek - 1);

        // Clear previous columns
        timeRollColumn.getColumns().clear();

        // Generate new columns dynamically for Monday to Friday
        for (int day = 0; day < 7; day++) {
            LocalDate date = firstDayOfWeek.plusDays(day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                String dayName = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

                // Create a new column
                TableColumn<Map<String, String>, String> dayColumn = new TableColumn<>(
                        dayName + "\n" + date.getDayOfMonth());
                dayColumn.setPrefWidth(50);
                dayColumn.setCellValueFactory(new PropertyValueFactory<>("day" + (date.getDayOfMonth())));
                dayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                dayColumn.setOnEditCommit(event -> {
                    Map<String, String> row = event.getRowValue();
                    row.put("day" + (date.getDayOfMonth()), event.getNewValue());
                });

                // Add column to the time roll section
                timeRollColumn.getColumns().add(dayColumn);
            }
        }
    }
}