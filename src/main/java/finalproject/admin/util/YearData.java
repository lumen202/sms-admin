package finalproject.admin.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Year;

public class YearData {
    private static final ObservableList<String> years = FXCollections.observableArrayList();

    static {
        int currentYear = Year.now().getValue();
        for (int i = currentYear - 10; i <= currentYear + 10; i++) {
            years.add(String.valueOf(i));
        }
    }

    public static ObservableList<String> getYears() {
        return years;
    }
}
