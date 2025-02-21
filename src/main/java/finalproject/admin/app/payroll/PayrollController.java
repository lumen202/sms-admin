package finalproject.admin.app.payroll;

import dev.sol.core.application.FXController;
import finalproject.admin.util.YearData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class PayrollController extends FXController {

    @FXML
    private ComboBox<String> yearMonthComboBox;

    @Override
    protected void load_fields() {
        ObservableList<String> yearMonthList = FXCollections.observableArrayList();
        for (String year : YearData.getYears()) {
            for (String month : new String[] { "January", "February", "March", "April", "May", "June", "July", "August",
                    "September", "October", "November", "December" }) {
                yearMonthList.add(month + " " + year);
            }
        }
        yearMonthComboBox.setItems(yearMonthList);
        yearMonthComboBox.setValue(yearMonthList.get(0)); // Set default value
    }

    @Override
    protected void load_bindings() {
        System.out.println("asdasdasd");
    }

    @Override
    protected void load_listeners() {
        // Initialize listeners if necessary
    }

    @FXML
    public void initialize() {
        load(); // Ensure this method is called to load fields, bindings, and listeners
    }
}
