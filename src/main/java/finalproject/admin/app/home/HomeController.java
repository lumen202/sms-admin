package finalproject.admin.app.home;

import dev.finalproject.App;
import dev.finalproject.models.Cluster;
import dev.sol.core.application.FXController;
import finalproject.admin.app.viewstudent.studentform.StudentFormController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class HomeController extends FXController {

    @FXML
    private StackPane contentPane;

    @FXML
    private TableView<Cluster> clusterTableView;
    @FXML
    private TableColumn<Cluster, Integer> clusterID;
    @FXML
    private TableColumn<Cluster, String> clusterName;

    private ObservableList<Cluster> clusterMasterList;

    private ContextMenu studentMenu;

    private void handleEditMenu() {
        initialize_editStudent();
    }

    private void initialize_editStudent() {
        loadSceneWithYear("/finalproject/admin/app/viewstudent/studentform/STUDENT_FORM.fxml",
                StudentFormController.class);
    }

    private void loadSceneWithYear(String fxmlPath, Class<? extends FXController> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent rootNode = loader.load();
            applyFadeTransition(rootNode);
            contentPane.getChildren().setAll(rootNode);

            Region region = (Region) rootNode;
            region.prefWidthProperty().bind(contentPane.widthProperty());
            region.prefHeightProperty().bind(contentPane.heightProperty());

            FXController controller = loader.getController();
            if (controllerClass.isInstance(controller)) {
                controller.load();
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

    @Override
    protected void load_bindings() {
        clusterMasterList = App.COLLECTIONS_REGISTRY.getList("CLUSTER");

        clusterID.setCellValueFactory(cell -> cell.getValue().clusterIDProperty().asObject());
        clusterName.setCellValueFactory(cell -> cell.getValue().clusterNameProperty());
        clusterTableView.setItems(clusterMasterList);

        studentMenu = new ContextMenu();
        MenuItem editMenu = new MenuItem("Edit Student Profile");
        editMenu.setOnAction(event -> handleEditMenu());
        studentMenu.getItems().add(editMenu);
        clusterTableView.setContextMenu(studentMenu);
    }

    @Override
    protected void load_fields() {
    }

    @Override
    protected void load_listeners() {
    }
}