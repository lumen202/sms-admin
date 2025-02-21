package finalproject.admin.app.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

import dev.sol.core.application.FXController;
import dev.sol.core.application.loader.FXLoaderFactory;
import finalproject.admin.app.viewstudent.StudentProfileLoader;
import finalproject.admin.util.YearData;
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
    private ComboBox<String> monthYearComboBox;
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
        // Initialize bindings if necessary
    }

    private void closeCurrentStage() {
        stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    @Override
    protected void load_fields() {
        System.out.println("asdasdasdasd");
    }

    @Override
    protected void load_listeners() {
        // No need to manually set the event handler
    }

    @FXML
    public void initialize() {
        load(); // Ensure this method is called to load fields, bindings, and listeners
        // Populate monthYear ComboBox
        ObservableList<String> monthYearList = FXCollections.observableArrayList();
        for (String year : YearData.getYears()) {
            for (String month : new String[] { "January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December" }) {
                monthYearList.add(month + " " + year);
            }
        }
        monthYearComboBox.setItems(monthYearList);

        // Set default selection
        LocalDate now = LocalDate.now();
        String defaultMonthYear = now.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + now.getYear();
        monthYearComboBox.setValue(defaultMonthYear);

        // Populate week ComboBox
        populateWeekComboBox();

        // Listen for changes in selection
        monthYearComboBox.setOnAction(event -> {
            populateWeekComboBox();
            updateTableColumns();
        });
        weekComboBox.setOnAction(event -> updateTableColumns());

        // Initialize table
        attendanceData = FXCollections.observableArrayList();
        attendanceTable.setItems(attendanceData);
        attendanceTable.setEditable(true);
        updateTableColumns();
    }

    private void populateWeekComboBox() {
        weekComboBox.getItems().clear();
        String selectedMonthYear = monthYearComboBox.getValue();

        if (selectedMonthYear == null) {
            return;
        }

        String[] parts = selectedMonthYear.split(" ");
        String selectedYear = parts[1];

        int monthNumber = monthYearComboBox.getItems().indexOf(selectedMonthYear) % 12 + 1;
        int yearNumber = Integer.parseInt(selectedYear);

        LocalDate firstDayOfMonth = LocalDate.of(yearNumber, monthNumber, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");

        LocalDate currentDay = firstDayOfMonth;
        while (currentDay.getMonthValue() == monthNumber) {
            LocalDate startOfWeek = currentDay;
            LocalDate endOfWeek = startOfWeek;
            for (int i = 0; i < 5; i++) { // Only include weekdays
                if (endOfWeek.getDayOfWeek() == DayOfWeek.SATURDAY || endOfWeek.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    endOfWeek = endOfWeek.plusDays(1);
                } else {
                    endOfWeek = endOfWeek.plusDays(1);
                }
            }
            endOfWeek = endOfWeek.minusDays(1); // Exclude Saturday and Sunday
            weekComboBox.getItems().add(startOfWeek.format(formatter) + " - " + endOfWeek.format(formatter));
            currentDay = endOfWeek.plusDays(2); // Move to the next Monday
        }

        if (!weekComboBox.getItems().isEmpty()) {
            weekComboBox.setValue(weekComboBox.getItems().get(0));
        }
    }

    private void updateTableColumns() {
        // Get selected month, year, and week
        String selectedMonthYear = monthYearComboBox.getValue();
        String selectedWeek = weekComboBox.getValue();

        if (selectedMonthYear == null || selectedWeek == null) {
            return;
        }

        String[] parts = selectedMonthYear.split(" ");
        String selectedMonth = parts[0];
        String selectedYear = parts[1];

        // Convert month name to number
        int monthNumber = monthYearComboBox.getItems().indexOf(selectedMonthYear) % 12 + 1;
        int yearNumber = Integer.parseInt(selectedYear);

        // Get the first day of the selected week
        String[] weekRange = selectedWeek.split(" - ");
        LocalDate startOfWeek = LocalDate.of(yearNumber, monthNumber, Integer.parseInt(weekRange[0]));

        // Clear previous columns
        timeRollColumn.getColumns().clear();

        // Generate new columns dynamically for Monday to Friday
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                continue;
            }
            LocalDate date = startOfWeek.with(day);
            TableColumn<Map<String, String>, String> dayColumn = new TableColumn<>(
                    date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + date.getDayOfMonth());
            dayColumn.setCellValueFactory(new PropertyValueFactory<>(date.toString()));
            dayColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            timeRollColumn.getColumns().add(dayColumn);
        }
    }
}