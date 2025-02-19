module finalproject.admin {

    requires transitive javafx.controls;
    requires transitive core.fx;
    requires transitive core.db;

    requires javafx.fxml;
    requires atlantafx.base;
    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.materialdesign;
    requires dev.finalproject;

    requires core.util;
    requires java.sql.rowset;
    requires javafx.base;

    opens finalproject.admin to javafx.fxml;
    opens finalproject.admin.app to javafx.fxml, core.fx;
    opens finalproject.admin.app.attendance to core.fx, javafx.fxml;
    opens finalproject.admin.app.viewstudent to core.fx, javafx.fxml;
    opens finalproject.admin.app.payroll to core.fx, javafx.fxml;
    opens finalproject.admin.app.viewstudent.studentform to core.fx, javafx.fxml;

    exports finalproject.admin;
}
